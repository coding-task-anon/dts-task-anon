package com.demo.dts_dev_challenge.service;

import com.demo.dts_dev_challenge.dto.request.CreateTaskRequest;
import com.demo.dts_dev_challenge.dto.request.EditTaskRequest;
import com.demo.dts_dev_challenge.dto.response.TaskResponse;
import com.demo.dts_dev_challenge.enums.TaskStatus;
import com.demo.dts_dev_challenge.persistence.entity.TaskEntity;
import com.demo.dts_dev_challenge.persistence.repository.WorkTaskRespository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class TaskServiceImplTest {

  private static WorkTaskRespository taskRespository;

  private static TaskServiceImpl taskService;

  @BeforeAll
  static void setUp() {
    taskRespository = Mockito.mock(WorkTaskRespository.class);
    taskService = new TaskServiceImpl(taskRespository);
  }

  @Test
  void testSuccessfulReturnTask() {
    TaskEntity taskEntity = getTaskEntity(1L);

    Mockito.when(taskRespository.findById(Mockito.anyLong())).thenReturn(Optional.of(taskEntity));
    TaskResponse taskByID = taskService.getTaskByID(1L);
    Assertions.assertNotNull(taskByID);
    Assertions.assertEquals(1L, taskByID.id());
  }

  @Test
  void testGetAllTasks() {
    List<TaskEntity> taskEntityList = List.of(getTaskEntity(1L), getTaskEntity(2L));
    Mockito.when(taskRespository.findAll()).thenReturn(taskEntityList);
    List<TaskResponse> allTasks = taskService.getAllTasks(null);
    Assertions.assertNotNull(allTasks);
    Assertions.assertEquals(2, allTasks.size());
  }

  @Test
  void testGetAllTasksReturnsEmpty() {
    List<TaskEntity> taskEntityList = new ArrayList<>();
    Mockito.when(taskRespository.findAll()).thenReturn(taskEntityList);
    List<TaskResponse> allTasks = taskService.getAllTasks(null);
    Assertions.assertNotNull(allTasks);
    Assertions.assertTrue(allTasks.isEmpty());
  }

  @Test
  void createTask() {

    Mockito.when(taskRespository.save(Mockito.any(TaskEntity.class))).thenReturn(getTaskEntity(1L));
    TaskResponse task =
        taskService.createTask(
            new CreateTaskRequest("Test Task", "Example test task", LocalDate.of(2025, 12, 25)));
    Assertions.assertNotNull(task);
  }

  @Test
  void editTask(){
    Optional<TaskEntity> taskEntity = Optional.of(getTaskEntity(1L));
    TaskEntity editedEntity = taskEntity.orElseThrow();
    Mockito.when(taskRespository.findById(1L)).thenReturn(taskEntity);
    Mockito.when(taskRespository.save(Mockito.any(TaskEntity.class))).thenReturn(getTaskEntity(1L));
    taskService.editTask(1L, new EditTaskRequest("Test Task", "Example test task", LocalDate.of(2025, 12, 25), TaskStatus.IN_PROGRESS));

  }

  private static TaskEntity getTaskEntity(long id) {
    TaskEntity taskEntity = new TaskEntity();
    taskEntity.setId(id);
    taskEntity.setName("Test Task");
    taskEntity.setDescription("Example test task");
    taskEntity.setTaskStatus(TaskStatus.PENDING);
    taskEntity.setDueDate(LocalDate.of(2025, 12, 25));
    return taskEntity;
  }
}
