package teamx.app.backend.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import teamx.app.backend.models.User;
import teamx.app.backend.repositories.UserRepository;
import teamx.app.backend.services.ExceptionService;
import teamx.app.backend.services.UserService;

import java.util.List;

/**
 * User Controller
 * This class is a REST controller for the user model.
 *
 * @author Johnny Magielse
 * @author Kaifie Dilmohamed
 * @author Junior Javier Brito Perez
 * @see User
 * @see UserRepository
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final ExceptionService exceptionService;

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody User user) {
        return exceptionService.handle(() ->
                userService.login(user)
        );
    }

    @GetMapping
    public ResponseEntity<List<User>> getAll() {
        return exceptionService.handle(
                userService::findAll
        );
    }

    @GetMapping("/noTeam")
    public ResponseEntity<List<User>> getAllNoTeam() {
        return exceptionService.handle(
                userService::getAllByNoTeam
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getById(@PathVariable Long id) {
        return exceptionService.handle(() ->
                userService.findById(id)
        );
    }

    @GetMapping("/team/{teamId}")
    public ResponseEntity<List<User>> getAllByTeamId(@PathVariable Long teamId) {
        return exceptionService.handle(() ->
                userService.getAllByTeamId(teamId)
        );
    }

    @PostMapping
    public ResponseEntity<User> add(@RequestBody User user) {
        return exceptionService.handle(() ->
                userService.add(user)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable Long id, @RequestBody User user) {
        UserDTO existingUser = userService.findByIdDTO(id);
        if (existingUser == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        UserDTO updatedUser = userService.saveDTO(user);
        return ResponseEntity.status(HttpStatus.OK).body(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserDTO> delete(@PathVariable Long id) {
        UserDTO existingUser = userService.findByIdDTO(id);
        if (existingUser == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        userService.deleteByIdDTO(id);
        return ResponseEntity.status(HttpStatus.OK).body(existingUser);
    }
}