package com.jqama.oop.solid.srp;

public record User(long id, String name, String email) {
    public User {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Name must not be blank");
        if (email == null || !email.contains("@")) throw new IllegalArgumentException("Invalid email: " + email);
    }
}