package com.demo.dts_dev_challenge.persistence.entity;

import com.demo.dts_dev_challenge.enums.TaskStatus;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity(name = "TASK")
public class TaskEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private long id;

  @Column(name = "TITLE")
  private String title;

  @Column(name = "DESCRIPTION", length = 2000)
  @Lob
  private String description;

  @Column(name = "DUE_DATE")
  private LocalDate dueDate;

  @Column(name = "TASK_STATUS")
  private TaskStatus taskStatus;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
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
