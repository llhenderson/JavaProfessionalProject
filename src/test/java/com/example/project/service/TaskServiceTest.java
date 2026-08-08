package com.example.project.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.project.model.Task;
import com.example.project.model.TaskStatus;
import com.example.project.repository.InMemoryTaskRepository;

class TaskServiceTest {

    private TaskService service;

    @BeforeEach
    void setUp() {
        service = new TaskService(new InMemoryTaskRepository());
    }

    @Test
    void createTask_startsAsPending() {
        Task created = service.createTask(
                "Write report",
                "Finish the quarterly summary"
        );

        assertEquals(TaskStatus.PENDING, created.getStatus());
    }
    @Test
    void createTask_preservesProvidedTitleAndDescription() {
        String title = "Write report";
        String description = "Finish the quarterly summary"; 

        Task created = service.createTask(title, description);
        assertEquals(title, created.getTitle());
        assertEquals(description, created.getDescription());
    }
    @Test 
    void completeTask_marksTaskAsCompleted() {
        Task created = service.createTask(
                "Write report",
                "Finish the quarterly summary"
        );

        service.completeTask(created.getId());

        Task completed = service.completeTask(created.getId());

        assertEquals(TaskStatus.COMPLETED, completed.getStatus());
    }
    @Test 
    
    void listTasks_returnsCreatedTasks() {
        Task task1 = service.createTask("Task 1", "Description 1");
        Task task2 = service.createTask("Task 2", "Description 2");

        var tasks = service.listTasks();

        assertEquals(2, tasks.size());
        assertTrue(tasks.stream()
                .anyMatch(task -> task.getId().equals(task1.getId())));
        assertTrue(tasks.stream()
                .anyMatch(task -> task.getId().equals(task2.getId())));
    }
    @Test 
    void deleteTask_removesTask(){
        Task task = service.createTask("Task to delete", "Decsprition");
        service.deleteTask(task.getId());
        assertEquals(0,service.listTasks().size());
    }
    @Test
    void createTask_withBlankTitle_throwsIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> service.createTask("", "Description")
        );

        assertEquals("Title must not be blank", exception.getMessage());
    }

    @Test
    void createTask_withNullTitle_throwsIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> service.createTask(null, "Description")
        );

        assertEquals("Title must not be blank", exception.getMessage());
    }

    @Test
    void completeTask_withUnknownId_throwsIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> service.completeTask("missing-id")
        );

        assertEquals("Task not found: missing-id", exception.getMessage());
    }
}