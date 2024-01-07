package teamx.app.backend.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import teamx.app.backend.utils.DTO.TeamDTO;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TeamTest {

    private Team team;
    private Warehouse warehouse;
    private User leader;
    private List<User> members;
    private List<Project> projects;

    @BeforeEach
    void setUp() {
        warehouse = Warehouse.builder()
                .id(1L)
                .name("Warehouse 1")
                .build();

        leader = User.builder()
                .id(1L)
                .name("Leader Name")
                .build();

        User member1 = User.builder()
                .id(2L)
                .name("Member 1")
                .build();

        User member2 = User.builder()
                .id(3L)
                .name("Member 2")
                .build();

        members = Arrays.asList(member1, member2);

        Project project1 = new Project();
        projects = Collections.singletonList(project1);

        team = Team.builder()
                .id(1L)
                .name("Team 1")
                .warehouse(warehouse)
                .leader(leader)
                .members(members)
                .projects(projects)
                .build();
    }

    @Test
    void testTeamGettersAndSetters() {
        assertEquals(1L, team.getId());
        assertEquals("Team 1", team.getName());
        assertEquals(warehouse, team.getWarehouse());
        assertEquals(leader, team.getLeader());
        assertEquals(members, team.getMembers());
        assertEquals(projects, team.getProjects());
    }

    @Test
    void testToDTO() {
        TeamDTO dto = team.toDTO();

        assertNotNull(dto);
        assertEquals(team.getId(), dto.getId());
        assertEquals(team.getName(), dto.getName());
        assertEquals(team.getWarehouse().getId(), dto.getWarehouseId());
        assertEquals(team.getLeader().getId(), dto.getLeaderId());
        assertNotNull(dto.getMembersIds());
        assertFalse(dto.getMembersIds().isEmpty());
        assertEquals(members.size(), dto.getMembersIds().size());
    }
}
