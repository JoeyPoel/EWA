package teamx.app.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import teamx.app.backend.models.dto.ProjectDTO;
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

    @GetMapping
    public ResponseEntity<List<ProjectDTO>> getAllProjects() {
        try {
            List<ProjectDTO> projects = projectService.getAllDTO();

            if (projects.isEmpty()) {
                return new ResponseEntity<>(projects, HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(projects, HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while getting projects", e);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectDTO> getProjectById(@PathVariable Long id) {
        try {
            ProjectDTO project = projectService.getByIdDTO(id);

            if (project == null) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(project, HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while getting project", e);
        }
    }

    @GetMapping("/warehouse/{warehouseId}")
    public ResponseEntity<List<ProjectDTO>> getAllProjectsByWarehouseId(@PathVariable Long warehouseId) {
        try {
            List<ProjectDTO> projects = projectService.getAllByWarehouseIdDTO(warehouseId);

            if (projects == null) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(projects, HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while getting projects", e);
        }
    }

    @PostMapping
    public ResponseEntity<ProjectDTO> addProject(@RequestBody ProjectDTO project) {
        try {
            ProjectDTO newProject = projectService.addDTO(project);

            if (newProject == null) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(newProject, HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while adding project", e);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjectDTO> updateProjectById(@PathVariable Long id, @RequestBody ProjectDTO project) {
        try {
            ProjectDTO updatedProject = projectService.updateDTO(project, id);

            if (updatedProject == null) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(updatedProject, HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while updating project", e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProjectDTO> deleteProjectById(@PathVariable Long id) {
        try {
            ProjectDTO project = projectService.getByIdDTO(id);

            if (project == null) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            ProjectDTO deletedProject = projectService.deleteDTO(id);

            if (deletedProject == null) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(deletedProject, HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while deleting project", e);
        }
    }
}
