package teamx.app.backend.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import teamx.app.backend.models.User;
import teamx.app.backend.services.UserService;

import java.util.List;

/**
 * User Controller
 * This class is a REST controller for the user model.
 *
 * @author Johnny Magielse
 * @author Kaifie Dilmohamed
 * @author Junior Javier Brito Perez
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
// TODO: Return DTOs instead of entities
    private final UserService userService;

    /*@PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody User user) {
        return ResponseEntity.ok(userService.login(user));
    }*/

    @GetMapping
    public ResponseEntity<List<User>> getAll() {
        return ResponseEntity.ok(userService.getAll());
    }

    @GetMapping("/noTeam")
    public ResponseEntity<List<User>> getAllNoTeam() {
        return ResponseEntity.ok(userService.getAllByNoTeam());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getById(id));
    }

    @GetMapping("/team/{teamId}")
    public ResponseEntity<List<User>> getAllByTeamId(@PathVariable Long teamId) {
        return ResponseEntity.ok(userService.getAllByTeamId(teamId));
    }

    @PostMapping("/add")
    public ResponseEntity<User> add(@RequestBody User user) {
        User createdUser = userService.add(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable Long id, @RequestBody User user) {
        return ResponseEntity.ok(userService.update(user, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> delete(@PathVariable Long id) {
        return ResponseEntity.ok(userService.delete(id));
    }
}