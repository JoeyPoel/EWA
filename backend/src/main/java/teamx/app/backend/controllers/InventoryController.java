package teamx.app.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import teamx.app.backend.models.dto.InventoryProductDTO;
import teamx.app.backend.services.InventoryService;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/inventories")
public class InventoryController {
    private final InventoryService inventoryService;

    @Autowired
    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @RequestMapping("/getAllProductsByHavingTransactions")
    public ResponseEntity<List<InventoryProductDTO>> getAllProductsByHavingTransactions() {
        try {
            List<InventoryProductDTO> inventoryProductDTOs = inventoryService
                    .convertToInventoryProductDTO(null,
                            inventoryService.getAllProductsHavingTransactions());

            if (inventoryProductDTOs == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No products found");
            }

            return ResponseEntity.ok(inventoryProductDTOs);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error while getting products: " + e.getMessage());
        }
    }

    @RequestMapping("/getProductsByWarehouseId/{warehouseId}")
    public ResponseEntity<List<InventoryProductDTO>> getWarehouseProducts(@PathVariable Long warehouseId) {
        try {
            List<InventoryProductDTO> inventoryProductDTOs = inventoryService
                    .convertToInventoryProductDTO(warehouseId, inventoryService.getProductsByWarehouseId(warehouseId));

            if (inventoryProductDTOs == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No products found in warehouse with id " + warehouseId);
            }

            return ResponseEntity.ok(inventoryProductDTOs);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error while getting products from warehouse with id " + warehouseId + ": " + e.getMessage());
        }
    }
}
