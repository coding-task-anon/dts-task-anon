package com.demo.dts_dev_challenge.service;

import com.demo.dts_dev_challenge.dto.request.CreateTaskRequest;
import com.demo.dts_dev_challenge.dto.response.TaskResponse;
import com.demo.dts_dev_challenge.enums.TaskStatus;

import java.util.List;

public interface TaskService {

    TaskResponse createTask(CreateTaskRequest createTaskRequest);

    TaskResponse getTaskByID(long id);

    List<TaskResponse> getTasksByStatus(TaskStatus taskStatus);

    List<TaskResponse> getAllTaskSummaries();
}
