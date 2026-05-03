package com.jqama.oop.solid.srp;

public interface EmailService {
    void sendWelcome(User user);

    void sendPasswordReset(User user, String resetToken);
}