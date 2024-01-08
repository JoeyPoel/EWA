package teamx.app.backend.services;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import teamx.app.backend.models.Project;
import java.sql.Date;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;

/**
 * Tests for email service
 *
 * @author Joey van der Poel
 */
@SpringBootTest
class EmailServiceTest {

    @Mock
    private ProjectService projectService;

    @InjectMocks
    private EmailService emailService;

    @Test
    void findAllProjectsThatAreStillInProgress_ProjectsFound() {
        // Mocking the projects that are in progress
        Project project1 = Mockito.mock(Project.class);
        Mockito.when(project1.getId()).thenReturn(1L);
        Mockito.when(project1.getName()).thenReturn("Project 1 Name");
        Mockito.when(project1.getDescription()).thenReturn("Project 1 Description");
        Mockito.when(project1.getStartDate()).thenReturn(Date.valueOf("2023-01-01"));
        Mockito.when(project1.getEndDate()).thenReturn(Date.valueOf("2023-02-01"));

        Project project2 = Mockito.mock(Project.class);
        Mockito.when(project2.getId()).thenReturn(2L);
        Mockito.when(project2.getName()).thenReturn("Project 2 Name");
        Mockito.when(project2.getDescription()).thenReturn("Project 2 Description");
        Mockito.when(project2.getStartDate()).thenReturn(Date.valueOf("2023-03-01"));
        Mockito.when(project2.getEndDate()).thenReturn(Date.valueOf("2023-04-01"));


        Mockito.when(projectService.findProjectsByStatusAndDateBetween(
                eq(Project.Status.IN_PROGRESS), // Using eq() for enum value
                eq(null),
                any(), // Matcher for any object
                any())).thenReturn(Arrays.asList(project1, project2));

        List<Project> projects = emailService.findAllProjectsThatAreStillInProgress();

        assertFalse(projects.isEmpty());
        assertEquals(2, projects.size());
    }

    @Test
    void findAllProjectsThatAreStillInProgress_ProjectsFinished() {
        // Mocking the projects that are finished
        Project project1 = Mockito.mock(Project.class);
        Mockito.when(project1.getId()).thenReturn(1L);
        Mockito.when(project1.getName()).thenReturn("Project 1 Name");
        Mockito.when(project1.getDescription()).thenReturn("Project 1 Description");
        Mockito.when(project1.getStartDate()).thenReturn(Date.valueOf("2023-01-01"));
        Mockito.when(project1.getEndDate()).thenReturn(Date.valueOf("2023-02-01"));

        Project project2 = Mockito.mock(Project.class);
        Mockito.when(project2.getId()).thenReturn(2L);
        Mockito.when(project2.getName()).thenReturn("Project 2 Name");
        Mockito.when(project2.getDescription()).thenReturn("Project 2 Description");
        Mockito.when(project2.getStartDate()).thenReturn(Date.valueOf("2023-03-01"));
        Mockito.when(project2.getEndDate()).thenReturn(Date.valueOf("2023-04-01"));

        Mockito.when(projectService.findProjectsByStatusAndDateBetween(
                eq(Project.Status.FINISHED), // Using eq() for enum value
                eq(null),
                any(), // Matcher for any object
                any())).thenReturn(Arrays.asList(project1, project2));

        List<Project> projects = emailService.findAllProjectsThatAreStillInProgress();

        assertTrue(projects.isEmpty());
    }

    @Test
    void findAllProjectsThatAreStillInProgress_NoProjects() {
        // Testing when no projects are in progress
        Mockito.when(projectService.findProjectsByStatusAndDateBetween(
                eq(Project.Status.IN_PROGRESS), // Using eq() for enum value
                eq(null),
                any(), // Matcher for any object
                any())).thenReturn(Collections.emptyList());

        List<Project> projects = emailService.findAllProjectsThatAreStillInProgress();

        assertTrue(projects.isEmpty());
    }
}
