package teamx.app.backend.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import teamx.app.backend.models.Warehouse;
import teamx.app.backend.repositories.WarehouseRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    private final WarehouseRepository warehouseRepository;

    @Autowired
    public WarehouseController(WarehouseRepository warehouseRepository) {
        this.warehouseRepository = warehouseRepository;
    }

    private static Warehouse updateWarehouseData(Warehouse newWarehouseData, Optional<Warehouse> originalWarehouseData) {
        Warehouse updatedWarehouseData = originalWarehouseData.get();
        updatedWarehouseData.setName(newWarehouseData.getName());
        updatedWarehouseData.setLocation(newWarehouseData.getLocation());
        updatedWarehouseData.setAddress(newWarehouseData.getAddress());
        updatedWarehouseData.setPostcode(newWarehouseData.getPostcode());
        updatedWarehouseData.setCountry(newWarehouseData.getCountry());
        updatedWarehouseData.setContactName(newWarehouseData.getContactName());
        updatedWarehouseData.setContactEmail(newWarehouseData.getContactEmail());
        updatedWarehouseData.setContactPhone(newWarehouseData.getContactPhone());
        return updatedWarehouseData;
    }

    @GetMapping("/test")
    public ResponseEntity<List<Warehouse>> getTestWarehouses() {
        return ResponseEntity.ok(warehouseRepository.findAll());
    }

    @GetMapping("/getAllWarehouses")
    public ResponseEntity<List<Warehouse>> getAllWarehouses() {
        try {
            List<Warehouse> warehouses = new ArrayList<>(warehouseRepository.findAll());
            if (warehouses.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No warehouses found");
            }
            return new ResponseEntity<>(warehouses, HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error retrieving warehouses");
        }
    }

    @GetMapping("/getWarehouseById/{id}")
    public ResponseEntity<Warehouse> getWarehouseById(@PathVariable Long id) {
        try {
            Optional<Warehouse> warehouse = warehouseRepository.findById(id);

            if (warehouse.isPresent()) {
                return new ResponseEntity<>(warehouse.get(), HttpStatus.OK);
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Warehouse not found");
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error retrieving warehouse");
        }
    }

    @PostMapping("/addWarehouse")
    public ResponseEntity<Warehouse> addWarehouse(@RequestBody Warehouse warehouse) {
        try {
            Warehouse savedWarehouse = warehouseRepository.save(warehouse);
            return new ResponseEntity<>(savedWarehouse, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error creating warehouse");
        }
    }

    @PutMapping("/updateWarehouseById/{id}")
    public ResponseEntity<Warehouse> updateWarehouseById(@PathVariable Long id, @RequestBody Warehouse newWarehouseData) {
        try {
            Optional<Warehouse> originalWarehouseData = warehouseRepository.findById(id);

            if (originalWarehouseData.isPresent()) {
                Warehouse updatedWarehouseData = updateWarehouseData(newWarehouseData, originalWarehouseData);

                Warehouse warehouse = warehouseRepository.save(updatedWarehouseData);
                return new ResponseEntity<>(warehouse, HttpStatus.OK);
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Warehouse not found");
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error updating warehouse");
        }
    }

    @DeleteMapping("/deleteWarehouseById/{id}")
    public ResponseEntity<Warehouse> deleteWarehouseById(@PathVariable Long id) {
        try {
            Optional<Warehouse> warehouse = warehouseRepository.findById(id);

            if (warehouse.isPresent()) {
                warehouseRepository.deleteById(id);
                return new ResponseEntity<>(warehouse.get(), HttpStatus.OK);
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Warehouse not found");
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error deleting warehouse");
        }
    }


}

