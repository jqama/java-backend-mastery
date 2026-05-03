package com.jqama.oop.solid.ocp;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.*;

/**
 * Tests for OCP payment strategy classes.
 *
 * @Nested — groups related tests inside one file. Clean and
 *         interview-impressive.
 * @ParameterizedTest — run the same test with multiple inputs.
 * @ValueSource — supply the list of values for a parameterized test.
 *
 *              Notice: PaymentProcessor is never changed as we add new
 *              strategies.
 *              That's the OCP proof — the test file grows, the processor class
 *              doesn't.
 */
@DisplayName("OCP — PaymentProcessor")
class PaymentProcessorTest {

    private PaymentProcessor processor;

    @BeforeEach
    void setUp() {
        processor = new PaymentProcessor();
    }

    // ── CreditCard ────────────────────────────────────────────────────────────

    @Nested
    @DisplayName("Credit Card payments")
    class CreditCardTests {

        private CreditCardPayment creditCard;

        @BeforeEach
        void setUp() {
            creditCard = new CreditCardPayment("4111111111111234");
        }

        @Test
        @DisplayName("should succeed for a valid positive amount")
        void shouldProcessValidPayment() {
            PaymentResult result = processor.process(creditCard, 250.00, "AUD");

            assertThat(result.success()).isTrue();
            assertThat(result.methodName()).isEqualTo("CREDIT_CARD");
            assertThat(result.amount()).isEqualTo(250.00);
            assertThat(result.transactionId()).isNotBlank();
        }

        @ParameterizedTest(name = "amount={0} should fail")
        @ValueSource(doubles = { 0.0, -1.0, -999.0 })
        @DisplayName("should fail for zero or negative amounts")
        void shouldFailForNonPositiveAmounts(double amount) {
            PaymentResult result = processor.process(creditCard, amount, "AUD");

            assertThat(result.success()).isFalse();
            assertThat(result.message()).containsIgnoringCase("positive");
        }
    }

    // ── PayPal ────────────────────────────────────────────────────────────────

    @Nested
    @DisplayName("PayPal payments")
    class PayPalTests {

        private PayPalPayment payPal;

        @BeforeEach
        void setUp() {
            payPal = new PayPalPayment("jj@example.com");
        }

        @Test
        @DisplayName("should succeed for a valid amount")
        void shouldProcessValidPayment() {
            PaymentResult result = processor.process(payPal, 99.99, "USD");

            assertThat(result.success()).isTrue();
            assertThat(result.methodName()).isEqualTo("PAYPAL");
            assertThat(result.currency()).isEqualTo("USD");
        }

        @Test
        @DisplayName("should fail for negative amount")
        void shouldFailForNegativeAmount() {
            PaymentResult result = processor.process(payPal, -50.0, "USD");
            assertThat(result.success()).isFalse();
        }
    }

    // ── BankTransfer ──────────────────────────────────────────────────────────

    @Nested
    @DisplayName("Bank Transfer payments")
    class BankTransferTests {

        private BankTransferPayment bankTransfer;

        @BeforeEach
        void setUp() {
            bankTransfer = new BankTransferPayment("062-000", "12345678");
        }

        @Test
        @DisplayName("should succeed for a valid amount within daily limit")
        void shouldProcessValidTransfer() {
            PaymentResult result = processor.process(bankTransfer, 5000.00, "AUD");

            assertThat(result.success()).isTrue();
            assertThat(result.methodName()).isEqualTo("BANK_TRANSFER");
        }

        @Test
        @DisplayName("should fail when amount exceeds daily limit of 100,000")
        void shouldFailAboveDailyLimit() {
            PaymentResult result = processor.process(bankTransfer, 100_001.00, "AUD");

            assertThat(result.success()).isFalse();
            assertThat(result.message()).containsIgnoringCase("limit");
        }

        @Test
        @DisplayName("should succeed at exact daily limit boundary — 100,000")
        void shouldSucceedAtExactLimit() {
            PaymentResult result = processor.process(bankTransfer, 100_000.00, "AUD");
            assertThat(result.success()).isTrue();
        }
    }

    // ── History tracking ──────────────────────────────────────────────────────

    @Nested
    @DisplayName("Payment history")
    class HistoryTests {

        @Test
        @DisplayName("should record all payments including failures")
        void shouldTrackAllPayments() {
            processor.process(new CreditCardPayment("4111"), 100.0, "AUD"); // success
            processor.process(new PayPalPayment("jj@example.com"), 200.0, "USD"); // success
            processor.process(new CreditCardPayment("4111"), -50.0, "AUD"); // failure

            assertThat(processor.getHistory()).hasSize(3);
        }

        @Test
        @DisplayName("history list should be immutable — callers cannot modify it")
        void historyShouldBeImmutable() {
            processor.process(new CreditCardPayment("4111"), 100.0, "AUD");

            // getHistory() returns an unmodifiable list — any mutation should throw
            assertThatThrownBy(() -> processor.getHistory().clear())
                    .isInstanceOf(UnsupportedOperationException.class);
        }
    }
}