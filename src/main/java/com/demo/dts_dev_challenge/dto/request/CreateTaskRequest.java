package com.demo.dts_dev_challenge.dto.request;


import java.time.LocalDate;

public record CreateTaskRequest(String name, String description, LocalDate dueDate) {

    public CreateTaskRequest{
        if (name.isBlank()) {
            throw new IllegalArgumentException("Required Field");
        }
        if (dueDate == null || dueDate.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Due date is required and must be in the future");
        }
    }
}
