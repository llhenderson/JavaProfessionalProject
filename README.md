# Java Professional Project

This project is a small task management application designed to teach professional Java development practices.

## What you will learn
- Project structure and packages
- Object-oriented programming
- Interfaces and implementations
- Enums and domain modeling
- Collections and in-memory repositories
- Exceptions and validation
- Testing with a simple test harness

## How to run
From the project root, compile and run:

```bash
javac -d out $(find src/main/java -name "*.java")
java -cp out com.example.project.Main
```

## How to test
```bash
javac -cp out -d out-test $(find src/test/java -name "*.java")
java -cp out:out-test com.example.project.service.TaskServiceTest
```
## Prerequisites

- Java 17 or a newer JDK capable of compiling for Java 17

## Build and test

On Windows PowerShell:

```powershell
.\mvnw.cmd clean verify