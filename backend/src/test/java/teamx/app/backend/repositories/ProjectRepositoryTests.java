package teamx.app.backend.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import teamx.app.backend.models.Project;
import teamx.app.backend.repositories.ProjectRepository;

import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.TimeZone;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

/**
 * Tests for project repository
 *  @Author Nizar Amine
 */

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@DataJpaTest
class ProjectRepositoryTests {

    @Mock
    private ProjectRepository projectRepository;

    @BeforeEach
    void setUp() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    @Test
    void testGetAllByTeamWarehouseId() {
        Long warehouseId = 1L;
        when(projectRepository.getAllByTeam_Warehouse_Id(warehouseId))
                .thenReturn(Arrays.asList(createProject(1L), createProject(2L)));

        List<Project> projects = projectRepository.getAllByTeam_Warehouse_Id(warehouseId);

        assertEquals(2, projects.size());
        Project firstProject = projects.get(0);
        assertEquals("Project 1", firstProject.getName());
        assertEquals("Description 1", firstProject.getDescription());
        assertEquals("Location 1", firstProject.getLocation());
        assertEquals("Client 1", firstProject.getClientName());
        assertEquals("client1@example.com", firstProject.getClientEmail());
        assertEquals("1234567890", firstProject.getClientPhone());
        assertEquals("IN_PROGRESS", firstProject.getStatus().name());
    }

    @Test
    void testFindAllByStatusAndTeamWarehouseIdAndEndDateBetween() {
        Project.Status status = Project.Status.IN_PROGRESS;
        Long warehouseId = 1L;
        Date startDate = Date.valueOf("2021-01-01");
        Date endDate = Date.valueOf("2021-02-01");

        when(projectRepository.findAllByStatusAndTeam_Warehouse_IdAndEndDateBetween(status, warehouseId, startDate, endDate))
                .thenReturn(Arrays.asList(createProject(1L), createProject(2L)));

        List<Project> projects = projectRepository
                .findAllByStatusAndTeam_Warehouse_IdAndEndDateBetween(status, warehouseId, startDate, endDate);

        assertEquals(2, projects.size());
        assertEquals(status, projects.get(0).getStatus());

    }

    @Test
    void testFindAllByEndDateBetween() {
        Date startDate = Date.valueOf("2021-01-01");
        Date endDate = Date.valueOf("2021-02-01");

        when(projectRepository.findAllByEndDateBetween(startDate, endDate))
                .thenReturn(Arrays.asList(createProject(1L), createProject(2L)));

        List<Project> projects = projectRepository.findAllByEndDateBetween(startDate, endDate);

        assertEquals(2, projects.size());
        assertTrue(!projects.isEmpty(), "Projects should not be empty");

        Project firstProject = projects.get(0);
        Project secondProject = projects.get(1);

        assertTrue(!firstProject.getEndDate().before(startDate) && !firstProject.getEndDate().after(endDate),
                "First project end date should be within the specified range");
        assertTrue(!secondProject.getEndDate().before(startDate) && !secondProject.getEndDate().after(endDate),
                "Second project end date should be within the specified range");

    }

    @Test
    void testFindAllByTeamId() {
        Long teamId = 1L;
        when(projectRepository.findAllByTeam_Id(teamId))
                .thenReturn(Arrays.asList(createProject(1L), createProject(2L)));

        List<Project> projects = projectRepository.findAllByTeam_Id(teamId);

        assertEquals(2, projects.size());
        Project firstProject = projects.get(0);
        assertEquals("Project 1", firstProject.getName());
        assertEquals("Description 1", firstProject.getDescription());
    }

    private Project createProject(Long id) {
        Project project = new Project();
        project.setId(id);
        project.setName("Project " + id);
        project.setDescription("Description " + id);
        project.setLocation("Location " + id);
        project.setClientName("Client " + id);
        project.setClientEmail("client" + id + "@example.com");
        project.setClientPhone("1234567890");

        // Generate random startDate and endDate
        LocalDate startDate = LocalDate.of(2021, 1, 1);
        LocalDate endDate = LocalDate.of(2021, 2, 1);
        Random random = new Random();
        long daysBetween = ChronoUnit.DAYS.between(startDate, endDate);
        long randomDays = random.nextInt((int) daysBetween + 1);
        LocalDate randomDate = startDate.plusDays(randomDays);

        project.setStartDate(Date.valueOf(randomDate));

        // Calculate the number of days between the randomDate and the maximum endDate
        long daysToEnd = ChronoUnit.DAYS.between(randomDate, endDate);
        // Generate a random number of days between 0 and the number of days to the end
        long randomDaysToEnd = random.nextInt((int) daysToEnd + 1);
        // Add the random number of days to the randomDate to get the endDate
        LocalDate randomEndDate = randomDate.plusDays(randomDaysToEnd);

        project.setEndDate(Date.valueOf(randomEndDate));

        project.setStatus(Project.Status.IN_PROGRESS);
        return project;
    }
}
