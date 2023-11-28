package teamx.app.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import teamx.app.backend.models.Project;
import teamx.app.backend.models.Team;
import teamx.app.backend.repositories.ProjectRepository;

import java.util.List;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;

    @Autowired
    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public List<Project> getAllProjects() {
        List<Project> projects = projectRepository.findAll();
        if (projects.isEmpty()) {
            return null;
        }
        return projects;
    }

    public Project getProjectById(Long id) {
        return projectRepository.findById(id).orElse(null);
    }

    public Project addProject(Project project) {
        return projectRepository.save(project);
    }

    public Project updateProject(Project project, Long id) {
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

    public Project deleteProject(Long id) {
        Project project = projectRepository.findById(id).orElse(null);
        if (project == null) {
            return null;
        }
        projectRepository.deleteById(id);
        return project;
    }

    public List<Project> getAllProjectsByWarehouseId(Long warehouseId) {
        return projectRepository.getAllByTeam_Warehouse_Id(warehouseId);
    }
}
