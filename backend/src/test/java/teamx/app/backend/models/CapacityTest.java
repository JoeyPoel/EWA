package teamx.app.backend.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import teamx.app.backend.utils.DTO.CapacityDTO;

import static org.junit.jupiter.api.Assertions.*;

class CapacityTest {

    private Capacity capacity;
    private Warehouse warehouse;
    private ProductCategory productCategory;

    @BeforeEach
    void setUp() {
        warehouse = Warehouse.builder()
                .id(1L)
                .name("Warehouse 1")
                .build();

        productCategory = ProductCategory.builder()
                .id(1L)
                .name("Category 1")
                .build();

        capacity = Capacity.builder()
                .id(1L)
                .capacity(100)
                .minimumStockLevel(10)
                .warehouse(warehouse)
                .productCategory(productCategory)
                .build();
    }

    @Test
    void testCapacityGettersAndSetters() {
        assertEquals(1L, capacity.getId());
        assertEquals(100, capacity.getCapacity());
        assertEquals(10, capacity.getMinimumStockLevel());
        assertEquals(warehouse, capacity.getWarehouse());
        assertEquals(productCategory, capacity.getProductCategory());
    }

    @Test
    void testToDTO() {
        CapacityDTO dto = capacity.toDTO();

        assertNotNull(dto);
        assertEquals(capacity.getId(), dto.getId());
        assertEquals(capacity.getCapacity(), dto.getCapacity());
        assertEquals(capacity.getMinimumStockLevel(), dto.getMinimumStockLevel());
        assertEquals(capacity.getWarehouse().getId(), dto.getWarehouseId());
        assertEquals(capacity.getProductCategory().getId(), dto.getCategoryId());
        assertEquals(capacity.getProductCategory().getName(), dto.getCategoryName());
    }
}
