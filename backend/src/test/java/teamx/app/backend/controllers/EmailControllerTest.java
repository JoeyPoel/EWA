package teamx.app.backend.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import teamx.app.backend.controllers.EmailController;
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
