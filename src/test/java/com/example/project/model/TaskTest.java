package com.example.project.model;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
public class TaskTest {
//"  Write report  " is stored as "Write report".
@Test
void taskCreation() {
    Task task = new Task("  Write report  ", "Finish the quarterly summary");
    assertEquals("Write report", task.getTitle());
}
@Test
void nullDescription() {
    Task task = new Task("Title", null);
    assertEquals("", task.getDescription());
}
@Test
void rejectBlankTitle() {
    Throwable thrown = assertThrows(
        IllegalArgumentException.class,
        () -> new Task("   ", "Description")
    );
    assertNotNull(thrown);
}
@Test
void completeTaskChangesStatus() {
    Task task = new Task("Title", "Description");
    task.complete();
    assertEquals(TaskStatus.COMPLETED, task.getStatus());
}
@Test
void completeTaskAlreadyCompleted() {
    Task task = new Task("Title", "Description");
    task.complete();
    task.complete();
    assertEquals(TaskStatus.COMPLETED, task.getStatus());
}

}