package com.demo.dts_dev_challenge.controller;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.demo.dts_dev_challenge.dto.request.CreateTaskRequest;
import com.demo.dts_dev_challenge.dto.request.EditTaskRequest;
import com.demo.dts_dev_challenge.dto.response.TaskResponse;
import com.demo.dts_dev_challenge.enums.TaskStatus;
import com.demo.dts_dev_challenge.service.TaskService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(TasksController.class)
class TasksControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TaskService taskService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getTaskById_ShouldReturnTask_WhenTaskExists() throws Exception {
        // Given
        Long taskId = 1L;
        TaskResponse taskResponse = createSampleTaskResponse(taskId, "Sample Task", TaskStatus.PENDING);

        when(taskService.getTaskByID(taskId)).thenReturn(taskResponse);

        // When & Then
        mockMvc.perform(get("/tasks/{id}", taskId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(taskId))
                .andExpect(jsonPath("$.name").value("Sample Task"))
                .andExpect(jsonPath("$.taskStatus").value("PENDING"))
                .andExpect(jsonPath("$.description").value("Sample description"));
    }

    @Test
    void createTask_ShouldReturnCreatedTask_WhenValidRequest() throws Exception {
        // Given
        CreateTaskRequest request = new CreateTaskRequest("New Task", "New task description", LocalDate.of(2026, 1, 1));
        TaskResponse taskResponse = createSampleTaskResponse(1L, "New Task", TaskStatus.PENDING);

        when(taskService.createTask(any(CreateTaskRequest.class))).thenReturn(taskResponse);

        // When & Then
        mockMvc.perform(post("/tasks/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("New Task"))
                .andExpect(jsonPath("$.taskStatus").value("PENDING"))
                .andExpect(jsonPath("$.description").value("Sample description"));
    }

    @Test
    void getAllTasks_ShouldReturnAllTasks() throws Exception {
        // Given
        List<TaskResponse> tasks = Arrays.asList(
                createSampleTaskResponse(1L, "Task 1", TaskStatus.PENDING),
                createSampleTaskResponse(2L, "Task 2", TaskStatus.IN_PROGRESS),
                createSampleTaskResponse(3L, "Task 3", TaskStatus.COMPLETED)
        );

        when(taskService.getAllTasks()).thenReturn(tasks);

        // When & Then
        mockMvc.perform(get("/tasks/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("Task 1"))
                .andExpect(jsonPath("$[0].taskStatus").value("PENDING"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].name").value("Task 2"))
                .andExpect(jsonPath("$[1].taskStatus").value("IN_PROGRESS"))
                .andExpect(jsonPath("$[2].id").value(3L))
                .andExpect(jsonPath("$[2].name").value("Task 3"))
                .andExpect(jsonPath("$[2].taskStatus").value("COMPLETED"));
    }



    @Test
    void editExistingTask_ShouldReturnUpdatedTask_WhenValidRequest() throws Exception {
        // Given
        Long taskId = 1L;
        EditTaskRequest editRequest = 
                new EditTaskRequest(
                "Updated Task", 
                "Updated description",
                LocalDate.of(2026, 1, 1), 
                TaskStatus.IN_PROGRESS);


        TaskResponse updatedTask = createSampleTaskResponse(taskId, "Updated Task", TaskStatus.IN_PROGRESS);

        when(taskService.editTask(eq(taskId), any(EditTaskRequest.class))).thenReturn(updatedTask);

        // When & Then
        mockMvc.perform(put("/tasks/{id}", taskId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(editRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(taskId))
                .andExpect(jsonPath("$.name").value("Updated Task"))
                .andExpect(jsonPath("$.taskStatus").value("IN_PROGRESS"));
    }

    @Test
    void deleteTask_ShouldReturnDeletedTaskId_WhenTaskExists() throws Exception {
        // Given
        Long taskId = 1L;
        Map<String, Integer> deleteResponse = Map.of("deletedTaskId", taskId.intValue());

        when(taskService.deleteTask(taskId)).thenReturn(deleteResponse);

        // When & Then
        mockMvc.perform(delete("/tasks/{id}", taskId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.deletedTaskId").value(taskId.intValue()));
    }

    @Test
    void getAllTasks_ShouldReturnEmptyList_WhenNoTasksExist() throws Exception {
        // Given
        when(taskService.getAllTasks()).thenReturn(Arrays.asList());

        // When & Then
        mockMvc.perform(get("/tasks/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void createTask_ShouldHandleRequestWithoutBody() throws Exception {
        // When & Then
        mockMvc.perform(post("/tasks/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void editExistingTask_ShouldHandleRequestWithoutBody() throws Exception {
        // When & Then
        mockMvc.perform(put("/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    // Helper method to create sample TaskResponse objects
    private TaskResponse createSampleTaskResponse(Long id, String title, TaskStatus status) {
        return new TaskResponse(id, title, "Sample description", status, LocalDate.now());
    }
}