package com.demo.dts_dev_challenge.dto.request;


import java.time.LocalDate;

public record CreateTaskRequest(String name, String description, LocalDate dueDate) {}
