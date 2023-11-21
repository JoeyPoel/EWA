package teamx.app.backend.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import teamx.app.backend.models.Project;
import teamx.app.backend.repositories.ProjectRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Project Controller
 * This class is a REST controller for the project model.
 *
 * @author Nizar Amine
 * @see Project
 * @see ProjectRepository
 */

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/projects")
public class ProjectController {

    private final ProjectRepository projectRepository;

    @Autowired
    public ProjectController(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    private static Project updateProjectData(Project newProjectData, Optional<Project> originalProjectData) {
        Project updatedProjectData = originalProjectData.get();
        updatedProjectData.setName(newProjectData.getName());
        updatedProjectData.setStatus(newProjectData.getStatus());
        updatedProjectData.setTeam_name(newProjectData.getTeam_name());
        updatedProjectData.setTeam_id(newProjectData.getTeam_id());
        updatedProjectData.setDescription(newProjectData.getDescription());
        return updatedProjectData;
    }

    @GetMapping("/test")
    public ResponseEntity<List<Project>> getTestProjects() {
        return ResponseEntity.ok(projectRepository.findAll());
    }

    @GetMapping("/getAllProjects")
    public ResponseEntity<List<Project>> getAllProjects() {
        try {
            List<Project> projects = new ArrayList<>(projectRepository.findAll());
            if (projects.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No projects found");
            }
            return new ResponseEntity<>(projects, HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error retrieving projects");
        }
    }

    @GetMapping("/getProjectById/{id}")
    public ResponseEntity<Project> getProjectById(@PathVariable Long id) {
        try {
            Optional<Project> project = projectRepository.findById(id);

            if (project.isPresent()) {
                return new ResponseEntity<>(project.get(), HttpStatus.OK);
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found");
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error retrieving project");
        }
    }

    @PostMapping("/addProject")
    public ResponseEntity<Project> addProject(@RequestBody Project project) {
        try {
            Project savedProject = projectRepository.save(project);
            return new ResponseEntity<>(savedProject, HttpStatus.CREATED);
        } catch (Exception e) {
            // Log the exception details
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error creating project", e);
        }
    }


    @PutMapping("/updateProjectById/{id}")
    public ResponseEntity<Project> updateProjectById(@PathVariable Long id, @RequestBody Project newProjectData) {
        try {
            Optional<Project> originalProjectData = projectRepository.findById(id);

            if (originalProjectData.isPresent()) {
                Project updatedProjectData = updateProjectData(newProjectData, originalProjectData);

                Project project = projectRepository.save(updatedProjectData);
                return new ResponseEntity<>(project, HttpStatus.OK);
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found");
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error updating project");
        }
    }

    @DeleteMapping("/deleteProjectById/{id}")
    public ResponseEntity<Project> deleteProjectById(@PathVariable Long id) {
        try {
            Optional<Project> project = projectRepository.findById(id);

            if (project.isPresent()) {
                projectRepository.deleteById(id);
                return new ResponseEntity<>(project.get(), HttpStatus.OK);
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found");
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error deleting project");
        }
    }
}
