# Vending Machine

## Build & test

```sh
./gradlew test                          # all tests
./gradlew test --tests *VendingMachineServiceTest  # single test class
```

Tests are pure Mockito unit tests (`@ExtendWith(MockitoExtension.class)`, no `@SpringBootTest`), so they run fast.

## Gotchas

- **Postman collection** (`src/main/resources/Dispenser.postman_collection.json`) uses `/dispenser/` paths but the controller uses `/vendingMachine/`. Stale.
- **No production database** configured. H2 is test-scoped only. JPA entities + startup seed data will fail at runtime without a datasource.
- **Default port** is 8080 (the `server.port` in `application.properties` is commented out).
- **Java 21 required** for building and running. The system JDK is `21.0.10` and Gradle 8.7 handles it directly (`java {}` block in `build.gradle`).

## Conventions

- Monetary values use `BigDecimal`, never `Double` (`DataProducts`, `PurchaseRequest.listCoins`, `Product.price`, `Purchase.amount`).
- Constructor injection everywhere (no `@Autowired` on fields).
- `@Transactional` on write operations that span multiple repository calls (e.g., `buyProduct` in application service).
- Exceptions live in `domain.exception` (pure Java). HTTP status mapping is done via `@RestControllerAdvice` in `infrastructure.web`.
- Seed data is handled by `DataSeeder` (infrastructure) using the output port.
- Wiring of application service (no Spring annotations) is done via `@Configuration` in `infrastructure.config`.

## Hexagonal architecture layers

```
domain/          → pure Java models + exceptions (no frameworks)
application/     → use cases (input ports), repository ports (output ports), service
infrastructure/  → JPA adapters, Spring repos, REST controller, exception handler
```

### Dependency direction: Infrastructure → Application → Domain (Domain knows nothing of the others)

### Layer rules

- **Domain**: No framework annotations, no I/O, no Spring/JPA imports. Contains business logic (e.g., `Product.hasStock()`, `Product.decreaseStock()`).
- **Application**: Defines input ports (use case interfaces) and output ports (repository interfaces). Implements use cases depending only on domain objects and ports.
- **Infrastructure**: Implements output ports (JPA adapters with mappers), exposes input ports via REST controller, wires beans via `@Configuration`.

## Package structure

```
com.vendingMachine
├── domain/
│   ├── model/          Product, Purchase (no JPA annotations)
│   └── exception/      ProductNotFoundException, PurchaseNotFoundException,
│                       InsufficientFundsException, OutOfStockException
├── application/
│   ├── port/
│   │   ├── input/      VendingMachineUseCase
│   │   └── output/     ProductRepositoryPort, PurchaseRepositoryPort
│   ├── dto/            PurchaseRequest, PurchaseResponse
│   └── service/        VendingMachineService (use case impl)
└── infrastructure/
    ├── config/         BeanConfiguration (wiring)
    ├── persistence/
    │   ├── entity/     ProductEntity, PurchaseEntity (JPA)
    │   ├── repository/ SpringProductRepository, SpringPurchaseRepository
    │   ├── mapper/     ProductMapper, PurchaseMapper
    │   └── adapter/    ProductJpaAdapter, PurchaseJpaAdapter
    └── web/            VendingMachineController, GlobalExceptionHandler
```

## Stack

Spring Boot 3.2.5 / Java 21 / Gradle / Lombok (infrastructure only) / JPA + H2 (test only) / JUnit 5 + Mockito + AssertJ
