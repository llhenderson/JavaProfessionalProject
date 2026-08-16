# Java Professional Project

This project is a small task management application designed to teach professional Java development practices.

## Prerequisites

- Java 17 or a newer JDK capable of compiling for Java 17

## Build and test

On Windows PowerShell:

```powershell
.\mvnw.cmd clean verify
```
## Test coverage

Running `.\mvnw.cmd clean verify` generates a JaCoCo coverage report at:

`target\site\jacoco\index.html`

The build requires at least 90% line coverage and 90% branch coverage.

## Project documentation

- [MVP requirements](docs/requirements.md)
- [Task lifecycle decision](docs/adr/0001-task-lifecycle.md)
- [Service error contract](docs/adr/0002-service-error-contract.md)

## Run the console application

```powershell
.\mvnw.cmd clean package
java -jar target\java-professional-project-1.0-SNAPSHOT.jar
```
