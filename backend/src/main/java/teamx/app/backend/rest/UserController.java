package teamx.app.backend.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import teamx.app.backend.models.User;
import teamx.app.backend.repositories.UserRepositoryMock;

/**
 * User controller.
 * This class is a REST controller for the User model.
 *
 * @author Kaifie Dil.
 */
@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/users")
public class UserController implements Controller<User> {
    private final UserRepositoryMock userRepository;

    @Autowired
    public UserController(UserRepositoryMock userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/test")
    public ResponseEntity<?> getTestWarehouses() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable int id) {
        User user = userRepository.findById(id);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @PostMapping()
    public ResponseEntity<?> add(User user) {
        if (userRepository.findById(user.getUserId()) != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User already exists");
        }
        User savedUser = userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    @Override
    public ResponseEntity<?> update(int id, User user) {
        return null;
    }

    @Override
    public ResponseEntity<?> delete(int id) {
        return null;
    }
}
