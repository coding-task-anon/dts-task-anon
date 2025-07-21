package com.demo.dts_dev_challenge.persistence.repository;

import com.demo.dts_dev_challenge.persistence.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkTaskRespository extends JpaRepository<TaskEntity, Long> {

  @Modifying
  @Query("DELETE FROM TASK t WHERE t.id = :id")
  int deleteById(@Param("id") long id);
}
