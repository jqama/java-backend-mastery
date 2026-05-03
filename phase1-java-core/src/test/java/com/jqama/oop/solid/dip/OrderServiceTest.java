package com.jqama.oop.solid.dip;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Tests for DIP — OrderService depends on NotificationSender abstraction.
 *
 * Key concept: OrderService is tested here with a MOCK NotificationSender.
 * We never import EmailNotificationSender or SmsNotificationSender in this
 * test.
 * That's the DIP payoff — the high-level class is testable in isolation.
 *
 * ArgumentCaptor:
 * Used when you want to assert WHAT arguments were passed to a mock.
 * Example: verify the notification was sent to the right email address.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("DIP — OrderService")
class OrderServiceTest {

    @Mock
    private NotificationSender notificationSender; // abstraction — not email or SMS

    @InjectMocks
    private OrderService orderService;

    // ArgumentCaptors capture the arguments passed to mock method calls
    @Captor
    private ArgumentCaptor<String> recipientCaptor;
    @Captor
    private ArgumentCaptor<String> subjectCaptor;
    @Captor
    private ArgumentCaptor<String> bodyCaptor;

    // ── placeOrder ────────────────────────────────────────────────────────────

    @Nested
    @DisplayName("placeOrder")
    class PlaceOrderTests {

        @Test
        @DisplayName("should call send() on the notification sender exactly once")
        void shouldSendExactlyOneNotification() {
            orderService.placeOrder("ORD-001", "jj@example.com", 299.99);

            verify(notificationSender, times(1)).send(anyString(), anyString(), anyString());
        }

        @Test
        @DisplayName("notification should go to the customer's email address")
        void shouldSendToCorrectRecipient() {
            orderService.placeOrder("ORD-001", "jj@example.com", 299.99);

            // Capture the recipient argument and assert it
            verify(notificationSender).send(recipientCaptor.capture(), anyString(), anyString());
            assertThat(recipientCaptor.getValue()).isEqualTo("jj@example.com");
        }

        @Test
        @DisplayName("notification subject should contain the order ID")
        void subjectShouldContainOrderId() {
            orderService.placeOrder("ORD-XYZ", "jj@example.com", 50.0);

            verify(notificationSender).send(anyString(), subjectCaptor.capture(), anyString());
            assertThat(subjectCaptor.getValue()).contains("ORD-XYZ");
        }

        @Test
        @DisplayName("notification body should contain the order total amount")
        void bodyShouldContainTotal() {
            orderService.placeOrder("ORD-001", "jj@example.com", 1500.00);

            verify(notificationSender).send(anyString(), anyString(), bodyCaptor.capture());
            assertThat(bodyCaptor.getValue()).contains("1500.00");
        }
    }

    // ── multi-channel ─────────────────────────────────────────────────────────

    @Nested
    @DisplayName("placeOrderMultiChannel")
    class MultiChannelTests {

        @Test
        @DisplayName("should notify every provided sender channel")
        void shouldNotifyAllChannels() {
            NotificationSender emailSender = mock(NotificationSender.class);
            NotificationSender smsSender = mock(NotificationSender.class);

            orderService.placeOrderMultiChannel("ORD-002", "jj@example.com", 99.0,
                    List.of(emailSender, smsSender));

            verify(emailSender, times(1)).send(anyString(), anyString(), anyString());
            verify(smsSender, times(1)).send(anyString(), anyString(), anyString());
        }

        @Test
        @DisplayName("empty sender list should not throw — graceful no-op")
        void emptySenderList_shouldNotThrow() {
            assertThatNoException().isThrownBy(
                    () -> orderService.placeOrderMultiChannel("ORD-003", "jj@example.com", 50.0, List.of()));
        }
    }

    // ── DIP proof: swap implementations ──────────────────────────────────────

    @Nested
    @DisplayName("DIP proof — swap implementations without changing OrderService")
    class SwapImplementationTests {

        @Test
        @DisplayName("OrderService should work with EmailNotificationSender")
        void shouldWorkWithEmailSender() {
            // Real EmailNotificationSender — no mock
            OrderService service = new OrderService(new EmailNotificationSender());

            // OrderService doesn't know it's email — it just calls send()
            assertThatNoException().isThrownBy(() -> service.placeOrder("ORD-001", "jj@example.com", 100.0));
        }

        @Test
        @DisplayName("OrderService should work with SmsNotificationSender — zero code change")
        void shouldWorkWithSmsSender() {
            // Swap email for SMS — OrderService class is completely unchanged
            OrderService service = new OrderService(new SmsNotificationSender());

            assertThatNoException().isThrownBy(() -> service.placeOrder("ORD-001", "+61400000000", 100.0));
        }
    }
}