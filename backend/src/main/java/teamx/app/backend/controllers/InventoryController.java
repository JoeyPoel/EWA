package teamx.app.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import teamx.app.backend.utils.DTO.InventoryProductDTO;
import teamx.app.backend.services.InventoryService;


import java.util.List;
import java.util.Map;

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
     * Retrieves all inventory products.
     *
     * @return ResponseEntity with a List of InventoryProductDTO objects representing all inventory products.
     */
    @GetMapping
    public ResponseEntity<List<InventoryProductDTO>> getAll() {
        return ResponseEntity.ok(inventoryService.getAll());
    }

    /**
     * Retrieves all inventory products by warehouse ID.
     *
     * @param warehouseId The ID of the warehouse to retrieve inventory products from.
     * @return ResponseEntity with a List of InventoryProductDTO objects representing all inventory products in the
     * specified warehouse.
     */
    @GetMapping("/warehouse/{warehouseId}")
    public ResponseEntity<List<InventoryProductDTO>> getAllByWarehouseId(@PathVariable Long warehouseId) {
        return ResponseEntity.ok(inventoryService.getByWarehouseId(warehouseId));
    }

    @GetMapping("/product/{productId}/warehouses")
    public ResponseEntity<Map<String, Integer>> findAllStockLevelsByProduct(@PathVariable Long productId) {
        return ResponseEntity.ok(inventoryService.getAllStockLevelsByProduct(productId));
    }

}
