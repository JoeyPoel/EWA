package teamx.app.backend.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import teamx.app.backend.models.Team;
import teamx.app.backend.repositories.TeamRepository;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Tests team service
 *
 * @author Joey van der Poel
 */
class TeamServiceTests {

    @Mock
    private TeamRepository teamRepository;

    @Mock
    private WarehouseService warehouseService;

    @Mock
    private UserService userService;

    @Mock
    private ProjectService projectService;

    @InjectMocks
    private TeamService teamService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testFindAll() {
        // Arrange
        when(teamRepository.findAll()).thenReturn(Collections.emptyList());

        // Act
        List<Team> teams = teamService.findAll();

        // Assert
        assertEquals(0, teams.size());
        verify(teamRepository, times(1)).findAll();
    }

    @Test
    void testFindById_ExistingId_ReturnsTeam() {
        // Arrange
        Long teamId = 1L;
        Team mockTeam = new Team();
        when(teamRepository.findById(teamId)).thenReturn(Optional.of(mockTeam));

        // Act
        Team foundTeam = teamService.findById(teamId);

        // Assert
        assertNotNull(foundTeam);
        assertEquals(mockTeam, foundTeam);
        verify(teamRepository, times(1)).findById(teamId);
    }

    @Test
    void testFindAll_ReturnsListOfTeams() {
        // Arrange
        List<Team> mockTeams = Arrays.asList(new Team(/* Populate with necessary fields */),
                new Team(/* Populate with necessary fields */));
        when(teamRepository.findAll()).thenReturn(mockTeams);

        // Act
        List<Team> teams = teamService.findAll();

        // Assert
        assertNotNull(teams);
        assertEquals(2, teams.size());
        assertEquals(mockTeams, teams);
        verify(teamRepository, times(1)).findAll();
    }

    @Test
    void testFindAllByWarehouseId_ReturnsListOfTeamsByWarehouseId() {
        // Arrange
        Long warehouseId = 1L;
        List<Team> mockTeams = Arrays.asList(new Team(/* Populate with necessary fields */),
                new Team(/* Populate with necessary fields */));
        when(teamRepository.getAllByWarehouse_Id(warehouseId)).thenReturn(mockTeams);

        // Act
        List<Team> teamsByWarehouse = teamService.findAllByWarehouseId(warehouseId);

        // Assert
        assertNotNull(teamsByWarehouse);
        assertEquals(2, teamsByWarehouse.size());
        assertEquals(mockTeams, teamsByWarehouse);
        verify(teamRepository, times(1)).getAllByWarehouse_Id(warehouseId);
    }


    @Test
    void testDelete_ExistingId_ReturnsDeletedTeam() {
        // Arrange
        Long teamId = 1L;
        Team existingTeam = new Team(/* Populate with necessary fields */);

        when(teamRepository.findById(teamId)).thenReturn(Optional.of(existingTeam));

        // Act
        Team deletedTeam = teamService.delete(teamId);

        // Assert
        assertNotNull(deletedTeam);
    }
}
