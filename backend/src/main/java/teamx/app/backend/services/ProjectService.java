package teamx.app.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import teamx.app.backend.models.Project;
import teamx.app.backend.models.Task;
import teamx.app.backend.models.Team;
import teamx.app.backend.models.Transaction;
import teamx.app.backend.models.dto.InventoryProjectDTO;
import teamx.app.backend.models.dto.TaskDTO;
import teamx.app.backend.repositories.ProjectRepository;
import teamx.app.backend.repositories.TeamRepository;
import teamx.app.backend.utils.DTO.ProjectDTO;

import java.sql.Date;
import java.util.List;
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

    public List<Project> findAll() {
        return projectRepository.findAll();
    }

    public Project findById(Long projectID) {
        return projectRepository.findById(projectID).orElse(null);
    }

    private Project save(Project project) {
        return projectRepository.save(project);
    }

    public Project add(ProjectDTO projectDTO) {
        return mapToEntity(new Project(), projectDTO);
    }

    public Project update(Long projectId, ProjectDTO newProjectData) {
        if (!projectId.equals(newProjectData.getId())) throw new IllegalArgumentException("Project ID does not match");
        Project project = findById(projectId);
        if (newProjectData.getTeamId() != null) {
            project.setTeam(teamRepository.findById(newProjectData.getTeamId()).orElseThrow());
        }
        return mapToEntity(project, newProjectData);
    }

    public Project delete(Long projectId) {
        Project deletedProject = findById(projectId);
        projectRepository.delete(deletedProject);
        return deletedProject;
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
        project.setTeam(teamRepository.findById(projectDTO.getTeamId()).orElseThrow());
        return save(project);
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

    private TaskDTO mapTasksToDTO(Task task) {
        TaskDTO dto = new TaskDTO();
        dto.setId(task.getId());
        dto.setOrder(task.getTaskOrder());
        dto.setName(task.getName());
        dto.setDescription(task.getDescription());
        dto.setDeadline(task.getDeadline().toString());
        dto.setStatus(String.valueOf(task.getStatus()));
        dto.setProjectId(task.getProject().getId());
        dto.setPersonalTodoListOwnerId(task.getPersonalTodoListOwner().getId());
        dto.setPersonalTodoListOwnerName(task.getPersonalTodoListOwner().getName());
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

    public List<TaskDTO> getProjectTasks(Long projectId) {
        Project project = projectRepository.findById(projectId).orElse(null);

        if (project != null) {
            return project.getTasks()
                    .stream()
                    .map(this::mapTasksToDTO)
                    .collect(Collectors.toList());
        }
        return null;
    }

    public List<Project> findAllByTeamId(Long id) {
        return projectRepository.findAllByTeam_Id(id);
    }
}
