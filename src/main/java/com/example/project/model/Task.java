package com.example.project.model;

import java.util.UUID;

public class Task {
    private final String id;
    private final String title;
    private final String description;
    private TaskStatus status;

    public Task(String title, String description) {
        this.id = UUID.randomUUID().toString();
        this.title = normalizeTitle(title);
        this.description = description == null ? "" : description;
        this.status = TaskStatus.PENDING;
    }

    private static String normalizeTitle(String title) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Title must not be blank");
        }
        return title.strip();
    }

    public void complete() {
        this.status = TaskStatus.COMPLETED;
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


}
