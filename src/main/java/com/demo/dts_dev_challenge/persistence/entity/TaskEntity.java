package com.demo.dts_dev_challenge.persistence.entity;

import com.demo.dts_dev_challenge.enums.TaskStatus;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity(name = "TASK")
public class TaskEntity {

    @Id
    public long id;

    @Column
    public String name;

    @Column
    public String description;

    @Column
    public LocalDate dueDate;

    @Column
    public TaskStatus taskStatus;
}
