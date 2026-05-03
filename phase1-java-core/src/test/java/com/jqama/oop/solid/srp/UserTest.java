package com.jqama.oop.solid.srp;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

/**
 * Tests for User record validation.
 *
 * Java records auto-generate: constructor, getters, equals, hashCode, toString.
 * We test the custom validation logic in the compact constructor.
 */
@DisplayName("SRP — User record")
class UserTest {

    @Test
    @DisplayName("should create user with valid data")
    void shouldCreateUserWithValidData() {
        User user = new User(1L, "Junaid", "jj@example.com");

        assertThat(user.id()).isEqualTo(1L);
        assertThat(user.name()).isEqualTo("Junaid");
        assertThat(user.email()).isEqualTo("jj@example.com");
    }

    @Test
    @DisplayName("should reject blank name")
    void shouldRejectBlankName() {
        assertThatThrownBy(() -> new User(1L, "", "jj@example.com"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Name must not be blank");
    }

    @Test
    @DisplayName("should reject null name")
    void shouldRejectNullName() {
        assertThatThrownBy(() -> new User(1L, null, "jj@example.com"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("should reject email without @ symbol")
    void shouldRejectInvalidEmail() {
        assertThatThrownBy(() -> new User(1L, "Junaid", "notanemail"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Invalid email");
    }

    @Test
    @DisplayName("two records with same data should be equal — record equality")
    void recordEquality_shouldWork() {
        User u1 = new User(1L, "Junaid", "jj@example.com");
        User u2 = new User(1L, "Junaid", "jj@example.com");

        // Records compare by value, not reference — unlike regular classes
        assertThat(u1).isEqualTo(u2);
        assertThat(u1.hashCode()).isEqualTo(u2.hashCode());
    }
}