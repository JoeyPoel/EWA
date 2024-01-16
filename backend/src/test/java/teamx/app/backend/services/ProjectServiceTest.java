package teamx.app.backend.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import teamx.app.backend.models.Project;
import teamx.app.backend.repositories.ProjectRepository;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Tests for project service
    *
    * @Author Nizar Amine
    */
@ExtendWith(MockitoExtension.class)
class ProjectServiceTest {

    @Mock
    private ProjectRepository projectRepository;


    @InjectMocks
    private ProjectService projectService;

    @Test
    void testDelete() {
        long projectId = 1L;
        Project existingProject = new Project();
        when(projectRepository.findById(projectId)).thenReturn(Optional.of(existingProject));

        Project result = projectService.delete(projectId);

        Mockito.verify(projectRepository).delete(existingProject);
        assertEquals(existingProject, result);
    }

    @Test
    void testFindAllByWarehouseId() {
        long warehouseId = 1L;
        List<Project> projects = new ArrayList<>();
        when(projectRepository.getAllByTeam_Warehouse_Id(warehouseId)).thenReturn(projects);

        List<Project> result = projectService.findAllByWarehouseId(warehouseId);

        assertEquals(projects, result);
    }

    @Test
    void testFindProjectsByStatusAndDateBetween() {
        Project.Status status = Project.Status.IN_PROGRESS;
        long warehouseId = 1L;
        Date startDate = Date.valueOf("2022-01-01");
        Date endDate = Date.valueOf("2022-02-01");
        List<Project> projects = new ArrayList<>();
        when(projectRepository.findAllByStatusAndTeam_Warehouse_IdAndEndDateBetween(
                status, warehouseId, startDate, endDate)).thenReturn(projects);

        List<Project> result = projectService.findProjectsByStatusAndDateBetween(
                status, warehouseId, startDate, endDate);

        assertEquals(projects, result);
    }

}
