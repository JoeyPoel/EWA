package teamx.app.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import teamx.app.backend.models.User;
import teamx.app.backend.repositories.UserRepository;
import teamx.app.backend.services.UserService;

import java.util.List;
import java.util.Optional;

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
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody User user) {
        try {
            Optional<User> foundUser = userService.findByEmail(user.getEmail());
            if (foundUser.isPresent()) {
                if (!foundUser.get().getPassword().equals(user.getPassword())) {
                    throw new ResponseStatusException(HttpStatus.CONFLICT, "Wrong password");
                }
                return ResponseEntity.status(HttpStatus.OK).body(foundUser.get());
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong");
        }
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        Optional<User> user = userService.findById(id);
        if (user.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(user.get());
    }

    @GetMapping("/team/{teamId}")
    public ResponseEntity<List<User>> getAllByTeamId(@PathVariable Long teamId) {
        try {
            List<User> users = userService.getAllByTeamId(teamId);
            if (users.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while getting user", e);
        }
    }

    @PostMapping
    public ResponseEntity<User> add(@RequestBody User user) {
        try {
            User newUser = userService.save(user);
            return new ResponseEntity<>(newUser, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error adding User");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody User user) {
        Optional<User> existingUser = userService.findById(id);
        if (existingUser.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        User updatedUser = userService.save(user);
        return ResponseEntity.status(HttpStatus.OK).body(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Optional<User> existingUser = userService.findById(id);
        if (existingUser.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        userService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}