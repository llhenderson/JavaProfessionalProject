package com.example.project;
import java.util.Scanner;

import com.example.project.cli.TaskConsoleApp;
import com.example.project.repository.InMemoryTaskRepository;
import com.example.project.repository.TaskRepository;
import com.example.project.service.TaskService;
public class Main {
    public static void main(String[] args) {
        TaskRepository repository = new InMemoryTaskRepository();
        TaskService service = new TaskService(repository);
        TaskConsoleApp app = new TaskConsoleApp(service, new Scanner(System.in), System.out);
        app.run();
    }
}
