# Vending Machine API

Spring Boot REST API that simulates a vending machine.

## Stack

- Java 10, Spring Boot 2.4.5, Maven
- Spring Data JPA + H2 (test scope)
- Lombok 1.18.34
- JUnit 5 + Mockito + AssertJ

## Architecture

Layered: Controller → Service (interface + impl) → Repository → JPA Entity

```
controller.VendingMachineController
  → service.VendingMachineService (interface)
    → service.impl.VendingMachineServiceImpl
      → model.repository.ProductRepository
      → model.repository.PurchaseRepository
        → model.entity.Product / Purchase
```

## API Endpoints

| Method | Path | Description |
|--------|------|-------------|
| GET | `/vendingMachine/showProducts` | List all products |
| POST | `/vendingMachine/insertMoney` | Insert coins (body: `{"listCoins": [1.0, 0.5]}`) |
| GET | `/vendingMachine/buy?idProducto=X&idPurchase=Y` | Buy a product |
| GET | `/vendingMachine/purchase/{purchaseId}` | Get purchase by ID |
| DELETE | `/vendingMachine/purchase/{purchaseId}` | Refund (cancel purchase) |

## Data Model

### Product
- `id` (Long, auto)
- `name` (String)
- `price` (BigDecimal)
- `quantity` (Integer)

### Purchase
- `id` (Long, auto)
- `amount` (BigDecimal) — total inserted

## Business Rules

- Cannot buy if `quantity <= 0`
- Cannot buy if `amount < price`
- On purchase, `quantity` is decremented by 1 and the product is saved
- Refund returns the `amount` and deletes the `Purchase`
- Base products are seeded at startup via `CommandLineRunner` in `VendingMachineApplication`

## Exceptions

- `EntityNotFoundException` → `@ResponseStatus(BAD_REQUEST)` — entity not found
- `UserExceptions` → `@ResponseStatus(BAD_REQUEST)` — insufficient stock/funds

## Conventions

- `BigDecimal` for monetary values (not `Double`)
- Constructor injection instead of `@Autowired` field injection
- `@Transactional` on operations that modify multiple entities
- Tests use Mockito + `@ExtendWith(MockitoExtension.class)`
- `@InjectMocks` automatically uses constructor injection

## Build & Test

```bash
mvn clean test        # compile and run tests
mvn clean install     # full build
```

Unit tests are in `src/test/java/com/vendingMachine/`.

## Postman Collection

The `Dispenser.postman_collection.json` file is outdated (uses `/dispenser/` instead of `/vendingMachine/`). Ignore or update it.
