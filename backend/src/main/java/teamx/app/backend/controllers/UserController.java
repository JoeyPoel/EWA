package teamx.app.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import teamx.app.backend.models.User;
import teamx.app.backend.repositories.UserRepository;

import java.util.List;
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
@RequestMapping("/user")
public class UserController {

    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<User>> getAll() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(userRepository.findAll());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong");
        }
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
}