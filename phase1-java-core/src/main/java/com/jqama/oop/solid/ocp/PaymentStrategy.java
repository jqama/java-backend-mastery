package com.jqama.oop.solid.ocp;

public interface PaymentStrategy {
    PaymentResult process(double amount, String currency);

    String getMethodName();
}