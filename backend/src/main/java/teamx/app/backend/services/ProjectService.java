package teamx.app.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import teamx.app.backend.models.Project;
import teamx.app.backend.models.Team;
import teamx.app.backend.models.Transaction;
import teamx.app.backend.models.dto.InventoryProjectDTO;
import teamx.app.backend.models.dto.ProjectDTO;
import teamx.app.backend.repositories.ProjectRepository;
import teamx.app.backend.repositories.TeamRepository;

import java.sql.Date;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final TeamRepository teamRepository;

    @Autowired
    public ProjectService(ProjectRepository projectRepository, TeamRepository teamRepository) {
        this.projectRepository = projectRepository;
        this.teamRepository = teamRepository;
    }

    protected List<Project> findAll() {
        return projectRepository.findAll();
    }

public List<Project> getAllProjects() { // otherwise joeys code breaks
        List<Project> projects = projectRepository.findAll();
        if (projects.isEmpty()) {
            return null;
        }
        return projects;
    }


    public List<ProjectDTO> findAllDTO() {
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
        if (newProjectData.getTeamId() != null){
            project.setTeam(teamRepository.findById(newProjectData.getTeamId()).orElseThrow());
        }
        Project savedProject = mapToEntity(project, newProjectData);
        return mapToDTO(savedProject);
    }

    public ProjectDTO delete(Long projectId) {
        Project deletedProject = findById(projectId);
        projectRepository.deleteById(projectId);
        return mapToDTO(deletedProject);
    }

    public List<ProjectDTO> findAllByWarehouseIdDTO(Long warehouseId) {
        return mapToDTO(projectRepository.getAllByTeam_Warehouse_Id(warehouseId));
    }

    public List<Project> findAllByWarehouseId(Long warehouseId) {
        return projectRepository.getAllByTeam_Warehouse_Id(warehouseId);
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

    protected void setTeam(List<Project> projects, Team team) {
        projects.forEach(project -> project.setTeam(team));
        projectRepository.saveAll(projects);
    }

    protected List<Project> findProjectsByDateBetween(Long warehouseId, Date startDate, Date endDate) {
        return warehouseId == null ?
                projectRepository.findAllByEndDateBetween(startDate, endDate) :
                projectRepository.findAllByTeam_Warehouse_IdAndEndDateBetween(warehouseId, startDate, endDate);
    }

    protected List<Project> findProjectsByStatusAndDateBetween
            (Project.Status status, Long warehouseId, Date startDate, Date endDate) {
        return warehouseId == null ?
                projectRepository.findAllByStatusAndEndDateBetween(status, startDate, endDate) :
                projectRepository.findAllByStatusAndTeam_Warehouse_IdAndEndDateBetween(
                        status, warehouseId, startDate, endDate);
    }

    public Collection<Object> findProjectsByStatus(Project.Status status) {
        return projectRepository.findAllByStatus(status);
    }

    public Collection<Object> findProjectsByStatus(Project.Status status, Long warehouseId) {
        return projectRepository.findAllByStatusAndTeam_Warehouse_Id(status, warehouseId);
    }

    private InventoryProjectDTO mapInventoryToDTO(Transaction transaction) {
        InventoryProjectDTO dto = new InventoryProjectDTO();
        dto.setId(transaction.getId());
        dto.setQuantity(transaction.getQuantity());
        dto.setTransactionType(transaction.getTransactionType().name());
        dto.setTransactionDate(transaction.getTransactionDate());

        if (transaction.getProduct() != null) {
            dto.setProductName(transaction.getProduct().getName());
        }

        if (transaction.getWarehouse() != null) {
            dto.setWarehouseName(transaction.getWarehouse().getName());
        }

        return dto;
    }

    public List<InventoryProjectDTO> getProjectMaterials(Long projectId) {
        Project project = projectRepository.findById(projectId).orElse(null);

        if (project != null) {
            return project.getMaterials()
                    .stream()
                    .map(this::mapInventoryToDTO)
                    .collect(Collectors.toList());
        }

        return null;
    }
}
