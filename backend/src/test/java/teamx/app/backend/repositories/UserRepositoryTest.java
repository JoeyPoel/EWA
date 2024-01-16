package teamx.app.backend.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;
import teamx.app.backend.models.Team;
import teamx.app.backend.models.User;

import java.util.Arrays;
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

    // Kaifie
    @Test
    void getAllByIdIn_ReturnsUsers() {
        entityManager.clear();
        entityManager.flush();
        // Arrange
        User user1 = new User();
        user1.setEmail("test1@example.com");
        user1.setName("Visitor1");
        user1.setRole(User.Role.USER);
        entityManager.persistAndFlush(user1);

        User user2 = new User();
        user2.setEmail("test2@example.com");
        user2.setName("Visitor2");
        user2.setRole(User.Role.USER);
        entityManager.persistAndFlush(user2);

        List<Long> ids = Arrays.asList(user1.getId(), user2.getId());

        // Act
        List<User> foundUsers = userRepository.getAllByIdIn(ids);

        // Assert
        assertEquals(2, foundUsers.size());
        assertTrue(foundUsers.stream().allMatch(u -> ids.contains(u.getId())));
    }
}

