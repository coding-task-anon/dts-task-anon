package com.demo.dts_dev_challenge.persistence.entity;

import com.demo.dts_dev_challenge.enums.TaskStatus;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity(name = "TASK")
public class TaskEntity {

    @Id
    private long id;

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private LocalDate dueDate;

    @Column
    private TaskStatus taskStatus;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public TaskStatus getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(TaskStatus taskStatus) {
        this.taskStatus = taskStatus;
    }
}
