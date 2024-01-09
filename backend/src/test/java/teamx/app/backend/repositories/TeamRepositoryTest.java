package teamx.app.backend.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import teamx.app.backend.models.Team;
import teamx.app.backend.models.Warehouse;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class TeamRepositoryTest {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private TestEntityManager entityManager;

    private Warehouse testWarehouse;

    @BeforeEach
    void setUp() {
        testWarehouse = new Warehouse();
        testWarehouse.setName("Warehouse Name");
        testWarehouse.setLocation("Warehouse Location");
        testWarehouse.setAddress("Warehouse Address");
        testWarehouse.setPostcode("1234XK");
        testWarehouse.setCountry("Warehouse Country");
        testWarehouse = entityManager.persist(testWarehouse);
    }

    @Test
    void testSaveTeam_ReturnsSavedTeam() {
        // Arrange
        Team teamToSave = new Team();
        teamToSave.setName("Team Name");
        teamToSave.setWarehouse(testWarehouse);

        // Act
        Team savedTeam = teamRepository.save(teamToSave);

        // Assert
        assertEquals(teamToSave, savedTeam);
        assertNotNull(savedTeam.getId());
    }

    @Test
    void testFindById_ExistingId_ReturnsTeam() {
        // Arrange
        Team teamToFind = new Team();
        teamToFind.setName("Team Name");
        teamToFind.setWarehouse(testWarehouse);
        entityManager.persistAndFlush(teamToFind);

        // Act
        Optional<Team> foundTeamOptional = teamRepository.findById(teamToFind.getId());

        // Assert
        assertTrue(foundTeamOptional.isPresent());
        assertEquals(teamToFind, foundTeamOptional.get());
    }

    @Test
    void testDeleteById_ExistingId_DeletesTeam() {
        // Arrange
        Team teamToDelete = new Team();
        teamToDelete.setName("Team Name");
        teamToDelete.setWarehouse(testWarehouse);
        entityManager.persistAndFlush(teamToDelete);

        // Act
        teamRepository.deleteById(teamToDelete.getId());

        // Assert
        Optional<Team> deletedTeam = teamRepository.findById(teamToDelete.getId());
        assertTrue(deletedTeam.isEmpty());
    }
}
