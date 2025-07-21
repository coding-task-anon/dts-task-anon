package com.demo.dts_dev_challenge.dto.request;

import com.demo.dts_dev_challenge.enums.TaskStatus;
import java.time.LocalDate;

public record EditTaskRequest(
    String name, String description, LocalDate dueDate, TaskStatus taskStatus) {

    public EditTaskRequest {
        if (name.isBlank()) {
            throw new IllegalArgumentException("Required Field");
        }
        if (dueDate == null || dueDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Due date is required and must be in the future");

        }
        if (taskStatus == null) {
            throw new IllegalArgumentException("Task Status Required");
        }
    }
}
