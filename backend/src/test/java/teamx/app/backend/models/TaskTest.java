package teamx.app.backend.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

    private Task task;
    private Project project;
    private User personalTodoListOwner;

    @BeforeEach
    void setUp() {
        project = new Project();
        personalTodoListOwner = new User();

        task = Task.builder()
                .id(1L)
                .taskOrder(1)
                .name("Sample Task")
                .description("Sample Description")
                .deadline(new Date(System.currentTimeMillis()))
                .status(Task.Status.TODO)
                .project(project)
                .personalTodoListOwner(personalTodoListOwner)
                .build();
    }

    @Test
    void testTaskGettersAndSetters() {
        assertEquals(1L, task.getId());
        assertEquals(1, task.getTaskOrder());
        assertEquals("Sample Task", task.getName());
        assertEquals("Sample Description", task.getDescription());
        assertNotNull(task.getDeadline());
        assertEquals(Task.Status.TODO, task.getStatus());
        assertEquals(project, task.getProject());
        assertEquals(personalTodoListOwner, task.getPersonalTodoListOwner());
    }
}