package com.example.project.service;

import java.util.List;

import com.example.project.exception.TaskNotFoundException;
import com.example.project.model.Task;
import com.example.project.repository.TaskRepository;

public class TaskService {
    private final TaskRepository repository;

    public TaskService(TaskRepository repository) {
        this.repository = repository;
    }

    public Task createTask(String title, String description) {
        return repository.save(new Task(title, description));
    }

    public Task completeTask(String id) {
        Task task = findRequiredTask(id);
        task.complete();
        return repository.save(task);
    }

    public List<Task> listTasks() {
        return repository.findAll();
    }

    public void deleteTask(String id) {
        Task task = findRequiredTask(id);
        repository.deleteById(task.getId());
    }

    private Task findRequiredTask(String id) {
        return repository.findById(id)
        .orElseThrow(() -> new TaskNotFoundException(id));
    }
}
