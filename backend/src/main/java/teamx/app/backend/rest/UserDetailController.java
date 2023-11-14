package teamx.app.backend.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import teamx.app.backend.models.UserDetail;
import teamx.app.backend.repositories.UserDetailRepositoryMock;

/**
 * UserDetail controller.
 * This class is a REST controller for the UserDetail model.
 *
 * @author Kaifie Dil.
 */
@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/users")
public class UserDetailController implements Controller<UserDetail> {
    private final UserDetailRepositoryMock userRepository;

    @Autowired
    public UserDetailController(UserDetailRepositoryMock userRepository) {
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
        UserDetail user = userRepository.findById(id);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "UserDetail not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @PostMapping()
    public ResponseEntity<?> add(UserDetail user) {
        if (userRepository.findById(user.getUserId()) != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "UserDetail already exists");
        }
        UserDetail savedUser = userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    @Override
    public ResponseEntity<?> update(int id, UserDetail user) {
        return null;
    }

    @Override
    public ResponseEntity<?> delete(int id) {
        return null;
    }
}
