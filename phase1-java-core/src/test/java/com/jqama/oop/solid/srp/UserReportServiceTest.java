package com.jqama.oop.solid.srp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

/**
 * Tests for UserReportService and User record (both in srp package).
 *
 * AssertJ assertions used:
 * assertThat(x).isEqualTo(y) — exact equality
 * assertThat(x).startsWith(y) — string prefix check
 * assertThat(x).contains(y) — substring check
 * assertThatThrownBy(() -> ...) — assert an exception is thrown
 * .isInstanceOf(X.class) — assert exception type
 * .hasMessageContaining("...") — assert exception message
 */
@DisplayName("SRP — UserReportService")
class UserReportServiceTest {

    private UserReportService reportService;

    @BeforeEach
    void setUp() {
        reportService = new UserReportService();
    }

    @Test
    @DisplayName("generateCsvReport should produce header + one row per user")
    void generateCsvReport_shouldProduceValidCsv() {
        List<User> users = List.of(
                new User(1L, "Junaid Qamar", "jj@example.com"),
                new User(2L, "Alice Smith", "alice@example.com"));

        String csv = reportService.generateCsvReport(users);

        assertThat(csv).startsWith("id,name,email");
        assertThat(csv).contains("1,Junaid Qamar,jj@example.com");
        assertThat(csv).contains("2,Alice Smith,alice@example.com");
    }

    @Test
    @DisplayName("generateCsvReport with empty list should return header only")
    void generateCsvReport_emptyList_shouldReturnHeaderOnly() {
        String csv = reportService.generateCsvReport(List.of());
        assertThat(csv).isEqualTo("id,name,email");
    }

    @Test
    @DisplayName("countUsers should return the correct count")
    void countUsers_shouldReturnCorrectCount() {
        List<User> users = List.of(
                new User(1L, "A", "a@b.com"),
                new User(2L, "B", "b@b.com"),
                new User(3L, "C", "c@b.com"));
        assertThat(reportService.countUsers(users)).isEqualTo(3);
    }

    @Test
    @DisplayName("countUsers on empty list should return 0")
    void countUsers_emptyList_shouldReturnZero() {
        assertThat(reportService.countUsers(List.of())).isZero();
    }
}