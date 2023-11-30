package teamx.app.backend.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import teamx.app.backend.models.dto.InventoryProductDTO;
import teamx.app.backend.services.InventoryService;

import java.util.List;

@Slf4j
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/inventories")
public class InventoryController {
    private final InventoryService inventoryService;

    @Autowired
    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping
    public ResponseEntity<List<InventoryProductDTO>> getAll() {
        try {
            List<InventoryProductDTO> inventoryProductDTOs = inventoryService.getAllDTO();

            if (inventoryProductDTOs.isEmpty()) {
                return new ResponseEntity<>(inventoryProductDTOs, HttpStatus.NO_CONTENT);
            }

            return ResponseEntity.ok(inventoryProductDTOs);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error while getting products: " + e.getMessage());
        }
    }

    @GetMapping("/warehouse/{warehouseId}")
    public ResponseEntity<List<InventoryProductDTO>> getWarehouseProducts(@PathVariable Long warehouseId) {
        try {
            List<InventoryProductDTO> inventoryProductDTOs = inventoryService.getByWarehouseIdDTO(warehouseId);

            if (inventoryProductDTOs.isEmpty()) {
                return new ResponseEntity<>(inventoryProductDTOs, HttpStatus.NO_CONTENT);
            }

            return ResponseEntity.ok(inventoryProductDTOs);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error while getting products from warehouse with id " + warehouseId + ": " + e.getMessage());
        }
    }
}
