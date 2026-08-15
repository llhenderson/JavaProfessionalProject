package com.example.project.cli;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.project.model.Task;
import com.example.project.model.TaskStatus;
import com.example.project.repository.InMemoryTaskRepository;
import com.example.project.service.TaskService;

public class TaskConsoleAppTest {

    private TaskService taskService;

    @BeforeEach
    void setUp() {
        taskService = new TaskService(new InMemoryTaskRepository());
    }

    private String runApp(String input) {
        ByteArrayOutputStream outputBuffer = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(outputBuffer);

        TaskConsoleApp app = new TaskConsoleApp(
                taskService,
                new Scanner(new StringReader(input)),
                output
        );
        app.run();
        return outputBuffer.toString(StandardCharsets.UTF_8);
    }

    @Test
    void run_withInvalidOption_showsHelpfulMessage() {
        String output = runApp("9\n0\n");
        assertTrue(output.contains("Unknown option. Please choose 0-4."));
    }

    @Test
    void run_createsAndListsTask() {
        String output = runApp(
                "1\n"
                + "Test Task\n"
                + "Create a test\n"
                + "2\n"
                + "0\n"
        );
        List<Task> tasks = taskService.listTasks();
        assertEquals(1, tasks.size());

        Task task = tasks.get(0);
        assertEquals("Test Task", task.getTitle());
        assertEquals("Create a test", task.getDescription());
        assertEquals(TaskStatus.PENDING, task.getStatus());

        assertTrue(output.contains("Created task: "));
        assertTrue(output.contains("Test Task"));
        assertTrue(output.contains("Create a test"));
        assertTrue(output.contains("PENDING"));
    }

    @Test
    void run_completesExistingTask() {
        Task task = taskService.createTask("Homework", "Finish math exercises");
        String output = runApp(
                "3\n"
                + task.getId() + "\n"
                + "0\n"
        );
        Task updatedTask = taskService.listTasks().get(0);

        assertEquals(TaskStatus.COMPLETED, updatedTask.getStatus());
        assertTrue(output.contains("Completed task: " + task.getId()));
    }

    @Test
    void run_deletesExistingTask() {
        Task task = taskService.createTask("Chores", "Clean the house");
        String output = runApp(
                "4\n"
                + task.getId() + "\n"
                + "0\n"
        );
        List<Task> tasks = taskService.listTasks();
        assertEquals(0, tasks.size());
        assertTrue(output.contains("Deleted task: " + task.getId()));
    }

    @Test
    void runWithUnknownIdToComplete_showsHelpfulError() {
        String output = runApp(
                "3\nmissing-id\n0\n"
        );
        assertTrue(output.contains("Error: Task not found: missing-id"));
    }

    @Test

    void runWithUnknownIdToDelete_showsHelpfulError() {
        String output = runApp("4\nmissing-id\n0\n");
        assertTrue(output.contains("Error: Task not found: missing-id"));
    }

    @Test
    void runWithBlankTitle_showsHelpfulError() {
        String output = runApp(
                "1\n"
                + "   \n"
                + "Description\n"
                + "0\n"
        );
        assertTrue(output.contains("Error: Title must not be blank"));
        assertTrue(taskService.listTasks().isEmpty());
    }

    @Test
    void run_withNoTasks_showsEmptyListMessage() {
        String output = runApp("2\n0\n");
        assertTrue(output.contains("No tasks found."));
    }
}
