package com.jqama.oop.solid.isp;

import org.junit.jupiter.api.*;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.*;

/**
 * Tests for ISP — OrderRepository interface segregation.
 *
 * Key ISP proof in testReadOnlyInterface_shouldNotExposeWriteMethods():
 * We use Java reflection to confirm that ReadOnlyOrderRepository
 * literally does not have save() or deleteById() methods.
 * Compile-time safety: if you type readOnlyView.save() it won't compile.
 */
@DisplayName("ISP — Order Repository")
class OrderRepositoryTest {

    private InMemoryOrderRepository repository;
    private Order testOrder;

    @BeforeEach
    void setUp() {
        repository = new InMemoryOrderRepository();
        testOrder = Order.of("ORD-001", "CUST-123", 299.99);
    }

    // ── Full CRUD ─────────────────────────────────────────────────────────────

    @Nested
    @DisplayName("Full CRUD via OrderRepository")
    class FullCrudTests {

        @Test
        @DisplayName("save then findById should return the same order")
        void saveAndFind_shouldRoundTrip() {
            repository.save(testOrder);

            assertThat(repository.findById("ORD-001"))
                    .isPresent()
                    .hasValueSatisfying(o -> {
                        assertThat(o.id()).isEqualTo("ORD-001");
                        assertThat(o.customerId()).isEqualTo("CUST-123");
                        assertThat(o.total()).isEqualTo(299.99);
                    });
        }

        @Test
        @DisplayName("findById for unknown ID should return empty Optional")
        void findById_unknown_shouldReturnEmpty() {
            assertThat(repository.findById("DOES-NOT-EXIST")).isEmpty();
        }

        @Test
        @DisplayName("findAll should return all saved orders")
        void findAll_shouldReturnAllOrders() {
            repository.save(Order.of("ORD-001", "CUST-1", 100.0));
            repository.save(Order.of("ORD-002", "CUST-2", 200.0));
            repository.save(Order.of("ORD-003", "CUST-3", 300.0));

            assertThat(repository.findAll())
                    .hasSize(3)
                    .extracting(Order::id)
                    .containsExactlyInAnyOrder("ORD-001", "ORD-002", "ORD-003");
        }

        @Test
        @DisplayName("deleteById should remove the order")
        void deleteById_shouldRemoveOrder() {
            repository.save(testOrder);
            repository.deleteById("ORD-001");

            assertThat(repository.findById("ORD-001")).isEmpty();
        }

        @Test
        @DisplayName("deleteById on non-existent ID should throw NoSuchElementException")
        void deleteById_nonExistent_shouldThrow() {
            assertThatThrownBy(() -> repository.deleteById("GHOST"))
                    .isInstanceOf(NoSuchElementException.class)
                    .hasMessageContaining("GHOST");
        }

        @Test
        @DisplayName("saving with duplicate ID should overwrite the existing order")
        void save_duplicateId_shouldOverwrite() {
            repository.save(testOrder);
            Order updated = Order.of("ORD-001", "CUST-123", 999.99);
            repository.save(updated);

            assertThat(repository.findById("ORD-001"))
                    .isPresent()
                    .hasValueSatisfying(o -> assertThat(o.total()).isEqualTo(999.99));
        }
    }

    // ── ISP proof: read-only view ─────────────────────────────────────────────

    @Nested
    @DisplayName("ISP — ReadOnly interface cannot mutate")
    class ReadOnlyTests {

        @Test
        @DisplayName("read-only view should see data saved via full repository")
        void readOnlyView_shouldSeeSavedData() {
            repository.save(Order.of("ORD-001", "CUST-1", 100.0));
            repository.save(Order.of("ORD-002", "CUST-2", 200.0));

            // Assign to the restricted interface — same object, narrower contract
            ReadOnlyOrderRepository readOnly = repository;

            assertThat(readOnly.findAll()).hasSize(2);
            assertThat(readOnly.findById("ORD-001")).isPresent();
        }

        @Test
        @DisplayName("ReadOnlyOrderRepository should NOT have save() or deleteById()")
        void readOnlyInterface_shouldNotExposeWriteMethods() {
            // Reflection check: confirm the interface contract has no mutation methods
            var methodNames = java.util.Arrays
                    .stream(ReadOnlyOrderRepository.class.getMethods())
                    .map(java.lang.reflect.Method::getName)
                    .toList();

            assertThat(methodNames).doesNotContain("save", "deleteById");
            assertThat(methodNames).contains("findById", "findAll");
        }
    }
}