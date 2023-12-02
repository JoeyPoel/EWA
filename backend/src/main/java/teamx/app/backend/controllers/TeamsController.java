package teamx.app.backend.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import teamx.app.backend.models.dto.TeamDTO;
import teamx.app.backend.services.TeamService;

import java.util.List;

/**
 * Teams controller.
 * This class is a REST controller for the team model.
 *
 * @author Joey van der Poel
 * @author Junior Javier Brito Perez
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/teams")
@Slf4j
public class TeamsController {
    private final TeamService teamService;

    @Autowired
    public TeamsController(TeamService teamService) {
        this.teamService = teamService;
    }

    /**
     * Retrieves all teams.
     *
     * @return a ResponseEntity containing a list of TeamDTO representing all teams.
     */
    @GetMapping
    public ResponseEntity<List<TeamDTO>> getAll() {
        return ResponseEntity.ok(teamService.findAll());
    }

    /**
     * Retrieves all teams by warehouse ID.
     *
     * @param warehouseId the ID of the warehouse
     * @return a ResponseEntity containing a list of TeamDTO representing all teams in the specified warehouse.
     */
    @GetMapping("/warehouse/{warehouseId}")
    public ResponseEntity<List<TeamDTO>> getAllByWarehouseId(@PathVariable Long warehouseId) {
        return ResponseEntity.ok(teamService.findAllByWarehouseId(warehouseId));
    }

    /**
     * Retrieves the team with the given ID.
     *
     * @param id the ID of the team
     * @return a ResponseEntity containing the TeamDTO representing the team with the specified ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TeamDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(teamService.findByIdDTO(id));
    }

    /**
     * Adds a new team.
     *
     * @param team the TeamDTO representing the team to be added
     * @return a ResponseEntity containing the TeamDTO representing the added team.
     */
    @PostMapping
    public ResponseEntity<TeamDTO> add(@RequestBody TeamDTO team) {
        return ResponseEntity.status(HttpStatus.CREATED).body(teamService.add(team));
    }

    /**
     * Updates a team by its ID.
     *
     * @param id the ID of the team to be updated
     * @param team the TeamDTO representing the updated team
     * @return a ResponseEntity containing the TeamDTO representing the updated team.
     */
    @PutMapping("/{id}")
    public ResponseEntity<TeamDTO> updateById(@PathVariable Long id, @RequestBody TeamDTO team) {
        return ResponseEntity.ok(teamService.update(id, team));
    }

    /**
     * Deletes a team by its ID.
     *
     * @param id the ID of the team to be deleted
     * @return a ResponseEntity containing the TeamDTO representing the deleted team.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<TeamDTO> deleteTeamById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(teamService.delete(id));
    }
}

