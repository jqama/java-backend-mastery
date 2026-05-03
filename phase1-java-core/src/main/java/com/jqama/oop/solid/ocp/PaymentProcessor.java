package com.jqama.oop.solid.ocp;

import java.util.*;

public class PaymentProcessor {
    private final List<PaymentResult> history = new ArrayList<>();

    public PaymentResult process(PaymentStrategy strategy, double amount, String currency) {
        PaymentResult result = strategy.process(amount, currency);
        history.add(result);
        return result;
    }

    public List<PaymentResult> getHistory() {
        return Collections.unmodifiableList(history);
    }
}