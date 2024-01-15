package teamx.app.backend.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import teamx.app.backend.models.User;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for user repository
 *
 * @Author Johnny Magielse
 */
@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager entityManager;

    // johnny
    @Test
    void findByEmail_ReturnsUser() {
        // Arrange
        User user = new User();
        user.setEmail("test@example.com");
        user.setName("Visitor");
        user.setRole(User.Role.USER);
        entityManager.persistAndFlush(user);

        // Act
        Optional<User> foundUser = userRepository.findByEmail(user.getEmail());

        // Assert
        assertTrue(foundUser.isPresent());
        assertEquals(user.getEmail(), foundUser.get().getEmail());
    }

    // johnny
    @Test
    void findByRole_ReturnsUsers() {
        List<User> foundUsers = userRepository.findByRole(User.Role.USER);

        assertEquals(49, foundUsers.size());
        assertTrue(foundUsers.stream().allMatch(user -> user.getRole() == User.Role.USER));
    }

    // johnny
    @Test
    void findByRole_ReturnsAdmins() {
        List<User> foundAdmins = userRepository.findByRole(User.Role.ADMIN);

        assertEquals(2, foundAdmins.size());
        assertTrue(foundAdmins.stream().allMatch(user -> user.getRole() == User.Role.ADMIN));
    }
}

