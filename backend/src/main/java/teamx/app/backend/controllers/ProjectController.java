package teamx.app.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import teamx.app.backend.models.Project;
import teamx.app.backend.services.ProjectService;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/projects")
public class ProjectController {
    private final ProjectService projectService;

    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/getAllProjects")
    public ResponseEntity<List<Project>> getAllProjects() {
        try {
            List<Project> projects = projectService.getAllProjects();
            if (projects == null) {
                throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No projects found");
            }
            return ResponseEntity.ok(projects);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error retrieving projects : " + e.getMessage());
        }
    }

    // TODO: Add validation and authentication
    @PostMapping("/addProject")
    public ResponseEntity<Project> addProject(@RequestBody Project project) {
        try {
            return ResponseEntity.ok(projectService.addProject(project));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PutMapping("/updateProject/{id}")
    public ResponseEntity<Project> updateProject(@RequestBody Project project, @PathVariable Long id) {
        try {
            Project updatedProject = projectService.updateProject(project, id);
            if (updatedProject == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found");
            }
            return ResponseEntity.ok(updatedProject);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
