package teamx.app.backend.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import teamx.app.backend.models.Team;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests team repository
 *
 * @author Joey van der Poel
 */
@DataJpaTest
class TeamRepositoryTests {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void testSaveTeam_ReturnsSavedTeam() {
        // Arrange
        Team teamToSave = new Team();

        // Act
        Team savedTeam = teamRepository.save(teamToSave);

        // Assert
        assertEquals(teamToSave, savedTeam);
        assertNotNull(savedTeam.getId()); // Ensure ID is assigned after saving
    }

    @Test
    void testFindById_ExistingId_ReturnsTeam() {
        // Arrange
        Team teamToFind = new Team();
        entityManager.persistAndFlush(teamToFind);

        // Act
        Optional<Team> foundTeamOptional = teamRepository.findById(teamToFind.getId());
        Team foundTeam = foundTeamOptional.orElse(null);

        // Assert
        assertTrue(foundTeamOptional.isPresent());
        assertEquals(teamToFind, foundTeam);
    }

    @Test
    void testDeleteById_ExistingId_DeletesTeam() {
        // Arrange
        Team teamToDelete = new Team();
        entityManager.persistAndFlush(teamToDelete);

        // Act
        teamRepository.deleteById(teamToDelete.getId());

        // Assert
        Optional<Team> deletedTeam = teamRepository.findById(teamToDelete.getId());
        assertTrue(deletedTeam.isEmpty());
    }
}
