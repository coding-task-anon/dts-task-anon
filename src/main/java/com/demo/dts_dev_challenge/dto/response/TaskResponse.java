package com.demo.dts_dev_challenge.dto.response;

import com.demo.dts_dev_challenge.enums.TaskStatus;
import java.time.LocalDate;

public record TaskResponse(
        Long id, String title, String description, TaskStatus taskStatus, LocalDate dueDate) {}
