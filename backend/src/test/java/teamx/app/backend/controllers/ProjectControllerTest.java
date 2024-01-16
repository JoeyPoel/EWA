package teamx.app.backend.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import teamx.app.backend.models.Project;
import teamx.app.backend.models.dto.InventoryProjectDTO;
import teamx.app.backend.models.dto.TaskDTO;
import teamx.app.backend.services.ProjectService;
import teamx.app.backend.utils.DTO.ProjectDTO;

import java.util.Arrays;
import java.util.List;
import java.util.TimeZone;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ProjectController.class)
class ProjectControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProjectService projectService;

    private Project project1;
    private Project project2;

    @BeforeEach
    void setUp() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));

        project1 = new Project();
        project1.setId(1L);
        project1.setName("Test Project 1");
        project1.setDescription("Description 1");
        project1.setStartDate(java.sql.Date.valueOf("2021-01-01"));
        project1.setEndDate(java.sql.Date.valueOf("2021-01-30"));
        project1.setStatus(Project.Status.IN_PROGRESS);
        project1.setLocation("Location 1");
        project1.setClientName("Client 1");
        project1.setClientEmail("client1@email.com");
        project1.setClientPhone("1234567890");


        project2 = new Project();
        project2.setId(2L);
        project2.setName("Test Project 2");
        project2.setDescription("Description 2");
        project2.setStartDate(java.sql.Date.valueOf("2021-02-01"));
        project2.setEndDate(java.sql.Date.valueOf("2021-02-28"));
        project2.setStatus(Project.Status.IN_PROGRESS);
        project2.setLocation("Location 2");
        project2.setClientName("Client 2");
        project2.setClientEmail("client2@email.com");

        List<Project> projects = Arrays.asList(project1, project2);

        when(projectService.findAll()).thenReturn(projects);
        when(projectService.findById(1L)).thenReturn(project1);

    }

    @Test
    void getAllProjects() throws Exception {
        mockMvc.perform(get("/projects"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(project1.getId().intValue())))
                .andExpect(jsonPath("$[0].name", is(project1.getName())))
                .andExpect(jsonPath("$[0].description", is(project1.getDescription())))
                .andExpect(jsonPath("$[0].startDate", is(project1.getStartDate().toString())))
                .andExpect(jsonPath("$[0].endDate", is(project1.getEndDate().toString())))
                .andExpect(jsonPath("$[0].status", is(project1.getStatus().toString())))
                .andExpect(jsonPath("$[0].location", is(project1.getLocation())))
                .andExpect(jsonPath("$[0].clientName", is(project1.getClientName())))
                .andExpect(jsonPath("$[0].clientEmail", is(project1.getClientEmail())))
                .andExpect(jsonPath("$[0].clientPhone", is(project1.getClientPhone())))
                .andExpect(jsonPath("$[1].id", is(project2.getId().intValue())))
                .andExpect(jsonPath("$[1].name", is(project2.getName())))
                .andExpect(jsonPath("$[1].description", is(project2.getDescription())))
                .andExpect(jsonPath("$[1].startDate", is(project2.getStartDate().toString())))
                .andExpect(jsonPath("$[1].endDate", is(project2.getEndDate().toString())))
                .andExpect(jsonPath("$[1].status", is(project2.getStatus().toString())))
                .andExpect(jsonPath("$[1].location", is(project2.getLocation())))
                .andExpect(jsonPath("$[1].clientName", is(project2.getClientName())))
                .andExpect(jsonPath("$[1].clientEmail", is(project2.getClientEmail())))
                .andExpect(jsonPath("$[1].clientPhone", is(project2.getClientPhone())));
    }

    @Test
    void getProjectById() throws Exception {
        mockMvc.perform(get("/projects/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(project1.getId().intValue())));
    }

    @Test
    void getAllProjectsByWarehouseId() throws Exception {
        when(projectService.findAllByWarehouseId(any(Long.class))).thenReturn(Arrays.asList(project1, project2));

        mockMvc.perform(get("/projects/warehouse/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(project1.getId().intValue())))
                .andExpect(jsonPath("$[0].name", is(project1.getName())))
                .andExpect(jsonPath("$[0].description", is(project1.getDescription())))
                .andExpect(jsonPath("$[0].startDate", is(project1.getStartDate().toString())))
                .andExpect(jsonPath("$[0].endDate", is(project1.getEndDate().toString())))
                .andExpect(jsonPath("$[0].status", is(project1.getStatus().toString())))
                .andExpect(jsonPath("$[0].location", is(project1.getLocation())))
                .andExpect(jsonPath("$[0].clientName", is(project1.getClientName())))
                .andExpect(jsonPath("$[0].clientEmail", is(project1.getClientEmail())))
                .andExpect(jsonPath("$[0].clientPhone", is(project1.getClientPhone())))
                .andExpect(jsonPath("$[1].id", is(project2.getId().intValue())))
                .andExpect(jsonPath("$[1].name", is(project2.getName())))
                .andExpect(jsonPath("$[1].description", is(project2.getDescription())))
                .andExpect(jsonPath("$[1].startDate", is(project2.getStartDate().toString())))
                .andExpect(jsonPath("$[1].endDate", is(project2.getEndDate().toString())))
                .andExpect(jsonPath("$[1].status", is(project2.getStatus().toString())))
                .andExpect(jsonPath("$[1].location", is(project2.getLocation())))
                .andExpect(jsonPath("$[1].clientName", is(project2.getClientName())))
                .andExpect(jsonPath("$[1].clientEmail", is(project2.getClientEmail())))
                .andExpect(jsonPath("$[1].clientPhone", is(project2.getClientPhone())));
    }

    @Test
    void addProject() throws Exception {
        ProjectDTO newProjectDTO = new ProjectDTO();
        newProjectDTO.setName("Test Project 3");
        newProjectDTO.setDescription("Description 3");
        newProjectDTO.setStartDate(java.sql.Date.valueOf("2021-03-01"));
        newProjectDTO.setEndDate(java.sql.Date.valueOf("2021-03-31"));

        when(projectService.add(any(ProjectDTO.class))).thenReturn(project1);

        mockMvc.perform(post("/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(newProjectDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(project1.getId().intValue())));
    }

    @Test
    void updateProjectById() throws Exception {
        ProjectDTO updatedProjectDTO = new ProjectDTO();
        updatedProjectDTO.setId(1L);
        updatedProjectDTO.setName("Test Project 1");
        updatedProjectDTO.setDescription("Description 1");
        updatedProjectDTO.setStartDate(java.sql.Date.valueOf("2021-01-01"));
        updatedProjectDTO.setEndDate(java.sql.Date.valueOf("2021-01-31"));
        updatedProjectDTO.setStatus(Project.Status.FINISHED.toString());

        when(projectService.update(any(Long.class), any(ProjectDTO.class))).thenReturn(project1);

        mockMvc.perform(put("/projects/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(updatedProjectDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(project1.getId().intValue())))
                .andExpect(jsonPath("$.name", is(project1.getName())))
                .andExpect(jsonPath("$.description", is(project1.getDescription())))
                .andExpect(jsonPath("$.startDate", is(project1.getStartDate().toString())))
                .andExpect(jsonPath("$.endDate", is(project1.getEndDate().toString())))
                .andExpect(jsonPath("$.status", is(project1.getStatus().toString())))
                .andExpect(jsonPath("$.location", is(project1.getLocation())))
                .andExpect(jsonPath("$.clientName", is(project1.getClientName())))
                .andExpect(jsonPath("$.clientEmail", is(project1.getClientEmail())))
                .andExpect(jsonPath("$.clientPhone", is(project1.getClientPhone())));

    }

    @Test
    void deleteProjectById() throws Exception {
        when(projectService.delete(any(Long.class))).thenReturn(project1);

        mockMvc.perform(delete("/projects/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(project1.getId().intValue())));
    }

    @Test
    void getAllInventory() throws Exception {
        // Stubbing the service method
        InventoryProjectDTO mockedInventory1 = new InventoryProjectDTO();
        mockedInventory1.setId(1L);
        mockedInventory1.setQuantity(10);
        mockedInventory1.setProductName("Product 1");
        mockedInventory1.setWarehouseName("Warehouse 1");
        mockedInventory1.setTransactionType("MATERIAL");
        mockedInventory1.setTransactionDate(java.sql.Date.valueOf("2021-01-01"));

        InventoryProjectDTO mockedInventory2 = new InventoryProjectDTO();
        mockedInventory2.setId(2L);
        mockedInventory2.setQuantity(20);
        mockedInventory2.setProductName("Product 2");
        mockedInventory2.setWarehouseName("Warehouse 2");
        mockedInventory2.setTransactionType("MATERIAL");
        mockedInventory2.setTransactionDate(java.sql.Date.valueOf("2021-02-01"));

        when(projectService.getProjectMaterials(any(Long.class)))
                .thenReturn(Arrays.asList(mockedInventory1, mockedInventory2));

        mockMvc.perform(get("/projects/inventory/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(mockedInventory1.getId().intValue())))
                .andExpect(jsonPath("$[1].id", is(mockedInventory2.getId().intValue())))
                .andExpect(jsonPath("$[0].quantity", is(mockedInventory1.getQuantity())))
                .andExpect(jsonPath("$[1].quantity", is(mockedInventory2.getQuantity())))
                .andExpect(jsonPath("$[0].productName", is(mockedInventory1.getProductName())))
                .andExpect(jsonPath("$[1].productName", is(mockedInventory2.getProductName())))
                .andExpect(jsonPath("$[0].warehouseName", is(mockedInventory1.getWarehouseName())))
                .andExpect(jsonPath("$[1].warehouseName", is(mockedInventory2.getWarehouseName())))
                .andExpect(jsonPath("$[0].transactionType", is(mockedInventory1.getTransactionType())))
                .andExpect(jsonPath("$[1].transactionType", is(mockedInventory2.getTransactionType())));
    }


    @Test
    void getAllTasks() throws Exception {
        TaskDTO mockedTaskDTO = TaskDTO.builder()
                .id(1L)
                .order(1)
                .name("Task 1")
                .description("Description 1")
                .deadline("2022-01-31")
                .status("Pending")
                .projectId(123L)
                .personalTodoListOwnerId(456L)
                .build();

        when(projectService.getProjectTasks(any(Long.class))).thenReturn(Arrays.asList(mockedTaskDTO));
        String responseContent = mockMvc.perform(get("/projects/tasks/1"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        System.out.println("Response Content: " + responseContent);

    }


    @Test
    void getAllByTeamId() throws Exception {
        when(projectService.findAllByTeamId(any(Long.class))).thenReturn(Arrays.asList(project1, project2));

        mockMvc.perform(get("/projects/team/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(project1.getId().intValue())))
                .andExpect(jsonPath("$[1].id", is(project2.getId().intValue())));
    }


    // method includes a try-catch block for
    // handling exceptions during JSON serialization.

    private static String asJsonString(final Object obj) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
