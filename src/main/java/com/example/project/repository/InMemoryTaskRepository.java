package com.example.project.repository;

import com.example.project.model.Task;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class InMemoryTaskRepository implements TaskRepository {
    private final Map<String, Task> tasks = new LinkedHashMap<>();

    @Override
    public Task save(Task task) {
        tasks.put(task.getId(), task);
        return task;
    }

    @Override
    public Optional<Task> findById(String id) {
        return Optional.ofNullable(tasks.get(id));
    }

    @Override
    public List<Task> findAll() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public void deleteById(String id) {
        tasks.remove(id);
    }
}
