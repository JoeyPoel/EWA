package teamx.app.backend.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import teamx.app.backend.models.User;
import teamx.app.backend.repositories.UserRepository;

import java.util.Optional;

/**
 * User Controller
 * This class is a REST controller for the user model.
 *
 * @author Johnny Magielse
 * @author Junior Javier Brito Perez
 * @see User
 * @see UserRepository
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody User user) {
        try {
            Optional<User> foundUser = userRepository.findByEmail(user.getEmail());
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

    @GetMapping("/all")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "UserDetail not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(user.get());
    }

    @PostMapping()
    public ResponseEntity<?> add(User user) {
        if (userRepository.findById((long) user.getId()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "UserDetail already exists");
        }
        User savedUser = userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody User user) {
        Optional<User> existingUser = userRepository.findById(id);
        if (existingUser.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        User updatedUser = userRepository.save(user);
        return ResponseEntity.status(HttpStatus.OK).body(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Optional<User> existingUser = userRepository.findById(id);
        if (existingUser.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        userRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}