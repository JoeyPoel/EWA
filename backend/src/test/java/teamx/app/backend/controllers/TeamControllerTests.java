package teamx.app.backend.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import teamx.app.backend.models.Project;
import teamx.app.backend.models.Team;
import teamx.app.backend.models.User;
import teamx.app.backend.models.Warehouse;
import teamx.app.backend.services.TeamService;
import teamx.app.backend.utils.DTO.TeamDTO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Tests team controller
 *
 * @author Joey van der Poel
 */
@WebMvcTest(controllers = TeamsController.class)
@AutoConfigureMockMvc
class TeamsControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TeamService teamService;

    private Team commonTeam; // Common Team object for tests



    @BeforeEach
    void setUp() {
        // Create a common Warehouse object for testing
        Warehouse warehouse = new Warehouse();
        warehouse.setId(1L); // Set the ID or relevant fields

        // Create a common User object for testing
        User leader = new User();
        leader.setId(1L); // Set the ID or relevant fields

        // Create a list of members for the team
        List<User> members = new ArrayList<>();
        User member1 = new User();
        member1.setId(2L); // Set the ID or relevant fields
        members.add(member1);

        // Create a list of projects for the team
        List<Project> projects = new ArrayList<>();
        Project project1 = new Project();
        project1.setId(1L); // Set the ID or relevant fields
        projects.add(project1);

        // Create a common Team object with necessary fields for testing
        commonTeam = Team.builder()
                .id(1L) // Set the ID or relevant fields
                .name("Test Team")
                .warehouse(warehouse)
                .leader(leader)
                .members(members)
                .projects(projects)
                .build();
    }

    @Test
    void testFindAll_ReturnsListOfTeams() throws Exception {
        // Arrange
        when(teamService.findAll()).thenReturn(Collections.singletonList(commonTeam));

        // Act & Assert
        mockMvc.perform(get("/teams")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andReturn();
    }

    @Test
    void testGetById_ReturnsTeamById() throws Exception {
        // Arrange
        Long teamId = 1L;
        when(teamService.findById(teamId)).thenReturn(commonTeam);

        // Act & Assert
        mockMvc.perform(get("/teams/{id}", teamId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
    }


    @Test
    void testGetAllByWarehouseId_ReturnsTeamsByWarehouseId() throws Exception {
        // Arrange
        Long warehouseId = 1L;
        when(teamService.findAllByWarehouseId(warehouseId)).thenReturn(Collections.singletonList(commonTeam));

        // Act & Assert
        mockMvc.perform(get("/teams/warehouse/{warehouseId}", warehouseId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andReturn();
    }

    @Test
    void testAdd_AddsNewTeam() throws Exception {
        // Arrange
        when(teamService.add(any())).thenReturn(commonTeam);

        // Act & Assert
        mockMvc.perform(post("/teams")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new TeamDTO())))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
    }

    @Test
    void testUpdateById_UpdatesTeamById() throws Exception {
        // Arrange
        Long teamId = 1L;
        when(teamService.update(eq(teamId), any())).thenReturn(commonTeam);

        // Act & Assert
        mockMvc.perform(put("/teams/{id}", teamId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new TeamDTO())))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
    }

    @Test
    void testDeleteTeamById_DeletesTeamById() throws Exception {
        // Arrange
        Long teamId = 1L;
        when(teamService.delete(teamId)).thenReturn(commonTeam);

        // Act & Assert
        mockMvc.perform(delete("/teams/{id}", teamId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
    }
}
