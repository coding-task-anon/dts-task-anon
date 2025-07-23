package com.demo.dts_dev_challenge.integration;

import com.demo.dts_dev_challenge.controller.TasksController;
import com.demo.dts_dev_challenge.dto.request.CreateTaskRequest;
import com.demo.dts_dev_challenge.dto.request.EditTaskRequest;
import com.demo.dts_dev_challenge.dto.response.TaskResponse;
import com.demo.dts_dev_challenge.enums.TaskStatus;
import com.demo.dts_dev_challenge.persistence.entity.TaskEntity;
import com.demo.dts_dev_challenge.persistence.repository.WorkTaskRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Transactional
public class TaskIntegrationTest {

  @Autowired WorkTaskRepository repository;

  @Autowired private TasksController tasksController;

  @BeforeEach
  void setUp() {
    repository.deleteAll();
  }

  @Test
  void createTask() {
    CreateTaskRequest createTaskRequest =
        new CreateTaskRequest("Test Title", "Test Description", LocalDate.of(2026, 12, 25));
    TaskResponse task = tasksController.createTask(createTaskRequest);
    Assertions.assertNotNull(task.id());
    Assertions.assertEquals("Test Title", task.name());
    Assertions.assertEquals(TaskStatus.PENDING, task.taskStatus());
  }

  @Test
  void editTask() {
    TaskEntity taskEntity = getTaskEntity("Test Title");
    TaskEntity saved = repository.save(taskEntity);
    long id = saved.getId();
    EditTaskRequest editTaskRequest =
        new EditTaskRequest("Updated Title", null, null, TaskStatus.IN_PROGRESS);
    TaskResponse taskResponse = tasksController.editExistingTask(id, editTaskRequest);
    Assertions.assertNotNull(taskResponse);
    Assertions.assertEquals("Updated Title", taskResponse.name());
    Assertions.assertEquals(TaskStatus.IN_PROGRESS, taskResponse.taskStatus());
  }

  @Test
  void deleteTask() {
    TaskEntity taskEntity = getTaskEntity("Test Title");
    TaskEntity saved = repository.save(taskEntity);
    long id = saved.getId();
    Map<String, Integer> stringIntegerMap = tasksController.deleteTask(id);
    Assertions.assertTrue(stringIntegerMap.containsKey("Records Deleted"));
    Assertions.assertEquals(1, stringIntegerMap.get("Records Deleted").longValue());
  }

  @Test
  void deleteTask_InvalidId() {
    TaskEntity taskEntity = getTaskEntity("Test Title");
    repository.save(taskEntity);
    IllegalArgumentException illegalArgumentException =
        Assertions.assertThrows(
            IllegalArgumentException.class, () -> tasksController.deleteTask(3));
    Assertions.assertEquals("No task with id : {3}", illegalArgumentException.getMessage());
  }

  @Test
  void findAllTask(){
    TaskEntity taskOne = getTaskEntity("Test 1");
    TaskEntity taskTwo = getTaskEntity("Test 2");
    TaskEntity taskThree = getTaskEntity("Test 3");

    repository.save(taskOne);
    repository.save(taskTwo);
    repository.save(taskThree);

    List<TaskResponse> allTasks = tasksController.getAllTasks();
    Assertions.assertEquals(3, allTasks.size());
    Assertions.assertEquals(TaskStatus.PENDING, allTasks.get(0).taskStatus());
  }

  @Test
  void findTaskById(){
    TaskEntity taskOne = getTaskEntity("Test 1");
    TaskEntity saved = repository.save(taskOne);
    TaskResponse taskById = tasksController.getTaskById(saved.getId());
    Assertions.assertEquals("Test 1", taskById.name());


  }

  private static TaskEntity getTaskEntity(String testTitle) {
    TaskEntity taskEntity = new TaskEntity();
    taskEntity.setName(testTitle);
    taskEntity.setDescription("Test Description");
    taskEntity.setTaskStatus(TaskStatus.PENDING);
    taskEntity.setDueDate(LocalDate.of(2025, 1, 1));
    return taskEntity;
  }
}
