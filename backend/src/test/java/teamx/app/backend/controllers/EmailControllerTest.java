package teamx.app.backend.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import teamx.app.backend.models.Project;
import teamx.app.backend.services.*;
import teamx.app.backend.utils.DTO;

import java.time.LocalDate;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Tests for email controller
 *
 * @author Joey van der Poel
 * @author Johnny Magielse
 */
@ExtendWith(MockitoExtension.class)
class EmailControllerTests {

    private MockMvc mockMvc;

    @Mock
    private EmailService emailService;

    @Mock
    private ProjectService projectService;

    @Mock
    private InventoryService inventoryService;

    @Mock
    private WarehouseService warehouseService;

    @Mock
    private UserService userService;

    @InjectMocks
    private EmailController emailController;

    @Mock
    private AuthenthicationService authenthicationService;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(emailController).build();
    }

    @Test
    void sendProjectEmail_WithInProgressProjects() throws Exception {
        lenient().when(projectService.findProjectsByStatusAndDateBetween(
                        Project.Status.IN_PROGRESS, null, java.sql.Date.valueOf("1970-01-01"), java.sql.Date.valueOf(LocalDate.now().plusWeeks(1))))
                .thenReturn(Collections.singletonList(createSampleProject()));

        mockMvc.perform(post("/mail/sendProjectEmail"))
                .andExpect(status().isOk());
    }

    @Test
    void sendProjectEmail_NoInProgressProjects() throws Exception {
        lenient().when(projectService.findProjectsByStatusAndDateBetween(
                        Project.Status.IN_PROGRESS, null, java.sql.Date.valueOf("1970-01-01"), java.sql.Date.valueOf(LocalDate.now().plusWeeks(1))))
                .thenReturn(Collections.emptyList());

        mockMvc.perform(post("/mail/sendProjectEmail"))
                .andExpect(status().isOk())
                .andExpect(content().string("No projects are still in progress."));
    }

    @Test
    void sendProductEmail_WithProductsNeedingCare() throws Exception {
        DTO.InventoryProductDTO productNeedingCare = createSampleInventoryProductWithLowQuantity();
        when(inventoryService.getByWarehouseId(any()))
                .thenReturn(Collections.singletonList(productNeedingCare));
        lenient().when(inventoryService.getByWarehouseId(any())) // Using lenient() to ignore unnecessary stubbing warning
                .thenReturn(Collections.singletonList(productNeedingCare));

        mockMvc.perform(post("/mail/sendProductEmail"))
                .andExpect(status().isOk());
    }

    @Test
    void sendProductEmail_NoProductsNeedingCare() throws Exception {
        DTO.InventoryProductDTO productWithHighQuantity = createSampleInventoryProductWithHighQuantity();
        when(inventoryService.getByWarehouseId(any()))
                .thenReturn(Collections.singletonList(productWithHighQuantity));
        lenient().when(inventoryService.getByWarehouseId(any())) // Using lenient() to ignore unnecessary stubbing warning
                .thenReturn(Collections.singletonList(productWithHighQuantity));

        mockMvc.perform(post("/mail/sendProductEmail"))
                .andExpect(status().isOk())
                .andExpect(content().string("No products need care."));
    }

    // johnny
    @Test
    void sendPassResetEmail_Success() throws Exception {
        // Mock the necessary dependencies
        when(authenthicationService.generateResetPassToken(any())).thenReturn(createSampleUserDTO());

        // Set up the mockMvc
        mockMvc = MockMvcBuilders.standaloneSetup(emailController).build();

        // Create a sample user DTO to be used in the request body
        DTO.UserDTO sampleUserDTO = new DTO.UserDTO();
        sampleUserDTO.setEmail("test@example.com");

        // Perform the request and assert the response
        mockMvc.perform(post("/mail/sendPassResetEmail")
                        .content(asJsonString(sampleUserDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Password Reset Email sent."));
    }

    // johnny
    @Test
    void sendPassResetEmail_UserNotFound() throws Exception {
        // Mock the necessary dependencies
        when(authenthicationService.generateResetPassToken(any())).thenReturn(null);

        // Set up the mockMvc
        mockMvc = MockMvcBuilders.standaloneSetup(emailController).build();

        // Create a sample user DTO to be used in the request body
        DTO.UserDTO sampleUserDTO = new DTO.UserDTO();
        sampleUserDTO.setEmail("test@example.com");

        // Perform the request and assert the response
        mockMvc.perform(post("/mail/sendPassResetEmail")
                        .content(asJsonString(sampleUserDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Found user was null!"));
    }

    private DTO.UserDTO createSampleUserDTO() {
        DTO.UserDTO userDTO = new DTO.UserDTO();
        userDTO.setEmail("test@example.com");
        userDTO.setJwtToken("sampleJwtToken");
        // Set other properties as needed
        return userDTO;
    }

    // Helper method to convert an object to JSON string
    private static String asJsonString(Object obj) throws Exception {
        return new ObjectMapper().writeValueAsString(obj);
    }

    private Project createSampleProject() {
        Project project = new Project();
        project.setName("Energy Infrastructure");
        project.setStatus(Project.Status.IN_PROGRESS);
        project.setStartDate(java.sql.Date.valueOf("2000-01-01"));
        project.setEndDate(java.sql.Date.valueOf("2000-02-01"));
        return project;
    }

    private DTO.InventoryProductDTO createSampleInventoryProductWithLowQuantity() {
        // Creating a sample product with a low quantity (less than the minimum stock)
        return new DTO.InventoryProductDTO(123L, 4L, "Sample Product", "Warehouse A", 3.00, -1);
    }

    private DTO.InventoryProductDTO createSampleInventoryProductWithHighQuantity() {
        // Creating a sample product with a high quantity (greater than the minimum stock)
        return new DTO.InventoryProductDTO(123L, 4L, "Sample Product", "Warehouse A", 3.00, 99999999);
    }
}
