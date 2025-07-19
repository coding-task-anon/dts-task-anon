package com.demo.dts_dev_challenge.service;


import com.demo.dts_dev_challenge.dto.response.TaskResponse;
import com.demo.dts_dev_challenge.persistence.entity.TaskEntity;
import com.demo.dts_dev_challenge.persistence.repository.WorkTaskRespository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Optional;

class TaskServiceImplTest {


    private static WorkTaskRespository taskRespository;

    private static TaskServiceImpl taskService;

    @BeforeAll
    static void setUp(){
        taskRespository = Mockito.mock(WorkTaskRespository.class);
        taskService = new TaskServiceImpl(taskRespository);
    }

    @Test
    void testSuccessfulReturnTask(){
        TaskEntity taskEntity = new TaskEntity();

        Mockito.when(taskRespository.findById(Mockito.anyLong())).thenReturn(Optional.of(taskEntity));
        TaskResponse taskByID = taskService.getTaskByID(1L);
        Assertions.assertNotNull(taskByID);
        Assertions.assertEquals(1L, taskByID.id());
    }


}
