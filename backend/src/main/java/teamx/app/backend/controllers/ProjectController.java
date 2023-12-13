package teamx.app.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import teamx.app.backend.models.Project;
import teamx.app.backend.services.ProjectService;
import teamx.app.backend.utils.DTO.InventoryProjectDTO;
import teamx.app.backend.utils.DTO.ProjectDTO;

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
     * Retrieves all projects from the database.
     *
     * @return a ResponseEntity object containing a list of ProjectDTO objects.
     */
    @GetMapping
    public ResponseEntity<List<ProjectDTO>> getAll() {
        return ResponseEntity.ok(projectService.findAll().stream().map(Project::toDTO).toList());
    }

    /**
     * Retrieves a project by its ID from the database.
     *
     * @param id the ID of the project to retrieve.
     * @return a ResponseEntity object containing the ProjectDTO object.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProjectDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(projectService.findById(id).toDTO());
    }

    /**
     * Retrieves all projects associated with a given warehouse ID from the database.
     *
     * @param warehouseId the ID of the warehouse to retrieve projects for.
     * @return a ResponseEntity object containing a List of ProjectDTO objects.
     */
    @GetMapping("/warehouse/{warehouseId}")
    public ResponseEntity<List<ProjectDTO>> getAllByWarehouseId(@PathVariable Long warehouseId) {
        return ResponseEntity.ok(
                projectService.findAllByWarehouseId(warehouseId).stream()
                        .map(Project::toDTO)
                        .toList()
        );
    }

    /**
     * Adds a new project to the database.
     *
     * @param project the ProjectDTO object representing the project to be added.
     * @return a ResponseEntity object containing the newly added ProjectDTO object.
     */
    @PostMapping
    public ResponseEntity<ProjectDTO> add(@RequestBody ProjectDTO project) {
        return ResponseEntity.status(HttpStatus.CREATED).body(projectService.add(project).toDTO());
    }

    /**
     * Updates an existing project in the database based on its ID.
     *
     * @param id      the ID of the project to be updated.
     * @param project the ProjectDTO object representing the updated project.
     * @return a ResponseEntity object containing the updated ProjectDTO object.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProjectDTO> updateById(@PathVariable Long id, @RequestBody ProjectDTO project) {
        return ResponseEntity.ok(projectService.update(id, project).toDTO());
    }

    /**
     * Deletes a project from the database based on its ID.
     *
     * @param id the ID of the project to be deleted.
     * @return a ResponseEntity object with the status set to HttpStatus.NO_CONTENT upon successful deletion.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ProjectDTO> deleteById(@PathVariable Long id) {
        return ResponseEntity.ok(projectService.delete(id).toDTO());
    }

    /**
     * Retrieves a projects inventory from the database.
     *
     * @return a ResponseEntity object containing a list of InventoryProjectDTO objects.
     */

    @GetMapping("/inventory/{id}")
    public ResponseEntity<List<InventoryProjectDTO>> getAllInventory(@PathVariable Long id) {
        return ResponseEntity.ok(projectService.getProjectMaterials(id));
    }

    @GetMapping("/team/{id}")
    public ResponseEntity<List<ProjectDTO>> getAllByTeamId(@PathVariable Long id) {
        return ResponseEntity.ok(projectService.findAllByTeamId(id).stream().map(Project::toDTO).toList());
    }
}
