package com.jqama.oop.solid.dip;

public interface NotificationSender {
    void send(String recipient, String subject, String body);

    String getChannelName();
}