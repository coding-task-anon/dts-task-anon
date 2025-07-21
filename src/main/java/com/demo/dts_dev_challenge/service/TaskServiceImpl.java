package com.demo.dts_dev_challenge.service;

import com.demo.dts_dev_challenge.dto.request.CreateTaskRequest;
import com.demo.dts_dev_challenge.dto.request.EditTaskRequest;
import com.demo.dts_dev_challenge.dto.response.TaskResponse;
import com.demo.dts_dev_challenge.enums.TaskStatus;
import com.demo.dts_dev_challenge.persistence.entity.TaskEntity;
import com.demo.dts_dev_challenge.persistence.repository.WorkTaskRespository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.stereotype.Service;

@Service
public class TaskServiceImpl implements TaskService {

  private final WorkTaskRespository taskRespository;

  public TaskServiceImpl(WorkTaskRespository taskRespository) {
    this.taskRespository = taskRespository;
  }

  @Transactional
  @Override
  public TaskResponse createTask(CreateTaskRequest createTaskRequest) {
    TaskEntity entity = new TaskEntity();
    entity.setName(createTaskRequest.name());
    entity.setDescription(createTaskRequest.description());
    entity.setDueDate(createTaskRequest.dueDate());
    entity.setTaskStatus(TaskStatus.PENDING);
    TaskEntity saved = taskRespository.save(entity);
    return this.buildTaskResponse(saved);
  }

  @Override
  public TaskResponse getTaskByID(long id) {
    Optional<TaskEntity> taskEntity = taskRespository.findById(id);
    return taskEntity
        .map(this::buildTaskResponse)
        .orElseThrow(
            () ->
                new DataAccessResourceFailureException("Task not found for id {%s}".formatted(id)));
  }

  private TaskResponse buildTaskResponse(TaskEntity taskEntity) {
    return new TaskResponse(
        taskEntity.getId(),
        taskEntity.getName(),
        taskEntity.getDescription(),
        taskEntity.getTaskStatus(),
        taskEntity.getDueDate());
  }

  @Override
  public List<TaskResponse> getAllTasks() {
    return taskRespository.findAll().stream().map(this::buildTaskResponse).toList();
  }

  @Transactional
  @Override
  public TaskResponse editTask(long id, EditTaskRequest editTask) {
    Optional<TaskEntity> taskToBeUpdated = taskRespository.findById(id);
    TaskEntity entity =
        taskToBeUpdated
            .map(taskEntity -> updateTask(taskEntity, editTask))
            .orElseThrow(
                () ->
                    new DataAccessResourceFailureException(
                        "Task not found for id {%s}".formatted(id)));

    return this.buildTaskResponse(entity);
  }

  @Transactional
  @Override
  public Map<String, Integer> deleteTask(long id) {
    int recordDelete = taskRespository.deleteById(id);
    if (recordDelete == 0) {
      throw new IllegalArgumentException("No task with id : {%}".formatted(id));
    }
    return Map.of("Record Deleted", recordDelete);
  }

  private TaskEntity updateTask(TaskEntity taskEntity, EditTaskRequest task) {
    if (!task.name().equals(taskEntity.getName())) {
      taskEntity.setName(task.name());
    }
    if (!task.description().equals(taskEntity.getDescription())) {
      taskEntity.setDescription(task.description());
    }
    if (!task.taskStatus().equals(taskEntity.getTaskStatus())) {
      taskEntity.setTaskStatus(task.taskStatus());
    }
    if (!task.dueDate().equals(taskEntity.getDueDate())) {
      taskEntity.setDueDate(task.dueDate());
    }
    return taskEntity;
  }
}
