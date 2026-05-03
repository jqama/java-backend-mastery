# Phase 1 — Java Core & OOP Mastery

---

## What's covered

| Module | Topics | Package |
|--------|--------|---------|
| OOP & SOLID | SRP, OCP, LSP, ISP, DIP | `com.jqama.oop.solid` |
| Collections & Streams | _(coming next)_ | `com.jqama.collections` |
| Concurrency | _(coming next)_ | `com.jqama.concurrency` |
| Modern Java 17+ | _(coming next)_ | `com.jqama.modern` |
| Design Patterns | _(coming next)_ | `com.jqama.patterns` |

---

## SOLID Principles — quick reference

### S — Single Responsibility
**Package:** `com.jqama.oop.solid.srp`

`UserService` orchestrates user registration. It delegates storage to `UserRepository` and
email to `EmailService`. Each class has one reason to change.

```
User (record)           — domain data + validation
UserRepository          — interface: data access contract
InMemoryUserRepository  — implementation: in-memory store (swap for JPA in prod)
EmailService            — interface: notification contract
ConsoleEmailService     — implementation: logs to console (swap for SES/SendGrid)
UserReportService       — CSV report generation
UserService             — orchestrator: one job, many collaborators
```

### O — Open/Closed
**Package:** `com.jqama.oop.solid.ocp`

`PaymentProcessor` processes any `PaymentStrategy` without knowing which one.
Adding a new payment type (e.g. `CryptoPayment`) requires zero changes to `PaymentProcessor`.

```
PaymentStrategy      — interface: the extension point
CreditCardPayment    — impl 1
PayPalPayment        — impl 2
BankTransferPayment  — impl 3 (AUD bank transfer with BSB)
PaymentResult        — immutable record: transaction outcome
PaymentProcessor     — closed for modification, open for extension
```

### L — Liskov Substitution
**Package:** `com.jqama.oop.solid.lsp`

`ShapeCalculator` operates on `Shape` subtypes without instanceof or casting.
`Rectangle`, `Circle` — any shape can be substituted without breaking callers.

### I — Interface Segregation
**Package:** `com.jqama.oop.solid.isp`

Repositories are split into `Readable`, `Writable`, and `Deletable`.
Reporting services receive only `ReadOnlyOrderRepository` — they literally cannot call `save()`.

### D — Dependency Inversion
**Package:** `com.jqama.oop.solid.dip`

`OrderService` depends on `NotificationSender` (abstraction).
Inject `EmailNotificationSender` or `SmsNotificationSender` — `OrderService` never changes.
This is exactly how Spring `@Autowired` works under the hood.

---

## Running the tests

```bash
cd phase-1-java-core
mvn test
```

Expected output:
```
[INFO] Tests run: 40+, Failures: 0, Errors: 0, Skipped: 0
[INFO] BUILD SUCCESS
```

---

## Test patterns used

| Pattern | Where | Purpose |
|---------|-------|---------|
| `@ExtendWith(MockitoExtension.class)` | All service tests | JUnit 5 + Mockito integration |
| `@Mock` | Dependencies | Create mock collaborators |
| `@InjectMocks` | Class under test | Auto-inject mocks via constructor |
| `verify()` | SRP, DIP tests | Assert interactions, not just state |
| `inOrder()` | SRP test | Assert call order (save before email) |
| `ArgumentCaptor` | DIP test | Capture and inspect method arguments |
| `@Nested` | OCP, DIP tests | Group related tests by scenario |
| `@ParameterizedTest` | OCP test | Run one test with multiple inputs |
| AssertJ `assertThat()` | All | Fluent, readable assertions |

---

## Key points

- **Why SRP?** Fewer reasons to change = easier to test, maintain, and reason about.
- **Why OCP?** Open for extension = no regression risk when adding features.
- **The Square/Rectangle problem** — why inheritance models must respect behavioural contracts.
- **ISP vs SRP** — ISP applies to interfaces (clients shouldn't see methods they don't use); SRP applies to classes.
- **DIP = Spring DI** — everything Spring Boot does with `@Autowired` is DIP. You write the interface; Spring wires the implementation.

---

## Tech stack

- Java 17 (records, sealed classes, pattern matching)
- JUnit 5.10 with `@Nested`, `@ParameterizedTest`, `@DisplayName`
- Mockito 5 with `ArgumentCaptor`, `inOrder`, `verifyNoInteractions`
- AssertJ 3.24 fluent assertions
- Maven 3.x
