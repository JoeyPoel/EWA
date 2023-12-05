package teamx.app.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import teamx.app.backend.models.Product;
import teamx.app.backend.models.dto.InventoryProductDTO;
import teamx.app.backend.repositories.ProductRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class represents an InventoryService that provides operations related to managing inventory products.
 *
 * @author Junior Javier Brito Perez
 */
@Service
public class InventoryService {
    private final ProductRepository productRepository;
    private final TransactionService transactionService;
    private final WarehouseService warehouseService;

    @Autowired
    public InventoryService(ProductRepository productRepository, TransactionService transactionService) {
        this.productRepository = productRepository;
        this.transactionService = transactionService;

    }

    public List<InventoryProductDTO> getAll() {
        List<Product> products = productRepository.findAll();
        if (products.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "No products found"
            );
        }
        return mapToDTO(null, products);
    }

    public List<InventoryProductDTO> getByWarehouseId(Long warehouseId) {
        return mapToDTO(warehouseId, productRepository.getAllByTransactions_Warehouse_Id(warehouseId));
    }

    private InventoryProductDTO mapToDTO(Long warehouseId, Product product) {
        InventoryProductDTO inventoryProductDTO = new InventoryProductDTO();
        inventoryProductDTO.setProductId(product.getId());
        inventoryProductDTO.setName(product.getName());
        inventoryProductDTO.setDescription(product.getDescription());
        inventoryProductDTO.setPrice(product.getPrice());
        inventoryProductDTO.setQuantity(transactionService.findProductCurrentStock(warehouseId, product.getId()));
        inventoryProductDTO.setWarehouseId(warehouseId);
        return inventoryProductDTO;
    }

    private List<InventoryProductDTO> mapToDTO(Long warehouseId, List<Product> products) {
        return products
                .stream()
                .map(product -> mapToDTO(warehouseId, product))
                .toList();
    }

    public Map<String, Integer> getAllStockLevelsByProduct(Long productId) {
        Map<String, Integer> stockLevels = new HashMap<>();
        warehouseService.findAll().stream()
                .map(warehouse -> {
                    int stockLevel = transactionService.findProductCurrentStock(warehouse.getId(), productId);
                    return Map.entry(warehouse.getName(), stockLevel);
                })
                .forEach(entry -> stockLevels.put(entry.getKey(), entry.getValue()));
        return stockLevels;
    }
}