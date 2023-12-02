package teamx.app.backend.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import teamx.app.backend.models.dto.InventoryProductDTO;
import teamx.app.backend.services.ExceptionService;
import teamx.app.backend.services.InventoryService;

import java.util.List;


/**
 * The InventoryController class is a REST controller that handles inventory-related operations.
 * It exposes endpoints for retrieving inventory products.
 *
 * @author Junior Javier Brito Perez
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/inventories")
@RequiredArgsConstructor
public class InventoryController {
    private final InventoryService inventoryService;
    private final ExceptionService exceptionService;

    @GetMapping
    public ResponseEntity<List<InventoryProductDTO>> getAll() {
        return exceptionService.handle(
                () -> inventoryService
                        .getAll()
                        .stream()
                        .map(product -> inventoryService.convertToDTO(null, product))
                        .toList()
        );
    }

    @GetMapping("/warehouse/{warehouseId}")
    public ResponseEntity<List<InventoryProductDTO>> getAllByWarehouseId(@PathVariable Long warehouseId) {
        return exceptionService.handle(
                () -> inventoryService
                        .getByWarehouseId(warehouseId)
                        .stream()
                        .map(product -> inventoryService.convertToDTO(warehouseId, product))
                        .toList()
        );
    }
}
