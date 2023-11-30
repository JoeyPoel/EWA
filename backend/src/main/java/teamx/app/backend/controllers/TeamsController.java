package teamx.app.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
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
public class TeamsController {
    private final TeamService teamService;

    @Autowired
    public TeamsController(TeamService teamService) {
        this.teamService = teamService;
    }

    /**
     * Retrieves all teams.
     *
     * @return List of TeamDTO objects representing all teams.
     * @throws ResponseStatusException if an error occurs while retrieving the teams.
     */
    @GetMapping
    public ResponseEntity<List<TeamDTO>> getAll() {
        try {
            List<TeamDTO> teams = teamService.getAllDTO();

            if (teams.isEmpty()) {
                return new ResponseEntity<>(teams, HttpStatus.NO_CONTENT);
            }

            return ResponseEntity.status(HttpStatus.OK).body(teams);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error retrieving teams");
        }
    }

    /**
     * Retrieves all teams by warehouse ID.
     *
     * @param warehouseId the ID of the warehouse for which to retrieve teams.
     * @return List of TeamDTO objects representing all teams associated with the specified warehouse.
     * @throws ResponseStatusException if an error occurs while retrieving the teams.
     */
    @GetMapping("/warehouse/{warehouseId}")
    public ResponseEntity<List<TeamDTO>> getAllByWarehouseId(@PathVariable Long warehouseId) {
        try {
            List<TeamDTO> teams = teamService.getAllByWarehouseIdDTO(warehouseId);

            if (teams.isEmpty()) {
                return new ResponseEntity<>(teams, HttpStatus.NO_CONTENT);
            }

            return ResponseEntity.status(HttpStatus.OK).body(teams);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error retrieving teams" +
                    e.getMessage());
        }
    }

    /**
     * Retrieves a team by its ID.
     *
     * @param id the ID of the team to retrieve.
     * @return TeamDTO object representing the team with the specified ID.
     * @throws ResponseStatusException if an error occurs while retrieving the team.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TeamDTO> getById(@PathVariable Long id) {
        try {
            TeamDTO team = teamService.getByIdDTO(id);

            if (team == null) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return ResponseEntity.status(HttpStatus.OK).body(team);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error retrieving team");
        }
    }

    /**
     * Adds a new team.
     *
     * @param team the TeamDTO object representing the team to add.
     * @return TeamDTO object representing the added team.
     * @throws ResponseStatusException if the team already exists or an error occurs while adding the team.
     */
    @PostMapping
    public ResponseEntity<TeamDTO> add(@RequestBody TeamDTO team) {
        try {
            TeamDTO addedTeam = teamService.addDTO(team);

            if (addedTeam == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Team already exists");
            }

            return ResponseEntity.status(HttpStatus.CREATED).body(addedTeam);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error adding team");
        }
    }

    /**
     * Updates a team by its ID.
     *
     * @param id   the ID of the team to update.
     * @param team the TeamDTO object representing the updated team data.
     * @return TeamDTO object representing the updated team.
     * @throws ResponseStatusException if the team with the given ID is not found or an error occurs while updating the
     *                                 team.
     */
    @PutMapping("/{id}")
    public ResponseEntity<TeamDTO> updateById(@PathVariable Long id, @RequestBody TeamDTO team) {
        try {
            TeamDTO updatedTeam = teamService.updateDTO(team, id);

            if (updatedTeam == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Team not found");
            }

            return ResponseEntity.status(HttpStatus.OK).body(updatedTeam);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error updating team");
        }
    }

    /**
     * Deletes a team by its ID.
     *
     * @param id the ID of the team to delete.
     * @return TeamDTO object representing the deleted team.
     * @throws ResponseStatusException if the team with the given ID is not found or an error occurs while deleting the
     *                                 team.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<TeamDTO> deleteTeamById(@PathVariable Long id) {
        try {
            if (teamService.getByIdDTO(id) == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Team not found");
            }

            TeamDTO deletedTeam = teamService.deleteDTO(id);

            if (deletedTeam == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Team not found");
            }

            return ResponseEntity.status(HttpStatus.OK).body(deletedTeam);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error deleting team");
        }
    }
}

