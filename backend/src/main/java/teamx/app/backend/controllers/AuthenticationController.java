package teamx.app.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import teamx.app.backend.models.User;
import teamx.app.backend.models.dto.UserDTO;
import teamx.app.backend.services.AuthenthicationService;

/**
 * AuthenticationController handles authentication-related HTTP requests.
 *
 * This controller provides an endpoint for user login. It receives a User object
 * in the request body and attempts to authenticate the user.
 * Further implementation will be added
 *
 * @author Nizar Amine
 */

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenthicationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenthicationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    /**
     * Handles user login requests.
     *
     * @param user The User object containing login credentials.
     * @return ResponseEntity with UserDTO and JWT token on successful authentication,
     * 401 status otherwise.
     */

    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@RequestBody User user) {
        UserDTO userDTO = authenticationService.authenticateUser(user);
        if (userDTO != null) {
            return ResponseEntity.ok(userDTO);
        } else {
            return ResponseEntity.status(401).body(null);
        }
    }


}
