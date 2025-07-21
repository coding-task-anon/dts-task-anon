package com.demo.dts_dev_challenge.controller;

import com.demo.dts_dev_challenge.dto.request.CreateTaskRequest;
import com.demo.dts_dev_challenge.dto.request.EditTaskRequest;
import com.demo.dts_dev_challenge.dto.response.TaskResponse;
import com.demo.dts_dev_challenge.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/tasks")
public class TasksController {

  private final TaskService taskService;

  public TasksController(TaskService taskService) {
    this.taskService = taskService;
  }

  @Operation(description = "Get Task")
  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public TaskResponse getTaskById(@PathVariable long id) {
    log.info("Get task for id {%s}".formatted(id));
    return taskService.getTaskByID(id);
  }

  @Operation(description = "Create Task")
  @PostMapping(value = ("/"), produces = MediaType.APPLICATION_JSON_VALUE)
  public TaskResponse createTask(@RequestBody CreateTaskRequest createTaskRequest) {
    log.info(createTaskRequest.toString());
    return taskService.createTask(createTaskRequest);
  }

  @Operation(description = "Get All Tasks")
  @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<TaskResponse> getAllTasks() {
    return taskService.getAllTasks();
  }

  @Operation(description = "Edit an existing task")
  @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public TaskResponse editExistingTask(
      @PathVariable final long id, @RequestBody final EditTaskRequest editTaskRequest) {
    return taskService.editTask(id, editTaskRequest);
  }

  @Operation(description = "Delete task by id")
  @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Map<String, Integer> deleteTask(@PathVariable final long id) {
    log.info("Delete called for id {%s}".formatted(id));
    return taskService.deleteTask(id);
  }
}
