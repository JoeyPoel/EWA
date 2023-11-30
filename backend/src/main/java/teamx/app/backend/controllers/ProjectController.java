package teamx.app.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import teamx.app.backend.models.dto.ProjectDTO;
import teamx.app.backend.services.ProjectService;

import java.util.List;

/**
 * The ProjectController class is a REST controller that handles project-related operations.
 * It exposes endpoints for retrieving, adding, updating, and deleting projects.
 *
 * @author Nizar Amine
 * @author Junior Javier Brito Perez
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/projects")
public class ProjectController {
    private final ProjectService projectService;

    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    /**
     * Retrieves all projects.
     *
     * @return ResponseEntity object containing a list of ProjectDTO objects
     * @throws ResponseStatusException if an error occurs while getting projects
     */
    @GetMapping
    public ResponseEntity<List<ProjectDTO>> getAll() {
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

    /**
     * Retrieves a project by its ID.
     *
     * @param id the ID of the project to retrieve
     * @return ResponseEntity object containing the ProjectDTO object for the given ID
     * @throws ResponseStatusException if an error occurs while getting the project
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProjectDTO> getById(@PathVariable Long id) {
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

    /**
     * Retrieves all projects associated with a given warehouse ID.
     *
     * @param warehouseId the ID of the warehouse to retrieve the projects for
     * @return ResponseEntity object containing a list of ProjectDTO objects associated with the given warehouse ID
     * @throws ResponseStatusException if an error occurs while getting the projects
     */
    @GetMapping("/warehouse/{warehouseId}")
    public ResponseEntity<List<ProjectDTO>> getAllByWarehouseId(@PathVariable Long warehouseId) {
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

    /**
     * Adds a new project.
     *
     * @param project the ProjectDTO object to be added
     * @return ResponseEntity object containing the added ProjectDTO object
     * @throws ResponseStatusException if an error occurs while adding the project
     */
    @PostMapping
    public ResponseEntity<ProjectDTO> add(@RequestBody ProjectDTO project) {
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

    /**
     * Updates a project by its ID.
     *
     * @param id      the ID of the project to be updated
     * @param project the updated ProjectDTO object
     * @return ResponseEntity object containing the updated ProjectDTO object
     * @throws ResponseStatusException if an error occurs while updating the project
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProjectDTO> updateById(@PathVariable Long id, @RequestBody ProjectDTO project) {
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

    /**
     * Deletes a project by its ID.
     *
     * @param id the ID of the project to be deleted
     * @return ResponseEntity object containing the deleted ProjectDTO object or NO_CONTENT status if the project does not exist
     * @throws ResponseStatusException if an error occurs while deleting the project
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ProjectDTO> deleteById(@PathVariable Long id) {
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
