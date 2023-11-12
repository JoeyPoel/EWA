package teamx.app.backend.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import teamx.app.backend.models.Warehouse;
import teamx.app.backend.repositories.WarehouseRepositoryMock;

/**
 * Warehouse controller.
 * This class is a REST controller for the Warehouse model.
 *
 * @author Junior Javier Brito Perez
 */
@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/warehouses")
public class WarehouseController implements Controller<Warehouse> {
    private final WarehouseRepositoryMock warehouseRepository;

    @Autowired
    public WarehouseController(WarehouseRepositoryMock warehouseRepository) {
        this.warehouseRepository = warehouseRepository;
    }

    @GetMapping("/test")
    public ResponseEntity<?> getTestWarehouses() {
        return ResponseEntity.ok(warehouseRepository.findAll());
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(warehouseRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable int id) {
        Warehouse warehouse = warehouseRepository.findById(id);
        if (warehouse == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Warehouse not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(warehouse);
    }

    @PostMapping()
    public ResponseEntity<?> add(Warehouse warehouse) {
        if (warehouseRepository.findById(warehouse.getId()) != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Warehouse already exists");
        }
        Warehouse savedWarehouse = warehouseRepository.save(warehouse);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedWarehouse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(int id, Warehouse warehouse) {
        if (id != warehouse.getId()) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Warehouse id mismatch");
        }
        if (warehouseRepository.findById(warehouse.getId()) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Warehouse not found");
        }
        Warehouse savedWarehouse = warehouseRepository.save(warehouse);
        return ResponseEntity.status(HttpStatus.OK).body(savedWarehouse);
    }

    @DeleteMapping( "/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        Warehouse warehouse = warehouseRepository.deleteById(id);
        if (warehouse == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Warehouse not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(warehouse);
    }
}

