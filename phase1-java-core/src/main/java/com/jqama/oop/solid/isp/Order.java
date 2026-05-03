package com.jqama.oop.solid.isp;

import java.time.Instant;

public record Order(String id, String customerId, double total, Instant createdAt) {
    public static Order of(String id, String customerId, double total) {
        return new Order(id, customerId, total, Instant.now());
    }
}