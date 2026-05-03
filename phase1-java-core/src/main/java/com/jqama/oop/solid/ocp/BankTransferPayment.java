package com.jqama.oop.solid.ocp;

public class BankTransferPayment implements PaymentStrategy {
    private final String bsb;
    private final String accountNumber;

    public BankTransferPayment(String bsb, String accountNumber) {
        this.bsb = bsb;
        this.accountNumber = accountNumber;
    }

    @Override
    public PaymentResult process(double amount, String currency) {
        if (amount <= 0)
            return PaymentResult.failure(getMethodName(), amount, currency, "Amount must be positive");
        if (amount > 100_000)
            return PaymentResult.failure(getMethodName(), amount, currency, "Exceeds daily limit of 100,000");
        System.out.printf("[BankTransfer] %s %.2f to BSB: %s Acc: %s%n", currency, amount, bsb, accountNumber);
        return PaymentResult.success(getMethodName(), amount, currency);
    }

    @Override
    public String getMethodName() {
        return "BANK_TRANSFER";
    }
}