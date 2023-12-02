package teamx.app.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import teamx.app.backend.models.User;
import teamx.app.backend.models.dto.UserDTO;
import teamx.app.backend.models.JWToken;
import teamx.app.backend.repositories.UserRepository;

import java.util.Optional;

@Service
public class AuthenthicationService {

    private final UserRepository userRepository;

    private JWToken jwtTokenGenerator;

    @Autowired
    public AuthenthicationService(UserRepository userRepository, JWToken jwtTokenGenerator) {
        this.userRepository = userRepository;
        this.jwtTokenGenerator = jwtTokenGenerator;
    }

    public UserDTO authenticateUser(String email, String password) {
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            if (passwordMatches(user.getPassword(), password)) {
                String jwtToken = jwtTokenGenerator.generateToken(user.getId(), user.getEmail(), user.getRole().name());

                return new UserDTO(user.getId(), user.getEmail(), user.getRole().name(), jwtToken);
            }
        }

        return null;
    }

    private boolean passwordMatches(String storedPassword, String enteredPassword) {
        // For simplicity for now
        return storedPassword.equals(enteredPassword);
    }
}
