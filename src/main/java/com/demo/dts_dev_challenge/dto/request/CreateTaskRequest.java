package com.demo.dts_dev_challenge.dto.request;

import com.demo.dts_dev_challenge.enums.TaskStatus;

import java.time.LocalDate;

public record CreateTaskRequest(String name, String description, LocalDate dueDate) {}
