package teamx.app.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import teamx.app.backend.models.Capacity;

import java.util.List;

/**
 * Repository interface for managing operations related to Capacity entities.
 * Extends JpaRepository to leverage Spring Data JPA capabilities.
 * Provides methods for accessing and manipulating Capacity entity data in the database.
 *
 * @author Joey van der Poel
 * @author Junior Javier Brito Perez
 */
public interface CapacityRepository extends JpaRepository<Capacity, Long> {

    /**
     * Retrieves a list of capacities based on a specified warehouse ID.
     *
     * @param warehouseId The ID of the warehouse to retrieve capacities for.
     * @return List of Capacity entities associated with the given warehouse ID.
     */
    List<Capacity> findAllByWarehouseId(Long warehouseId);
}
