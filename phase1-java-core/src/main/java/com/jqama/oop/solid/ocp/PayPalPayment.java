package com.jqama.oop.solid.ocp;

public class PayPalPayment implements PaymentStrategy {
    private final String paypalEmail;

    public PayPalPayment(String paypalEmail) {
        this.paypalEmail = paypalEmail;
    }

    @Override
    public PaymentResult process(double amount, String currency) {
        if (amount <= 0)
            return PaymentResult.failure(getMethodName(), amount, currency, "Amount must be positive");
        System.out.printf("[PayPal] Charging %s %.2f from: %s%n", currency, amount, paypalEmail);
        return PaymentResult.success(getMethodName(), amount, currency);
    }

    @Override
    public String getMethodName() {
        return "PAYPAL";
    }
}