package com.demo.dts_dev_challenge.dto.request;


import java.time.LocalDate;

public record CreateTaskRequest(String title, String description, LocalDate dueDate) {

    public CreateTaskRequest{
        if (title.isBlank()) {
            throw new IllegalArgumentException("Required Field");
        }
        if(title.length() > 255){
            throw new IllegalArgumentException("Title length cannot exceed 255");
        }
        if(description != null && description.length() > 2000){
            throw new IllegalArgumentException("Title length cannot exceed 2000 characters");
        }
        if (dueDate == null || dueDate.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Due date is required and must be in the future");
        }
    }
}
