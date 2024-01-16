package teamx.app.backend.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import teamx.app.backend.models.Warehouse;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class WarehouseRepositoryTest {

    @Autowired
    private WarehouseRepository warehouseRepository;

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
    void testSaveWarehouse_ReturnsSavedWarehouse() {
        Warehouse warehouseToSave = new Warehouse();
        warehouseToSave.setName("New Warehouse Name");
        warehouseToSave.setLocation("New Warehouse Location");
        warehouseToSave.setAddress("New Warehouse Address");
        warehouseToSave.setPostcode("5678XK");
        warehouseToSave.setCountry("New Warehouse Country");

        Warehouse savedWarehouse = warehouseRepository.save(warehouseToSave);

        assertEquals(warehouseToSave, savedWarehouse);
        assertNotNull(savedWarehouse.getId());
    }

    @Test
    void testFindById_ExistingId_ReturnsWarehouse() {
        Optional<Warehouse> foundWarehouseOptional = warehouseRepository.findById(testWarehouse.getId());

        assertTrue(foundWarehouseOptional.isPresent());
        assertEquals(testWarehouse, foundWarehouseOptional.get());
    }

    @Test
    void testDeleteById_ExistingId_DeletesWarehouse() {
        warehouseRepository.deleteById(testWarehouse.getId());

        Optional<Warehouse> deletedWarehouse = warehouseRepository.findById(testWarehouse.getId());
        assertTrue(deletedWarehouse.isEmpty());
    }
}
