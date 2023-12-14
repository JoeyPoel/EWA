package teamx.app.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import teamx.app.backend.models.Product;
import teamx.app.backend.repositories.ProductRepository;
import teamx.app.backend.utils.DTO.InventoryProductDTO;

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
    public InventoryService(ProductRepository productRepository, TransactionService transactionService,
                            WarehouseService warehouseService) {
        this.productRepository = productRepository;
        this.transactionService = transactionService;

        this.warehouseService = warehouseService;
    }

    public List<InventoryProductDTO> findAll() {
        List<Product> products = productRepository.findAll();
        if (products.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "No products found"
            );
        }
        return toDTO(null, products);
    }

    public List<InventoryProductDTO> getByWarehouseId(Long warehouseId) {
        return toDTO(warehouseId, productRepository.getAllByTransactions_Warehouse_Id(warehouseId));
    }

    private InventoryProductDTO toDTO(Long warehouseId, Product product) {
        return InventoryProductDTO.builder()
                .productId(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .quantity(transactionService.findProductCurrentStock(warehouseId, product.getId()))
                .warehouseId(warehouseId)
                .build();
    }

    private List<InventoryProductDTO> toDTO(Long warehouseId, List<Product> products) {
        return products
                .stream()
                .map(product -> toDTO(warehouseId, product))
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