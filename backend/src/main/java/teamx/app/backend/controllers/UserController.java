package teamx.app.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import teamx.app.backend.models.User;
import teamx.app.backend.models.dto.UserDTO;
import teamx.app.backend.repositories.UserRepository;
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
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@RequestBody User user) {
        try {
            UserDTO foundUser = userService.login(user);
            return new ResponseEntity<>(foundUser, HttpStatus.OK);
        } catch (ResponseStatusException e) {
            throw new ResponseStatusException(e.getStatusCode(), e.getReason() + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAll() {
        return ResponseEntity.ok(userService.findAllDTO());
    }

    @GetMapping("/noTeam")
    public ResponseEntity<List<UserDTO>> getAllNoTeam() {
        return ResponseEntity.ok(userService.getAllByNoTeamDTO());
    }


    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getById(@PathVariable Long id) {
        UserDTO user = userService.findByIdDTO(id);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @GetMapping("/team/{teamId}")
    public ResponseEntity<List<UserDTO>> getAllByTeamId(@PathVariable Long teamId) {
        try {
            List<UserDTO> users = userService.getAllByTeamIdDTO(teamId);
            if (users.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while getting user", e);
        }
    }

    @PostMapping
    public ResponseEntity<UserDTO> add(@RequestBody UserDTO user) {
        try {
            UserDTO newUser = userService.saveDTO(user);
            return new ResponseEntity<>(newUser, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error adding User");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> update(@PathVariable Long id, @RequestBody UserDTO user) {
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