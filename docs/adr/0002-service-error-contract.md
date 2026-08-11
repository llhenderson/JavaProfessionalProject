# ADR-0002: Distinguish missing-task errors from invalid input

## Status

Accepted

## Context

The service must distinguish invalid task data from attempts to operate on a task that does not exist.

## Decision

- Invalid task data, such as a blank title, uses `IllegalArgumentException`.
- Missing task IDs use `TaskNotFoundException`.
- `TaskNotFoundException` extends `RuntimeException`.
- The exception message includes the requested task ID.

## Consequences

- Callers can handle validation failures differently from missing-task failures.
- A future REST layer can map these errors to different HTTP responses.
- The service remains free of web-framework concerns.
