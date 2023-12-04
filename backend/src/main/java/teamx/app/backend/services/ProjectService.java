package teamx.app.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import teamx.app.backend.models.Project;
import teamx.app.backend.models.dto.ProjectDTO;
import teamx.app.backend.repositories.ProjectRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;

    @Autowired
    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public List<Project> getAllProjects() { // otherwise joeys code breaks
        List<Project> projects = projectRepository.findAll();
        if (projects.isEmpty()) {
            return null;
        }
        return projects;
    }


    public List<ProjectDTO> findAll() {
        List<Project> projects = projectRepository.findAll();
        if (projects.isEmpty()) {
            throw new NoSuchElementException("No projects found");
        }
        return mapToDTO(projects);
    }

    protected Project findById(Long projectID) {
        return projectRepository.findById(projectID)
                .orElseThrow(() -> new NoSuchElementException("Project not found with id " + projectID));
    }

    public ProjectDTO findDTOById(Long projectID) {
        return mapToDTO(findById(projectID));
    }

    private Project save(Project project) {
        return projectRepository.save(project);
    }

    public ProjectDTO add(ProjectDTO projectDTO) {
        Project project = new Project();
        return mapToDTO(mapToEntity(project, projectDTO));
    }

    public ProjectDTO update(Long projectId, ProjectDTO newProjectData) {
        if (!projectId.equals(newProjectData.getId())) throw new IllegalArgumentException("Project ID does not match");
        Project project = findById(projectId);
        return mapToDTO(mapToEntity(project, newProjectData));
    }

    public ProjectDTO delete(Long projectId) {
        Project deletedProject = findById(projectId);
        projectRepository.deleteById(projectId);
        return mapToDTO(deletedProject);
    }

    public List<ProjectDTO> findAllByWarehouseId(Long warehouseId) {
        return mapToDTO(projectRepository.getAllByTeam_Warehouse_Id(warehouseId));
    }

    private Project mapToEntity(Project project, ProjectDTO projectDTO) {
        project.setName(projectDTO.getName());
        project.setDescription(projectDTO.getDescription());
        project.setLocation(projectDTO.getLocation());
        project.setClientName(projectDTO.getClientName());
        project.setClientEmail(projectDTO.getClientEmail());
        project.setClientPhone(projectDTO.getClientPhone());
        project.setStartDate(projectDTO.getStartDate());
        project.setEndDate(projectDTO.getEndDate());
        project.setStatus(Project.Status.valueOf(projectDTO.getStatus()));
        return save(project);
    }

    private ProjectDTO mapToDTO(Project project) {
        return new ProjectDTO(project);
    }

    private List<ProjectDTO> mapToDTO(List<Project> projects) {
        return projects
                .stream()
                .map(this::mapToDTO)
                .toList();
    }
}
