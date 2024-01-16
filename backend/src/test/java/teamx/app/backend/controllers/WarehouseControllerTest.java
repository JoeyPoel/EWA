package teamx.app.backend.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import teamx.app.backend.models.Warehouse;
import teamx.app.backend.services.WarehouseService;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(WarehouseController.class)
@AutoConfigureMockMvc
class WarehouseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WarehouseService warehouseService;

    private Warehouse warehouse1;
    private Warehouse warehouse2;

    @BeforeEach
    public void setUp() {
        warehouse1 = new Warehouse();
        warehouse1.setId(1L);
        warehouse1.setName("Warehouse 1");

        warehouse2 = new Warehouse();
        warehouse2.setId(2L);
        warehouse2.setName("Warehouse 2");
    }

    @Test
    public void testFindAll() throws Exception {
        List<Warehouse> warehouses = Arrays.asList(warehouse1, warehouse2);
        when(warehouseService.findAll()).thenReturn(warehouses);
        mockMvc.perform(get("/warehouses"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("Warehouse 1"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].name").value("Warehouse 2"));
    }

    @Test
    public void testFindById() throws Exception {
        when(warehouseService.findById(1L)).thenReturn(warehouse1);
        mockMvc.perform(get("/warehouses/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Warehouse 1"));
    }

    @Test
    public void testAdd() throws Exception {
        when(warehouseService.add(warehouse1)).thenReturn(warehouse1);
        mockMvc.perform(post("/warehouses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1,\"name\":\"Warehouse 1\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Warehouse 1"));
    }

    @Test
    public void testUpdateById() throws Exception {
        when(warehouseService.updateById(1L, warehouse1)).thenReturn(warehouse1);
        mockMvc.perform(put("/warehouses/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1,\"name\":\"Warehouse 1\"}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Warehouse 1"));
    }

    @Test
    public void testDeleteById() throws Exception {
        when(warehouseService.deleteById(1L)).thenReturn(warehouse1);
        mockMvc.perform(delete("/warehouses/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Warehouse 1"));
    }

    @Test
    public void testFindById_NotFound() throws Exception {
        when(warehouseService.findById(100L)).thenThrow(new NoSuchElementException("No warehouse found with id: 100"));
        mockMvc.perform(get("/warehouses/100"))
                .andExpect(status().isNotFound());
    }

}
