package com.example.project.service;

import java.util.List;

import com.example.project.model.Task;
import com.example.project.model.TaskStatus;
import com.example.project.repository.TaskRepository;

public class TaskService {
    private final TaskRepository repository;

    public TaskService(TaskRepository repository) {
        this.repository = repository;
    }

    public Task createTask(String title, String description) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Title must not be blank");
        }

        Task task = new Task(title, description);
        return repository.save(task);
    }

    public Task completeTask(String id) {
        Task task = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Task not found: " + id));
        task.setStatus(TaskStatus.COMPLETED);
        return repository.save(task);
    }

    public List<Task> listTasks() {
        return repository.findAll();
    }

    public void deleteTask(String id) {
        repository.deleteById(id);
    }
}
