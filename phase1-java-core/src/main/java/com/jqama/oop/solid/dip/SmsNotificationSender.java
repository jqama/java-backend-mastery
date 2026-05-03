package com.jqama.oop.solid.dip;

public class SmsNotificationSender implements NotificationSender {
    @Override
    public void send(String recipient, String subject, String body) {
        System.out.printf("[SMS] To: %s | %s - %s%n", recipient, subject, body);
    }

    @Override
    public String getChannelName() {
        return "SMS";
    }
}