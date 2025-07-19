package com.demo.dts_dev_challenge.service;

import com.demo.dts_dev_challenge.dto.request.CreateTaskRequest;
import com.demo.dts_dev_challenge.dto.response.TaskResponse;
import com.demo.dts_dev_challenge.enums.TaskStatus;
import com.demo.dts_dev_challenge.persistence.repository.WorkTaskRespository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService{

    private final WorkTaskRespository taskRespository;

    public TaskServiceImpl(WorkTaskRespository taskRespository) {
        this.taskRespository = taskRespository;
    }

    @Override
    public TaskResponse createTask(CreateTaskRequest createTaskRequest) {
        return null;
    }

    @Override
    public TaskResponse getTaskByID(long id) {
        return null;
    }

    @Override
    public List<TaskResponse> getTasksByStatus(TaskStatus taskStatus) {
        return List.of();
    }

    @Override
    public List<TaskResponse> getAllTaskSummaries() {
        return List.of();
    }
}
