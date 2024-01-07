package teamx.app.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import teamx.app.backend.utils.DTO.CapacityDTO;
import teamx.app.backend.models.Capacity;
import teamx.app.backend.models.ProductCategory;
import teamx.app.backend.models.Warehouse;
import teamx.app.backend.repositories.CapacityRepository;
import teamx.app.backend.repositories.ProductCategoryRepository;
import teamx.app.backend.repositories.WarehouseRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Service handling operations related to Warehouse entities.
 * Manages CRUD operations for Warehouse entities, including retrieval, addition, updating, and deletion.
 */
@Service
public class WarehouseService {
    private final WarehouseRepository warehouseRepository;
    private final CapacityRepository capacityRepository;
    private final ProductCategoryRepository productCategoryRepository;

    /**
     * Constructor for WarehouseService.
     *
     * @param warehouseRepository       WarehouseRepository instance.
     * @param capacityRepository        CapacityRepository instance.
     * @param productCategoryRepository ProductCategoryRepository instance.
     */
    @Autowired
    public WarehouseService(WarehouseRepository warehouseRepository, CapacityRepository capacityRepository,
                            ProductCategoryRepository productCategoryRepository) {
        this.warehouseRepository = warehouseRepository;
        this.capacityRepository = capacityRepository;
        this.productCategoryRepository = productCategoryRepository;
    }

    /**
     * Retrieves all warehouses.
     *
     * @return List of all Warehouse entities.
     */
    public List<Warehouse> findAll() {
//        TODO: Return empty list instead of throwing exception in production
        List<Warehouse> warehouses = warehouseRepository.findAll();
        if (warehouses.isEmpty()) {
            throw new NoSuchElementException("No warehouses found");
        }
        return warehouses;
    }

    /**
     * Retrieves IDs of all warehouses.
     *
     * @return List of Long containing IDs of all warehouses.
     */
    public List<Long> findAllIds() {
        return findAll()
                .stream()
                .map(Warehouse::getId)
                .toList();
    }

    /**
     * Finds a warehouse by its ID.
     *
     * @param id The ID of the warehouse to retrieve.
     * @return The found Warehouse entity.
     * @throws ResponseStatusException if the warehouse ID is null or if no warehouse is found with the given ID.
     */
    public Warehouse findById(Long id) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Warehouse id is null");
        }

        return warehouseRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Warehouse not found"));
    }

    /**
     * Adds a new warehouse.
     *
     * @param warehouse The Warehouse entity to be added.
     * @return The added Warehouse entity.
     */
    public Warehouse add(Warehouse warehouse) {
        return warehouseRepository.save(warehouse);
    }

    /**
     * Updates a warehouse by its ID.
     *
     * @param id        The ID of the warehouse to be updated.
     * @param warehouse The updated Warehouse entity details.
     * @return The updated Warehouse entity.
     * @throws ResponseStatusException if input validation fails or no warehouse is found with the given ID.
     */
    public Warehouse updateById(Long id, Warehouse warehouse) {
        validateInputs(id, warehouse);
        Warehouse existingWarehouse = findExistingWarehouse(id);

        updateProperties(existingWarehouse, warehouse);

        return warehouseRepository.save(existingWarehouse);
    }

    /**
     * Deletes a warehouse by its ID.
     *
     * @param warehouseId The ID of the warehouse to be deleted.
     * @return The deleted Warehouse entity.
     * @throws NullPointerException if no warehouse is found with the given ID.
     */
    public Warehouse deleteById(Long warehouseId) {
        Warehouse existingWarehouse = warehouseRepository.findById(warehouseId).orElse(null);

        if (existingWarehouse == null) {
            throw new NullPointerException("Warehouse not found");
        }

        warehouseRepository.deleteById(warehouseId);
        return existingWarehouse;
    }

    /**
     * Validates input parameters.
     *
     * @param id        The ID of the warehouse.
     * @param warehouse The Warehouse entity.
     * @throws NullPointerException      if warehouse is null or if warehouse ID is invalid.
     * @throws IllegalArgumentException  if warehouse ID is invalid or doesn't match the provided ID.
     * @throws IllegalStateException     if warehouse ID doesn't match the provided ID.
     * @throws ResponseStatusException   if warehouse ID is null.
     */
    private void validateInputs(Long id, Warehouse warehouse) {
        if (warehouse == null) {
            throw new NullPointerException("Warehouse is null");
        }

        if (id <= 0 || warehouse.getId() <= 0) {
            throw new IllegalArgumentException("Invalid warehouse id");
        }

        if (!Objects.equals(warehouse.getId(), id)) {
            throw new IllegalStateException("Warehouse id does not match");
        }
    }

    /**
     * Finds an existing warehouse by its ID.
     *
     * @param id The ID of the warehouse.
     * @return The existing Warehouse entity.
     * @throws NoSuchElementException if no warehouse is found with the given ID.
     */
    private Warehouse findExistingWarehouse(Long id) {
        return warehouseRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Warehouse not found"));
    }

    /**
     * Updates properties of an existing warehouse.
     *
     * @param existingWarehouse The existing Warehouse entity.
     * @param warehouse         The Warehouse entity with updated properties.
     */
    private void updateProperties(Warehouse existingWarehouse, Warehouse warehouse) {
        existingWarehouse.setName(warehouse.getName());
        existingWarehouse.setLocation(warehouse.getLocation());
        existingWarehouse.setAddress(warehouse.getAddress());
        existingWarehouse.setPostcode(warehouse.getPostcode());
        existingWarehouse.setCountry(warehouse.getCountry());
        existingWarehouse.setContactName(warehouse.getContactName());
        existingWarehouse.setContactEmail(warehouse.getContactEmail());
        existingWarehouse.setContactPhone(warehouse.getContactPhone());
    }
}
