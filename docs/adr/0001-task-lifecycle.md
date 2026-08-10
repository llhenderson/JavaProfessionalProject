# ADR-0001: Define task lifecycle behavior for the MVP

## Status

Accepted

## Context

The task manager has basic creation, listing, completion, and deletion behavior.
Before expanding it, the application needs explicit rules that tests and future
implementations can follow.

## Decision

- A task has a system-generated UUID identifier.
- A title is required and is trimmed before validation.
- A description is optional and is represented as an empty string when omitted.
- The MVP has two statuses: `PENDING` and `COMPLETED`.
- Completion is idempotent: completing a completed task leaves it completed.
- Tasks are listed in creation order, oldest first.
- An unknown task ID is an error for completion and deletion.
- The `Task` domain object owns its state transition through an intention-revealing
  method such as `complete()`, rather than exposing a general `setStatus()` method.

## Consequences

- Day 4 will add tests and code for whitespace validation, description handling,
  task lifecycle protection, and missing-task deletion.
- Ordering becomes an explicit application promise rather than an accidental
  property of `LinkedHashMap`.
- The application will not add task lookup, editing, priorities, or persistence
  until they have their own requirements.