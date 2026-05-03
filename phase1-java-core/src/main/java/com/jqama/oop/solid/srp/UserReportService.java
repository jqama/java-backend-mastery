package com.jqama.oop.solid.srp;

import java.util.List;
import java.util.stream.Collectors;

public class UserReportService {
    public String generateCsvReport(List<User> users) {
        String header = "id,name,email";
        String rows = users.stream()
                .map(u -> "%d,%s,%s".formatted(u.id(), u.name(), u.email()))
                .collect(Collectors.joining("\n"));
        return rows.isEmpty() ? header : header + "\n" + rows;
    }

    public long countUsers(List<User> users) {
        return users.size();
    }
}