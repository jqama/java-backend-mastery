package com.jqama.oop.solid.ocp;

import java.time.Instant;
import java.util.UUID;

public record PaymentResult(String transactionId, boolean success, String methodName,
        double amount, String currency, String message, Instant processedAt) {

    public static PaymentResult success(String methodName, double amount, String currency) {
        return new PaymentResult(UUID.randomUUID().toString(), true, methodName, amount, currency,
                "Payment processed successfully", Instant.now());
    }

    public static PaymentResult failure(String methodName, double amount, String currency, String reason) {
        return new PaymentResult(UUID.randomUUID().toString(), false, methodName, amount, currency,
                reason, Instant.now());
    }
}