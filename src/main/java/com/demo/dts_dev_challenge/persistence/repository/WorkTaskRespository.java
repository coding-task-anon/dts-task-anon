package com.demo.dts_dev_challenge.persistence.repository;

import com.demo.dts_dev_challenge.persistence.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkTaskRespository extends JpaRepository<TaskEntity, Long> {}
