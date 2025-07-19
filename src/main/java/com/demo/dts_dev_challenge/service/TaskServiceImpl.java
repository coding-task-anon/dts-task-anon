package com.demo.dts_dev_challenge.service;

import com.demo.dts_dev_challenge.dto.request.CreateTaskRequest;
import com.demo.dts_dev_challenge.dto.response.TaskResponse;
import com.demo.dts_dev_challenge.enums.TaskStatus;
import com.demo.dts_dev_challenge.persistence.entity.TaskEntity;
import com.demo.dts_dev_challenge.persistence.repository.WorkTaskRespository;
import java.util.List;
import java.util.Optional;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.stereotype.Service;

@Service
public class TaskServiceImpl implements TaskService {

  private final WorkTaskRespository taskRespository;

  public TaskServiceImpl(WorkTaskRespository taskRespository) {
    this.taskRespository = taskRespository;
  }

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
  public List<TaskResponse> getTasksByStatus(TaskStatus taskStatus) {
    return List.of();
  }

  @Override
  public List<TaskResponse> getAllTasks() {
    List<TaskEntity> respositoryAll = taskRespository.findAll();
    return respositoryAll.stream().map(this::buildTaskResponse).toList();
  }
}
