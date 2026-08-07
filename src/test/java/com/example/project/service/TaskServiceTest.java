package com.example.project.service;

import com.example.project.model.Task;
import com.example.project.model.TaskStatus;
import com.example.project.repository.InMemoryTaskRepository;

import java.util.List;
import java.util.Objects;

public class TaskServiceTest {
    public static void main(String[] args) {
        TaskService service = new TaskService(new InMemoryTaskRepository());

        Task created = service.createTask("Write report", "Finish the quarterly summary");
        assertTrue(created.getStatus() == TaskStatus.PENDING, "New tasks should start as pending");
        assertEquals("Write report", created.getTitle(), "Title should be preserved");

        Task completed = service.completeTask(created.getId());
        assertTrue(completed.getStatus() == TaskStatus.COMPLETED, "Completed task should be marked done");

        List<Task> tasks = service.listTasks();
        assertEquals(1, tasks.size(), "One task should be stored after creation");

        service.deleteTask(created.getId());
        assertEquals(0, service.listTasks().size(), "Task should be removed after deletion");

        try {
            service.completeTask("missing-id");
            throw new AssertionError("Missing task should throw an exception");
        } catch (IllegalArgumentException expected) {
            // expected
        }

        System.out.println("All TaskService tests passed.");
    }

    private static void assertTrue(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    private static void assertEquals(Object expected, Object actual, String message) {
        if (!Objects.equals(expected, actual)) {
            throw new AssertionError(message + " Expected=" + expected + " Actual=" + actual);
        }
    }
}
