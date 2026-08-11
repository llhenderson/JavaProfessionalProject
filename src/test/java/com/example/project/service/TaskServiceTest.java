package com.example.project.service;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.project.exception.TaskNotFoundException;
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

        Task completed = service.completeTask(created.getId());

        assertEquals(TaskStatus.COMPLETED, completed.getStatus());
    }
    @Test 
    
    void listTasks_returnsCreatedTasks() {
        Task task1 = service.createTask("Task 1", "Description 1");
        Task task2 = service.createTask("Task 2", "Description 2");

        var tasks = service.listTasks();

        assertEquals(2, tasks.size());
        assertEquals(
            List.of(task1.getId(), task2.getId()),
            tasks.stream().map(Task::getId).toList()
        );
    }

    @Test 
    void deleteTask_removesTask(){
        Task task = service.createTask("Task to delete", "Description");
        service.deleteTask(task.getId());
        assertEquals(0,service.listTasks().size());
    }
    @Test
    void createTask_withBlankTitle_throwsIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> service.createTask(" ", "Description")
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
        TaskNotFoundException exception = assertThrows(
                TaskNotFoundException.class,
                () -> service.completeTask("missing-id")
        );

        assertEquals("Task not found: missing-id", exception.getMessage());
    }
    @Test
    void createTask_trimsTitle() {
        Task created = service.createTask(
            "   Write report   ",
            "Finish the quarterly summary"
        );
        assertEquals("Write report", created.getTitle());
    }
    @Test
    void createTask_withNullDescription_usesEmptyDescription() {
        Task created = service.createTask(
            "Write report",
            null
        );
        assertEquals("", created.getDescription());
    }
    @Test
    void completeTask_isIdempotent() {
        Task created = service.createTask(
            "Write report",
            "Finish the quarterly summary"
        );
        Task completed1 = service.completeTask(created.getId());
        Task completed2 = service.completeTask(created.getId());

        assertEquals(TaskStatus.COMPLETED, completed1.getStatus());
        assertEquals(TaskStatus.COMPLETED, completed2.getStatus());
    }
    @Test
    void deleteTask_withUnknownId_throwsIllegalArgumentException() {
        TaskNotFoundException exception = assertThrows(
                TaskNotFoundException.class,
                () -> service.deleteTask("missing-id")
        );

        assertEquals("Task not found: missing-id", exception.getMessage());
    }
}
