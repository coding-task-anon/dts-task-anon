package com.demo.dts_dev_challenge.persistence.repository;

import com.demo.dts_dev_challenge.enums.TaskStatus;
import com.demo.dts_dev_challenge.persistence.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkTaskRespository extends JpaRepository<TaskEntity, Long> {

    List<TaskEntity> findAllByTaskStatus(TaskStatus taskStatus);

    @Modifying
    @Query("DELETE FROM TASK t WHERE t.id = :id")
    int deleteById(@Param("id") long id);
}
