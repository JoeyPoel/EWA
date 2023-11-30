package teamx.app.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import teamx.app.backend.models.Warehouse;
import teamx.app.backend.models.dto.CapacityDTO;
import teamx.app.backend.services.WarehouseService;

import java.util.List;

/**
 * This class is a REST controller for the Warehouse model.
 *
 * @author Junior Javier Brito Perez
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/warehouses")
public class WarehouseController {
    private final WarehouseService warehouseService;

    @Autowired
    public WarehouseController(WarehouseService warehouseService) {
        this.warehouseService = warehouseService;
    }

    /**
     * Retrieves all warehouses.
     *
     * @return a ResponseEntity object containing a list of Warehouse objects if successful
     * @throws ResponseStatusException with HttpStatus.NOT_FOUND if no warehouses are found
     * @throws ResponseStatusException with HttpStatus.INTERNAL_SERVER_ERROR if an error occurs while
     *                                 retrieving warehouses
     */
    @GetMapping
    public ResponseEntity<List<Warehouse>> getAll() {
        try {
            List<Warehouse> warehouses = warehouseService.getAll();

            if (warehouses == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Warehouses not found");
            }

            return new ResponseEntity<>(warehouses, HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error retrieving warehouses");
        }
    }

    /**
     * Retrieves a warehouse by its ID.
     *
     * @param id the ID of the warehouse to retrieve
     * @return a ResponseEntity object containing the Warehouse object if successful
     * @throws ResponseStatusException with HttpStatus.NOT_FOUND if the warehouse with the given ID is not found
     * @throws ResponseStatusException with HttpStatus.INTERNAL_SERVER_ERROR if an error occurs while retrieving
     *                                 the warehouse
     */
    @GetMapping("/{id}")
    public ResponseEntity<Warehouse> getById(@PathVariable Long id) {
        try {
            Warehouse warehouse = warehouseService.getById(id);
            if (warehouse == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Warehouse not found");
            }
            return new ResponseEntity<>(warehouse, HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error retrieving warehouse");
        }
    }

    /**
     * Adds a new warehouse.
     *
     * @param warehouse the Warehouse object to add
     * @return a ResponseEntity object containing the newly added Warehouse object if successful
     * @throws ResponseStatusException with HttpStatus.INTERNAL_SERVER_ERROR if an error occurs while adding the
     * warehouse
     */
    @PostMapping
    public ResponseEntity<Warehouse> add(@RequestBody Warehouse warehouse) {
        try {
            Warehouse newWarehouse = warehouseService.add(warehouse);
            return new ResponseEntity<>(newWarehouse, HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error adding warehouse");
        }
    }

    /**
     * Updates a warehouse by its ID.
     *
     * @param id               the ID of the warehouse to update
     * @param newWarehouseData the Warehouse object containing the updated data
     * @return a ResponseEntity object containing the updated Warehouse object if successful
     * @throws ResponseStatusException with HttpStatus.NOT_FOUND if the warehouse is not found,
     *                                 or HttpStatus.INTERNAL_SERVER_ERROR if an error occurs while updating
     *                                 the warehouse
     */
    @PutMapping("/{id}")
    public ResponseEntity<Warehouse> updateById(@PathVariable Long id, @RequestBody Warehouse newWarehouseData) {
        try {
            Warehouse updatedWarehouse = warehouseService.updateById(id, newWarehouseData);
            if (updatedWarehouse == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Warehouse not found");
            }
            return new ResponseEntity<>(updatedWarehouse, HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error updating warehouse");
        }
    }

    /**
     * Deletes a warehouse by its ID.
     *
     * @param id the ID of the warehouse to delete
     * @return a ResponseEntity object containing the deleted Warehouse object if successful
     * @throws ResponseStatusException with HttpStatus.NOT_FOUND if the warehouse is not found,
     *                                 or HttpStatus.INTERNAL_SERVER_ERROR if an error occurs while deleting
     *                                 the warehouse
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Warehouse> deleteById(@PathVariable Long id) {
        try {
            Warehouse deletedWarehouse = warehouseService.deleteById(id);
            if (deletedWarehouse == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Warehouse not found");
            }
            return new ResponseEntity<>(deletedWarehouse, HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error deleting warehouse");
        }
    }

    /**
     * Retrieves the capacity of a warehouse by its ID.
     *
     * @param id the ID of the warehouse to retrieve capacity for
     * @return a ResponseEntity object containing a list of CapacityDTO objects if successful
     * @throws ResponseStatusException with HttpStatus.NOT_FOUND if no capacity categories are found for the warehouse,
     *                                 or HttpStatus.INTERNAL_SERVER_ERROR if an error occurs while retrieving the
     *                                 capacity of the warehouse
     */
    @GetMapping("/{id}/capacity")
    public ResponseEntity<List<CapacityDTO>> getCapacity(@PathVariable Long id) {
        try {
            List<CapacityDTO> warehouseCapacityCategories =
                    warehouseService.getCapacity(id);
            if (warehouseCapacityCategories == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No warehouse capacity categories found");
            }
            return new ResponseEntity<>(warehouseCapacityCategories, HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error retrieving warehouse"
                    + e.getMessage());
        }
    }

    /**
     * Adds capacity to a warehouse by its ID.
     *
     * @param id                               the ID of the warehouse to add capacity to
     * @param warehouseProductCategoryCapacity the CapacityDTO object containing the capacity details to be added
     * @return a ResponseEntity object containing the newly added CapacityDTO object if successful
     * @throws ResponseStatusException with HttpStatus.NOT_FOUND if the warehouse is not found,
     *                                 or HttpStatus.INTERNAL_SERVER_ERROR if an error occurs while adding the capacity
     */
    @PostMapping("/{id}/capacity")
    public ResponseEntity<CapacityDTO>
    addCapacityByWarehouseId(@PathVariable Long id, @RequestBody CapacityDTO warehouseProductCategoryCapacity) {
        try {
            CapacityDTO newWarehouseProductCategoryCapacity =
                    warehouseService.addCapacity(id, warehouseProductCategoryCapacity);
            if (newWarehouseProductCategoryCapacity == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Warehouse not found");
            }
            return new ResponseEntity<>(newWarehouseProductCategoryCapacity, HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error adding warehouse capacity");
        }
    }

    /**
     * Updates the capacity of a warehouse by its ID.
     *
     * @param id                               the ID of the warehouse to update the capacity for
     * @param warehouseProductCategoryCapacity the CapacityDTO object containing the updated capacity details
     * @return a ResponseEntity object containing the updated CapacityDTO object if successful
     * @throws ResponseStatusException with HttpStatus.NOT_FOUND if the warehouse capacity is not found,
     *                                 or HttpStatus.INTERNAL_SERVER_ERROR if an error occurs while updating the
     *                                 capacity
     */
    @PutMapping("/{id}/capacity")
    public ResponseEntity<CapacityDTO>
    updateCapacityById(@PathVariable Long id, @RequestBody CapacityDTO warehouseProductCategoryCapacity) {
        try {
            CapacityDTO updatedWarehouseProductCategoryCapacity =
                    warehouseService.updateCapacityById(id, warehouseProductCategoryCapacity);
            if (updatedWarehouseProductCategoryCapacity == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Warehouse capacity not found");
            }
            return new ResponseEntity<>(updatedWarehouseProductCategoryCapacity, HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error updating warehouse capacity"
                    + e.getMessage());
        }
    }
}
