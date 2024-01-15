package teamx.app.backend.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import teamx.app.backend.models.User;
import teamx.app.backend.repositories.UserRepository;
import teamx.app.backend.utils.DTO.UserDTO;
import teamx.app.backend.models.JWToken;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.when;

/**
 * Tests for authentication service
 *
 * @Author Johnny Magielse
 */
@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private JWToken jwtTokenGenerator;

    @InjectMocks
    private AuthenthicationService authenthicationService;

    // johnny
    @Test
    void generateResetPassToken_ReturnsUserDTO() {
        // Arrange
        String email = "test@example.com";
        String jwtToken = "fakeToken";
        User.Role role = User.Role.ADMIN;
        Long id = 1L;

        User user = new User();
        user.setId(id);
        user.setEmail(email);
        user.setRole(role);

        UserDTO userDTO = UserDTO.builder()
                .id(id)
                .email(email)
                .role(role.name())
                .jwtToken(jwtToken)
                .build();

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(jwtTokenGenerator.generateToken(id, email, role.name())).thenReturn(jwtToken);

        // Act
        UserDTO result = authenthicationService.generateResetPassToken(email);

        // Assert
        assertEquals(userDTO.getId(), result.getId());
        assertEquals(userDTO.getEmail(), result.getEmail());
        assertEquals(userDTO.getRole(), result.getRole());
        assertEquals(userDTO.getJwtToken(), result.getJwtToken());
    }

    // johnny
    @Test
    void generateResetPassToken_ReturnsIncorrectUserDTO_Fails() {
        // Arrange
        String email = "test@example.com";
        String jwtToken = "fakeToken";
        User.Role role = User.Role.ADMIN;
        Long id = 1L;

        User user = new User();
        user.setId(id);
        user.setEmail(email);
        user.setRole(role);

        UserDTO incorrectUserDTO = UserDTO.builder()
                .id(id)
                .email("incorrect@example.com") // Incorrect email
                .role(role.name())
                .jwtToken(jwtToken)
                .build();

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        when(jwtTokenGenerator.generateToken(id, email, role.name())).thenReturn(jwtToken);

        // Act
        UserDTO result = authenthicationService.generateResetPassToken(email);

        // Assert
        assertNotEquals(incorrectUserDTO.getEmail(), result.getEmail());
    }
}
