package com.example.project.cli;
import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

import com.example.project.exception.TaskNotFoundException;
import com.example.project.model.Task;
import com.example.project.service.TaskService;


public class TaskConsoleApp {
        private final TaskService taskService;
        private final Scanner scanner;
        private final PrintStream output;

        public TaskConsoleApp(
                TaskService taskService,
                Scanner scanner,
                PrintStream output) {
            this.taskService = taskService;
            this.scanner = scanner;
            this.output = output;
    }

    public void run() {
        boolean running = true;

        while(running) {
            printMenu();
            String option = readLine("Choose an option: ");

            switch(option) {
            case "1" -> createTask();
            case "2" -> listTasks();
            case "3" -> completeTask();
            case "4" -> deleteTask();
            case "0" -> running = false;
            default -> output.println("Unknown option. Please choose 0-4.");
        }
    }
}
private void printMenu() {
    output.println();
    output.println("1. Create task");
    output.println("2. List tasks");
    output.println("3. Complete task");
    output.println("4. Delete task");
    output.println("0. Exit");
}
private String readLine(String prompt) {
    output.print(prompt);
    return scanner.nextLine();
}
private void createTask() {
    try{
        String title = readLine("Title: ");
        String description = readLine("Description (optional): ");

        Task created = taskService.createTask(title, description);
        output.println("Created task: " + created.getId());
    } catch (IllegalArgumentException | TaskNotFoundException e) {
        output.println("Error: " + e.getMessage());
    }
}

private void listTasks() {
    List<Task> tasks = taskService.listTasks();

    if(tasks.isEmpty()) {
        output.println("No tasks found.");
        return;
    }

    for (Task task : tasks) {
        output.println(task.getId() + ": " + task.getTitle()
            + " | " + task.getDescription()
            + " | " + task.getStatus());
    }
    }

private void completeTask() {
    try {
        String id = readLine("Task ID: ");
        Task completedTask = taskService.completeTask(id);
        output.println("Completed task: " + completedTask.getId());
    } catch (IllegalArgumentException | TaskNotFoundException e) {
        output.println("Error: " + e.getMessage());
    }
}

private void deleteTask() {
    try {
        String id = readLine("Task ID: ");
        taskService.deleteTask(id);
        output.println("Deleted task: " + id);
    } catch (IllegalArgumentException | TaskNotFoundException e) {
        output.println("Error: " + e.getMessage());
    }
}
}
