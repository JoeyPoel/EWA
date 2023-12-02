package teamx.app.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import teamx.app.backend.models.dto.InventoryProductDTO;
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
public class InventoryController {
    private final InventoryService inventoryService;

    @Autowired
    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    /**
     * Retrieves all inventory products as a list of InventoryProductDTO objects.
     *
     * @return a ResponseEntity containing a list of InventoryProductDTO objects
     *
     * @throws ResponseStatusException if an error occurs while retrieving the products
     */
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

    /**
     * Retrieves all inventory products for a given warehouse id as a list of InventoryProductDTO objects.
     *
     * @param warehouseId the id of the warehouse to retrieve products from
     * @return a ResponseEntity containing a list of InventoryProductDTO objects
     *
     * @throws ResponseStatusException if an error occurs while retrieving the products
     */
    @GetMapping("/warehouse/{warehouseId}")
    public ResponseEntity<List<InventoryProductDTO>> getAllByWarehouseId(@PathVariable Long warehouseId) {
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
