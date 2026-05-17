# Vending Machine

## Build & test

```sh
./gradlew test                          # all tests
./gradlew test --tests *VendingMachineServiceImplTest  # single test class
```

Tests are pure Mockito unit tests (`@ExtendWith(MockitoExtension.class)`, no `@SpringBootTest`), so they run fast.

## Gotchas

- **Postman collection** (`src/main/resources/Dispenser.postman_collection.json`) uses `/dispenser/` paths but the controller uses `/vendingMachine/`. Stale.
- **No production database** configured. H2 is test-scoped only. JPA entities + startup seed data (`VendingMachineApplication.initData`) will fail at runtime without a datasource.
- **Default port** is 8080 (the `server.port` in `application.properties` is commented out).
- **Java 21 required** for building and running. The system JDK is `21.0.10` and Gradle 8.7 handles it directly (`java {}` block in `build.gradle`).

## Conventions

- Monetary values use `BigDecimal`, never `Double` (`DataProducts`, `PurchaseRequest.listCoins`, `Product.price`, `Purchase.amount`).
- Constructor injection everywhere (no `@Autowired` on fields).
- `@Transactional` on write operations that span multiple repository calls (e.g., `buyProcess`).
- Exceptions carry `@ResponseStatus(HttpStatus.BAD_REQUEST)`.
- `VendingMachineApplication` exposes a `@Bean CommandLineRunner initData` that seeds 8 products via `productRepository.saveAll(...)` if the DB is empty.

## Stack

Spring Boot 3.2.5 / Java 21 / Gradle / Lombok / JPA + H2 (test only) / JUnit 5 + Mockito + AssertJ
