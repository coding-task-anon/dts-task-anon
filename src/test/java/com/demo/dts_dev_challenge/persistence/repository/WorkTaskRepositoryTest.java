package com.demo.dts_dev_challenge.persistence.repository;

import com.demo.dts_dev_challenge.enums.TaskStatus;
import com.demo.dts_dev_challenge.persistence.entity.TaskEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class WorkTaskRepositoryTest {


    @Autowired
    WorkTaskRepository repository;

    @Autowired
    private TestEntityManager entityManager;

    @BeforeEach
    void setUp() {
        repository.deleteAll();
        entityManager.flush(); // Ensure deletion is committed
    }


    @Test
    void testCreateTask(){
        TaskEntity taskEntity = getTaskEntity("Test Title");
        TaskEntity saved = repository.save(taskEntity);
        Assertions.assertNotNull(saved);
        Assertions.assertEquals("Test Title", saved.getTitle());

        // Verify it can be found in database
        Optional<TaskEntity> foundTask = repository.findById(saved.getId());
        Assertions.assertTrue(foundTask.isPresent());
        Assertions.assertEquals("Test Title", foundTask.get().getTitle());
    }

    @Test
    void testDeleteTask(){
        TaskEntity taskEntity = getTaskEntity("Test Title");
        TaskEntity saved = entityManager.persistAndFlush(taskEntity);
        Assertions.assertNotNull(saved);
        long id = saved.getId();

        int recordsDeleted = repository.deleteById(id);
        entityManager.flush();
        entityManager.clear();
        Optional<TaskEntity> queryResponse = repository.findById(id);

        Assertions.assertEquals(1, recordsDeleted);
        Assertions.assertFalse(queryResponse.isPresent());
    }

    @Test
    void findAll(){
        TaskEntity taskOne = getTaskEntity("Test 1");
        TaskEntity taskTwo = getTaskEntity("Test 2");
        TaskEntity taskThree = getTaskEntity("Test 3");

        entityManager.persist(taskOne);
        entityManager.persist(taskTwo);
        entityManager.persist(taskThree);

        List<TaskEntity> taskEntities = repository.findAll();
        Assertions.assertEquals(3, taskEntities.size());

    }

    private static TaskEntity getTaskEntity(String testTitle) {
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setTitle(testTitle);
        taskEntity.setDescription("Test Description");
        taskEntity.setTaskStatus(TaskStatus.PENDING);
        taskEntity.setDueDate(LocalDate.of(2025, 1, 1));
        return taskEntity;
    }

}
