# Task Manager MVP Requirements

## Product goal

Provide a simple task manager where a user can create, view, complete, and delete tasks.

## In scope

- Create a task
- List tasks
- Complete a task
- Delete a task
- Clear validation and not-found feedback

## Out of scope

- User accounts
- Due dates
- Priorities
- Editing tasks
- Reopening completed tasks
- Database persistence
- Web or graphical interface

## User stories

### US-01: Create a task

As a user, I want to create a task with a title and optional description so that I can record work I need to do.

Acceptance criteria:

- A title is required after leading and trailing whitespace is removed.
- A new task starts with status `PENDING`.
- The system generates the task ID.
- An omitted description is stored as an empty value rather than `null`.

### US-02: List tasks

As a user, I want to list my tasks so that I can see what I need to do.

Acceptance criteria:

- An empty task list is valid.
- Tasks are listed in creation order, oldest first.
- Each result includes its ID, title, description, and status.

### US-03: Complete a task

As a user, I want to mark a task complete so that I can track finished work.

Acceptance criteria:

- A pending task becomes `COMPLETED`.
- Completing an already completed task leaves it completed.
- Completing an unknown ID reports that the task was not found.

### US-04: Delete a task

As a user, I want to delete a task so that I can remove work I no longer need.

Acceptance criteria:

- Deleting an existing task removes it from future lists.
- Deleting an unknown ID reports that the task was not found.

### US-05: Receive clear feedback

As a user, I want invalid requests explained clearly so that I can correct them.

Acceptance criteria:

- Blank or whitespace-only titles are rejected.
- Invalid input does not create a task.
- Unknown task IDs produce a clear not-found result.