package teamx.app.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import teamx.app.backend.models.Warehouse;
import teamx.app.backend.models.dto.CapacityDTO;
import teamx.app.backend.repositories.WarehouseRepository;
import teamx.app.backend.services.WarehouseService;

import java.util.List;

/**
 * Warehouse controller.
 * This class is a REST controller for the Warehouse model.
 *
 * @author Junior Javier Brito Perez
 * @see Warehouse
 * @see WarehouseRepository
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

    @GetMapping
    public ResponseEntity<List<Warehouse>> getAllWarehouses() {
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

    @GetMapping("/{id}")
    public ResponseEntity<Warehouse> getWarehouseById(@PathVariable Long id) {
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

    @PostMapping
    public ResponseEntity<Warehouse> addWarehouse(@RequestBody Warehouse warehouse) {
        try {
            Warehouse newWarehouse = warehouseService.add(warehouse);
            return new ResponseEntity<>(newWarehouse, HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error adding warehouse");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Warehouse> updateWarehouseById(@PathVariable Long id, @RequestBody Warehouse newWarehouseData) {
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

    @DeleteMapping("/{id}")
    public ResponseEntity<Warehouse> deleteWarehouseById(@PathVariable Long id) {
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

    @GetMapping("/{id}/capacity")
    public ResponseEntity<List<CapacityDTO>> getWarehouseCapacityCategories(@PathVariable Long id) {
        try {
            List<CapacityDTO> warehouseCapacityCategories =
                    warehouseService.getCapacity(id);
            if (warehouseCapacityCategories == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No warehouse capacity categories found");
            }
            return new ResponseEntity<>(warehouseCapacityCategories, HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error retrieving warehouse" + e.getMessage());
        }
    }

    @PostMapping("/{id}/capacity")
    public ResponseEntity<CapacityDTO>
    addWarehouseCapacityByWarehouseId(@PathVariable Long id,
                                      @RequestBody CapacityDTO warehouseProductCategoryCapacity) {
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

    @PutMapping("/{id}/capacity")
    public ResponseEntity<CapacityDTO>
    updateWarehouseCapacityById(@PathVariable Long id,
                                @RequestBody CapacityDTO warehouseProductCategoryCapacity) {
        try {
            CapacityDTO updatedWarehouseProductCategoryCapacity =
                    warehouseService.updateCapacityById(id, warehouseProductCategoryCapacity);
            if (updatedWarehouseProductCategoryCapacity == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Warehouse capacity not found");
            }
            return new ResponseEntity<>(updatedWarehouseProductCategoryCapacity, HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error updating warehouse capacity" + e.getMessage());
        }
    }
}
