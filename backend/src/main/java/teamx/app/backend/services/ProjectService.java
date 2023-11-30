package teamx.app.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import teamx.app.backend.models.Project;
import teamx.app.backend.models.dto.ProjectDTO;
import teamx.app.backend.repositories.ProjectRepository;

import java.sql.Date;
import java.util.List;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final TeamService teamService;

    @Autowired
    public ProjectService(ProjectRepository projectRepository, TeamService teamService) {
        this.projectRepository = projectRepository;
        this.teamService = teamService;
    }

    protected List<Project> getAll() {
        return projectRepository.findAll();
    }

    public List<ProjectDTO> getAllDTO() {
        return getAll().stream().map(this::convertToDTO).toList();
    }

    protected Project getById(Long id) {
        return projectRepository.findById(id).orElse(null);
    }

    public ProjectDTO getByIdDTO(Long id) {
        return convertToDTO(getById(id));
    }

    protected Project add(Project project) {
        return projectRepository.save(project);
    }

    public ProjectDTO addDTO(ProjectDTO projectDTO) {
        return convertToDTO(add(convertToEntity(projectDTO)));
    }

    protected Project update(Project project, Long id) {
        Project existingProject = projectRepository.findById(id).orElse(null);
        if (existingProject == null || project == null) {
            return null;
        }
        existingProject.setName(project.getName());
        existingProject.setDescription(project.getDescription());
        existingProject.setLocation(project.getLocation());
        existingProject.setTeam(project.getTeam());
        existingProject.setClientName(project.getClientName());
        existingProject.setClientEmail(project.getClientEmail());
        existingProject.setClientPhone(project.getClientPhone());
        existingProject.setStartDate(project.getStartDate());
        existingProject.setEndDate(project.getEndDate());
        existingProject.setStatus(project.getStatus());
        return projectRepository.save(existingProject);
    }

    public ProjectDTO updateDTO(ProjectDTO projectDTO, Long id) {
        return convertToDTO(update(convertToEntity(projectDTO), id));
    }

    protected Project delete(Long id) {
        Project project = projectRepository.findById(id).orElse(null);
        if (project == null) {
            return null;
        }
        projectRepository.deleteById(id);
        return project;
    }

    public ProjectDTO deleteDTO(Long id) {
        return convertToDTO(delete(id));
    }

    protected List<Project> getAllByWarehouseId(Long warehouseId) {
        return projectRepository.getAllByTeam_Warehouse_Id(warehouseId);
    }

    public List<ProjectDTO> getAllByWarehouseIdDTO(Long warehouseId) {
        return getAllByWarehouseId(warehouseId).stream().map(this::convertToDTO).toList();
    }

    private ProjectDTO convertToDTO(Project project) {
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setId(project.getId());
        projectDTO.setName(project.getName());
        projectDTO.setDescription(project.getDescription());
        projectDTO.setLocation(project.getLocation());
        projectDTO.setClientName(project.getClientName());
        projectDTO.setClientEmail(project.getClientEmail());
        projectDTO.setClientPhone(project.getClientPhone());
        projectDTO.setStartDate(project.getStartDate());
        projectDTO.setEndDate(project.getEndDate());
        projectDTO.setStatus(String.valueOf(project.getStatus()));
        projectDTO.setTeamId(project.getTeam() != null ? project.getTeam().getId() : null);
        return projectDTO;
    }

    private Project convertToEntity(ProjectDTO projectDTO) {
        Project project = new Project();
        project.setId(projectDTO.getId());
        project.setName(projectDTO.getName());
        project.setDescription(projectDTO.getDescription());
        project.setLocation(projectDTO.getLocation());
        project.setClientName(projectDTO.getClientName());
        project.setClientEmail(projectDTO.getClientEmail());
        project.setClientPhone(projectDTO.getClientPhone());
        // TODO: CHECK IF THIS WORKS
        project.setStartDate(Date.valueOf(String.valueOf(projectDTO.getStartDate())));
        project.setEndDate(Date.valueOf(String.valueOf(projectDTO.getEndDate())));
        project.setStatus(Project.Status.valueOf(projectDTO.getStatus()));
        project.setTeam(projectDTO.getTeamId() != null ? teamService.getById(projectDTO.getTeamId()) : null);
        return project;
    }
}
