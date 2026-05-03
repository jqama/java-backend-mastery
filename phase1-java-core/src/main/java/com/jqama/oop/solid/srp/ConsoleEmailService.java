package com.jqama.oop.solid.srp;

public class ConsoleEmailService implements EmailService {
    @Override
    public void sendWelcome(User user) {
        System.out.printf("[EMAIL] Welcome %s! Sent to: %s%n", user.name(), user.email());
    }

    @Override
    public void sendPasswordReset(User user, String resetToken) {
        System.out.printf("[EMAIL] Password reset for %s. Token: %s%n", user.email(), resetToken);
    }
}