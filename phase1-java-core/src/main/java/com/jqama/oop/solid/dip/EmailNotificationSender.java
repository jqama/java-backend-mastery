package com.jqama.oop.solid.dip;

public class EmailNotificationSender implements NotificationSender {
    @Override
    public void send(String recipient, String subject, String body) {
        System.out.printf("[EMAIL] To: %s | %s | %s%n", recipient, subject, body);
    }

    @Override
    public String getChannelName() {
        return "EMAIL";
    }
}