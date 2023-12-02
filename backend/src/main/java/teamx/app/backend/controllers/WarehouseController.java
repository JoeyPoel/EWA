package teamx.app.backend.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import teamx.app.backend.models.Warehouse;
import teamx.app.backend.models.dto.CapacityDTO;
import teamx.app.backend.services.WarehouseService;

import java.util.List;

/**
 * Controller class for managing warehouses.
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/warehouses")
@RequiredArgsConstructor
public class WarehouseController {
    private final WarehouseService warehouseService;

    /**
     * Retrieves all warehouses.
     *
     * @return ResponseEntity contaning a list of Warehouse objects.
     */
    @GetMapping
    public ResponseEntity<List<Warehouse>> findAll() {
        return ResponseEntity.ok(warehouseService.findAll());
    }

    /**
     * Retrieves a warehouse by its ID.
     *
     * @param id The ID of the warehouse to retrieve.
     * @return ResponseEntity containing the Warehouse object with the specified ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Warehouse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(warehouseService.findById(id));
    }

    /**
     * Adds a new Warehouse to the system.
     *
     * @param warehouse The Warehouse object to be added.
     * @return ResponseEntity containing the created Warehouse object.
     */
    @PostMapping
    public ResponseEntity<Warehouse> add(@RequestBody Warehouse warehouse) {
        return ResponseEntity.status(HttpStatus.CREATED).body(warehouseService.add(warehouse));
    }

    /**
     * Updates a Warehouse in the system by its ID.
     *
     * @param id               The ID of the Warehouse to be updated.
     * @param newWarehouseData The updated Warehouse object.
     * @return ResponseEntity containing the updated Warehouse object.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Warehouse> updateById(@PathVariable Long id, @RequestBody Warehouse newWarehouseData) {
        return ResponseEntity.ok(warehouseService.updateById(id, newWarehouseData));
    }

    /**
     * Deletes a Warehouse from the system by its ID.
     *
     * @param id The ID of the Warehouse to be deleted.
     * @return ResponseEntity containing the deleted Warehouse object.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Warehouse> deleteById(@PathVariable Long id) {
        return ResponseEntity.ok(warehouseService.deleteById(id));
    }

    /**
     * Retrieves the capacity details for a Warehouse by its ID.
     *
     * @param id The ID of the Warehouse for which capacity details are to be retrieved.
     * @return ResponseEntity containing a list of CapacityDTO objects representing the capacity details of the
     * Warehouse.
     */
    @GetMapping("/{id}/capacity")
    public ResponseEntity<List<CapacityDTO>> getCapacity(@PathVariable Long id) {
        return ResponseEntity.ok(warehouseService.getCapacity(id));
    }

    /**
     * Adds the capacity details for a Warehouse by its ID.
     *
     * @param id                               The ID of the Warehouse to add the capacity details to.
     * @param warehouseProductCategoryCapacity The CapacityDTO object representing the capacity details to be added.
     * @return ResponseEntity containing the CapacityDTO object representing the added capacity details of the
     * Warehouse.
     */
    @PostMapping("/{id}/capacity")
    public ResponseEntity<CapacityDTO>
    addCapacityByWarehouseId(@PathVariable Long id, @RequestBody CapacityDTO warehouseProductCategoryCapacity) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(warehouseService.addCapacity(id, warehouseProductCategoryCapacity));
    }

    /**
     * Updates the capacity details for a Warehouse by its ID.
     *
     * @param id                               The ID of the Warehouse to update the capacity details of.
     * @param warehouseProductCategoryCapacity The CapacityDTO object representing the updated capacity details.
     * @return ResponseEntity containing the CapacityDTO object representing the updated capacity details of the
     * Warehouse.
     */
    @PutMapping("/{id}/capacity")
    public ResponseEntity<CapacityDTO>
    updateCapacityById(@PathVariable Long id, @RequestBody CapacityDTO warehouseProductCategoryCapacity) {
        return ResponseEntity.ok(warehouseService.updateCapacityById(id, warehouseProductCategoryCapacity));
    }
}
