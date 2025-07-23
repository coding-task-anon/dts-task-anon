package com.demo.dts_dev_challenge.dto.request;

import com.demo.dts_dev_challenge.enums.TaskStatus;
import java.time.LocalDate;

public record EditTaskRequest(
    String name, String description, LocalDate dueDate, TaskStatus taskStatus) {}
