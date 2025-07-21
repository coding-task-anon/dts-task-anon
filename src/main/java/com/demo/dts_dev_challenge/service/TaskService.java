package com.demo.dts_dev_challenge.service;

import com.demo.dts_dev_challenge.dto.request.CreateTaskRequest;
import com.demo.dts_dev_challenge.dto.request.EditTaskRequest;
import com.demo.dts_dev_challenge.dto.response.TaskResponse;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Map;

public interface TaskService {

  TaskResponse createTask(CreateTaskRequest createTaskRequest);

  TaskResponse getTaskByID(long id);

  List<TaskResponse> getAllTasks();

  TaskResponse editTask(long id, EditTaskRequest editTaskRequest);

  @Transactional
  Map<String, Integer> deleteTask(long id);
}
