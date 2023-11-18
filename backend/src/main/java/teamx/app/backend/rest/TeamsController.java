package teamx.app.backend.rest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import teamx.app.backend.models.Team;
import teamx.app.backend.repositories.TeamRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Teams controller.
 * This class is a REST controller for the team model.
 *
 * @author Joey van der Poel
 * @author Junior Javier Brito Perez
 * @see Team
 * @see TeamRepository
 */
@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/teams")
public class TeamsController {
    private final TeamRepository teamRepository;

    @Autowired
    public TeamsController(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @GetMapping("/test")
    public ResponseEntity<?> getTestWarehouses() {
        return ResponseEntity.ok(teamRepository.findAll());
    }

    @GetMapping("/getAllTeams")
    public ResponseEntity<List<Team>> getAllTeams() {
        try {
            List<Team> teams = new ArrayList<>(teamRepository.findAll());
            if (teams.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No teams found");
            }
            return ResponseEntity.ok(teamRepository.findAll());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @GetMapping("/getTeamById/{id}")
    public ResponseEntity<Team> getTeamById(@PathVariable Long id) {
        try {
           Optional<Team> team = teamRepository.findById(id);
            if (team.isPresent()) {
                return ResponseEntity.ok(team.get());
            } else {
                throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No team found");
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error retrieving team");
        }
    }

    @PostMapping("/addTeam")
    public ResponseEntity<Team> addTeam(@RequestBody Team team) {
        try {
            Team newTeam = teamRepository.save(team);
            return ResponseEntity.status(HttpStatus.CREATED).body(newTeam);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error adding team");
        }
    }

    @PutMapping("/updateTeamById/{id}")
    public ResponseEntity<Team> updateTeamById(@PathVariable Long id, @RequestBody Team team) {
        try {
            Optional<Team> originalTeam = teamRepository.findById(id);
            if (originalTeam.isPresent()) {
                Team updatedTeam = updateTeamData(team, originalTeam);
                teamRepository.save(updatedTeam);
                return ResponseEntity.ok(updatedTeam);
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Team not found");
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error updating team");
        }
    }

    private Team updateTeamData(Team team, Optional<Team> originalTeam) {
        Team updatedTeam = originalTeam.get();
        updatedTeam.setWarehouse(team.getWarehouse());
        updatedTeam.setName(team.getName());
//        updatedTeam.setMembers(team.getMembers());
        return updatedTeam;
    }

    @DeleteMapping( "/deleteTeamById/{id}")
    public ResponseEntity<Team> deleteTeamById(@PathVariable Long id) {
        try {
            Optional<Team> team = teamRepository.findById(id);
            if (team.isPresent()) {
                teamRepository.deleteById(id);
                return new ResponseEntity<>(team.get(), HttpStatus.OK);
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Team not found");
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error deleting team");
        }
    }
}

