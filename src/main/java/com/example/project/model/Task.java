package com.example.project.model;

import java.util.UUID;

public class Task {
    private final String id;
    private final String title;
    private final String description;
    private TaskStatus status;

    public Task(String title, String description) {
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.description = description;
        this.status = TaskStatus.PENDING;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }
}
