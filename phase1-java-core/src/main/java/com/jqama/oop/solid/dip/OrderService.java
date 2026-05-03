package com.jqama.oop.solid.dip;

import java.util.List;

public class OrderService {
    private final NotificationSender notificationSender;

    public OrderService(NotificationSender notificationSender) {
        this.notificationSender = notificationSender;
    }

    public void placeOrder(String orderId, String customerEmail, double total) {
        System.out.printf("[OrderService] Order %s placed. Total: AUD %.2f%n", orderId, total);
        notificationSender.send(customerEmail, "Order Confirmation #" + orderId,
                "Your order for AUD %.2f has been placed.".formatted(total));
    }

    public void placeOrderMultiChannel(String orderId, String customerEmail,
            double total, List<NotificationSender> senders) {
        senders.forEach(s -> s.send(customerEmail, "Order #" + orderId,
                "Confirmed. Total: AUD %.2f".formatted(total)));
    }
}