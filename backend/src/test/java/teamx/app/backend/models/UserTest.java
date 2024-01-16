package teamx.app.backend.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import teamx.app.backend.utils.DTO.UserDTO;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private User user;
    private Team team;

    @BeforeEach
    void setUp() {
        team = new Team();
        team.setId(1L);

        user = User.builder()
                .id(1L)
                .name("John Doe")
                .email("john@example.com")
                .password("password123")
                .role(User.Role.USER)
                .team(team)
                .build();
    }

    @Test
    void testUserGettersAndSetters() {
        assertEquals(1L, user.getId());
        assertEquals("John Doe", user.getName());
        assertEquals("john@example.com", user.getEmail());
        assertEquals("password123", user.getPassword());
        assertEquals(User.Role.USER, user.getRole());
        assertEquals(team, user.getTeam());
    }

    @Test
    void testToString() {
        String expectedString = "User{id=1, name=John Doe, email=john@example.com, role=USER, team=1}";
        assertEquals(expectedString, user.toString());
    }

    @Test
    void testToDTO() {
        UserDTO dto = user.toDTO();

        assertNotNull(dto);
        assertEquals(user.getId(), dto.getId());
        assertEquals(user.getName(), dto.getName());
        assertEquals(user.getEmail(), dto.getEmail());
        assertEquals(String.valueOf(user.getRole()), dto.getRole());
        assertEquals(user.getTeam().getId(), dto.getTeamId());
        assertNull(dto.getJwtToken());
    }

    @Test
    void testToDTOWithToken() {
        String jwtToken = "dummyToken";
        UserDTO dto = user.toDTO(jwtToken);

        assertNotNull(dto);
        assertEquals(user.getId(), dto.getId());
        assertEquals(user.getName(), dto.getName());
        assertEquals(user.getEmail(), dto.getEmail());
        assertEquals(String.valueOf(user.getRole()), dto.getRole());
        assertEquals(user.getTeam().getId(), dto.getTeamId());
        assertEquals(jwtToken, dto.getJwtToken());
    }

    // Kaifie
    @Test
    void testUserConstructor() {
        User user = new User(1L, "John Doe", "john@example.com", "password123", User.Role.USER, team);
        assertEquals(1L, user.getId());
        assertEquals("John Doe", user.getName());
        assertEquals("john@example.com", user.getEmail());
        assertEquals("password123", user.getPassword());
        assertEquals(User.Role.USER, user.getRole());
        assertEquals(team, user.getTeam());
    }

    // Kaifie
    @Test
    void testUserToDTOWithNullTeam() {
        User userWithNullTeam = new User(1L, "John Doe", "john@example.com", "password123", User.Role.USER, null);
        UserDTO dto = userWithNullTeam.toDTO();
        assertNotNull(dto);
        assertEquals(userWithNullTeam.getId(), dto.getId());
        assertEquals(userWithNullTeam.getName(), dto.getName());
        assertEquals(userWithNullTeam.getEmail(), dto.getEmail());
        assertEquals(String.valueOf(userWithNullTeam.getRole()), dto.getRole());
        assertNull(dto.getTeamId());
        assertNull(dto.getJwtToken());
    }
}
