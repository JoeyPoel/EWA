package teamx.app.backend.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WarehouseTest {

    private Warehouse warehouse;

    @BeforeEach
    void setUp() {
        warehouse = Warehouse.builder()
                .id(1L)
                .name("Warehouse 1")
                .location("Location 1")
                .address("1234 Street")
                .postcode("1234AB")
                .country("Country 1")
                .contactName("John Doe")
                .contactEmail("john@example.com")
                .contactPhone("1234567890")
                .build();
    }

    @Test
    void testWarehouseGettersAndSetters() {
        assertEquals(1L, warehouse.getId());
        assertEquals("Warehouse 1", warehouse.getName());
        assertEquals("Location 1", warehouse.getLocation());
        assertEquals("1234 Street", warehouse.getAddress());
        assertEquals("1234AB", warehouse.getPostcode());
        assertEquals("Country 1", warehouse.getCountry());
        assertEquals("John Doe", warehouse.getContactName());
        assertEquals("john@example.com", warehouse.getContactEmail());
        assertEquals("1234567890", warehouse.getContactPhone());
    }

    @Test
    void testWarehousePostcodePattern() {
        warehouse.setPostcode("wrong");
        assertNotEquals("1234AB", warehouse.getPostcode(), "Postcode should not match the pattern");
    }
}
