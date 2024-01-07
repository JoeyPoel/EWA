package teamx.app.backend.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import teamx.app.backend.utils.DTO.ProjectDTO;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProjectTest {

    private Project project;
    private Team team;
    private List<Task> tasks;
    private List<Transaction> materials;

    @BeforeEach
    void setUp() {
        team = new Team();
        Task task1 = new Task();
        Task task2 = new Task();
        tasks = Arrays.asList(task1, task2);

        Transaction material1 = new Transaction();
        Transaction material2 = new Transaction();
        materials = Arrays.asList(material1, material2);

        project = Project.builder()
                .id(1L)
                .name("Project 1")
                .description("Description 1")
                .location("Location 1")
                .clientName("Client 1")
                .clientEmail("client1@example.com")
                .clientPhone("1234567890")
                .startDate(new Date(System.currentTimeMillis()))
                .endDate(new Date(System.currentTimeMillis()))
                .status(Project.Status.IN_PROGRESS)
                .team(team)
                .tasks(tasks)
                .materials(materials)
                .build();
    }

    @Test
    void testProjectGettersAndSetters() {
        assertEquals(1L, project.getId());
        assertEquals("Project 1", project.getName());
        assertEquals("Description 1", project.getDescription());
        assertEquals("Location 1", project.getLocation());
        assertEquals("Client 1", project.getClientName());
        assertEquals("client1@example.com", project.getClientEmail());
        assertEquals("1234567890", project.getClientPhone());
        assertNotNull(project.getStartDate());
        assertNotNull(project.getEndDate());
        assertEquals(Project.Status.IN_PROGRESS, project.getStatus());
        assertEquals(team, project.getTeam());
        assertEquals(tasks, project.getTasks());
        assertEquals(materials, project.getMaterials());
    }

    @Test
    void testToDTO() {
        ProjectDTO dto = project.toDTO();

        assertNotNull(dto);
        assertEquals(project.getId(), dto.getId());
        assertEquals(project.getName(), dto.getName());
        assertEquals(project.getDescription(), dto.getDescription());
        assertEquals(project.getLocation(), dto.getLocation());
        assertEquals(project.getClientName(), dto.getClientName());
        assertEquals(project.getClientEmail(), dto.getClientEmail());
        assertEquals(project.getClientPhone(), dto.getClientPhone());
        assertEquals(project.getStartDate(), dto.getStartDate());
        assertEquals(project.getEndDate(), dto.getEndDate());
        assertEquals(String.valueOf(project.getStatus()), dto.getStatus());
        assertEquals(project.getTeam().getId(), dto.getTeamId());
    }
}
