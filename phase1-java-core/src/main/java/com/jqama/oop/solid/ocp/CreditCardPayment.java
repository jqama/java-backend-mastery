package com.jqama.oop.solid.ocp;

public class CreditCardPayment implements PaymentStrategy {
    private final String maskedCardNumber;

    public CreditCardPayment(String maskedCardNumber) {
        this.maskedCardNumber = maskedCardNumber;
    }

    @Override
    public PaymentResult process(double amount, String currency) {
        if (amount <= 0)
            return PaymentResult.failure(getMethodName(), amount, currency, "Amount must be positive");
        System.out.printf("[CreditCard] Charging %s %.2f%n", currency, amount);
        return PaymentResult.success(getMethodName(), amount, currency);
    }

    @Override
    public String getMethodName() {
        return "CREDIT_CARD";
    }
}