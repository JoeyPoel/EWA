package teamx.app.backend.rest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import teamx.app.backend.models.Team;
import teamx.app.backend.repositories.TeamRepositoryMock;

/**
 * Teams controller.
 * This class is a REST controller for the team model.
 *
 * @author Joey van der Poel
 */
@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/teams")
public class TeamsController implements Controller<Team> {
    private final TeamRepositoryMock teamRepository;

    @Autowired
    public TeamsController(TeamRepositoryMock teamRepository) {
        this.teamRepository = teamRepository;
    }

    @GetMapping("/test")
    public ResponseEntity<?> getTestWarehouses() {
        return ResponseEntity.ok(teamRepository.findAll());
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(teamRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable int id) {
        Team team = teamRepository.findById(id);
        if (team == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Team not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(team);
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody Team team) {
        if (teamRepository.findById(team.getId()) != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Team already exists");
        }
        teamRepository.save(team);
        return ResponseEntity.status(HttpStatus.CREATED).body(team);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable int id, @RequestBody Team team) {
        if (id != team.getId()) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Team id mismatch");
        }
        if (teamRepository.findById(team.getId()) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Team not found");
        }
        Team savedTeam = teamRepository.put(id,team);
        return ResponseEntity.status(HttpStatus.OK).body(savedTeam);
    }

    @DeleteMapping( "/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        Team team = teamRepository.deleteById(id);
        if (team == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Team not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(team);
    }
}

