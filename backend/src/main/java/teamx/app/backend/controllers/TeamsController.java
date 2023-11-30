package teamx.app.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import teamx.app.backend.models.Team;
import teamx.app.backend.models.dto.TeamDTO;
import teamx.app.backend.repositories.TeamRepository;
import teamx.app.backend.services.TeamService;

import java.util.ArrayList;
import java.util.List;

/**
 * Teams controller.
 * This class is a REST controller for the team model.
 *
 * @author Joey van der Poel
 * @author Junior Javier Brito Perez
 * @see Team
 * @see TeamRepository
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

    @GetMapping
    public ResponseEntity<List<TeamDTO>> getAll() {
        try {
            List<TeamDTO> teams = teamService.getAllDTO();

            if (teams.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ArrayList<>());
            }

            return ResponseEntity.status(HttpStatus.OK).body(teams);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error retrieving teams");
        }
    }

    @GetMapping("/warehouse/{warehouseId}")
    public ResponseEntity<List<TeamDTO>> getAllByWarehouseId(@PathVariable Long warehouseId) {
        try {
            List<TeamDTO> teams = teamService.getAllByWarehouseIdDTO(warehouseId);
            if (teams == null) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ArrayList<>());
            }
            return ResponseEntity.status(HttpStatus.OK).body(teams);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error retrieving teams" + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeamDTO> getById(@PathVariable Long id) {
        try {
            TeamDTO team = teamService.getByIdDTO(id);

            if (team == null) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
            }
            return ResponseEntity.status(HttpStatus.OK).body(team);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error retrieving team");
        }
    }

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

