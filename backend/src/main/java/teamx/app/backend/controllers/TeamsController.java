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

    @GetMapping("/getAllTeams")
    public ResponseEntity<List<Team>> getAllTeams() {
        try {
            List<Team> teams = teamService.getAll();
            if (teams == null) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ArrayList<>());
            }
            return ResponseEntity.status(HttpStatus.OK).body(teams);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error retrieving teams");
        }
    }

    @GetMapping("/getAllTeamsByWarehouseId/{warehouseId}")
    public ResponseEntity<List<TeamDTO>> getAllTeamsByWarehouseId(@PathVariable Long warehouseId) {
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

    @GetMapping("/getTeamById/{id}")
    public ResponseEntity<Team> getTeamById(@PathVariable Long id) {
        try {
            Team team = teamService.getTeamById(id);
            if (team == null) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
            }
            return ResponseEntity.status(HttpStatus.OK).body(team);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error retrieving team");
        }
    }

    //TODO: add validation
    @PostMapping("/addTeam")
    public ResponseEntity<Team> addTeam(@RequestBody Team team) {
        try {
            Team newTeam = teamService.addTeam(team);
            if (newTeam == null) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Team already exists");
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(newTeam);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error adding team");
        }
    }

    @PutMapping("/updateTeamById/{id}")
    public ResponseEntity<Team> updateTeamById(@PathVariable Long id, @RequestBody Team team) {
        try {
            Team updatedTeam = teamService.updateTeam(team, id);
            if (updatedTeam == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Team not found");
            }
            return ResponseEntity.status(HttpStatus.OK).body(updatedTeam);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error updating team");
        }
    }

    @DeleteMapping("/deleteTeamById/{id}")
    public ResponseEntity<Team> deleteTeamById(@PathVariable Long id) {
        try {
            Team deletedTeam = teamService.deleteTeam(id);
            if (deletedTeam == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Team not found");
            }
            return ResponseEntity.status(HttpStatus.OK).body(deletedTeam);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error deleting team");
        }
    }
}

