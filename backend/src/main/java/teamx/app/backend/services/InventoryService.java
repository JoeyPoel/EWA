package teamx.app.backend.services;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import teamx.app.backend.models.Product;
import teamx.app.backend.models.dto.InventoryProductDTO;
import teamx.app.backend.repositories.ProductRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryService {
    private final ProductRepository productRepository;
    private final TransactionService transactionService;

    public List<Product> getAll() {
        List<Product> products = productRepository.findAll();
        throwExceptionIfProductsEmpty(products);
        return products;
    }

    public List<Product> getByWarehouseId(Long warehouseId) {
        List<Product> products = productRepository.getAllByTransactions_Warehouse_Id(warehouseId);
        throwExceptionIfProductsEmpty(products);
        return products;
    }

    private void throwExceptionIfProductsEmpty(List<Product> products) {
        if (products.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "No products found"
            );
        }
    }

    public InventoryProductDTO convertToDTO(Long warehouseId, Product product) {
        InventoryProductDTO inventoryProductDTO = new InventoryProductDTO();
        inventoryProductDTO.setProductId(product.getId());
        inventoryProductDTO.setWarehouseId(warehouseId);
        inventoryProductDTO.setName(product.getName());
        inventoryProductDTO.setDescription(product.getDescription());
        inventoryProductDTO.setPrice(product.getPrice());
        inventoryProductDTO.setQuantity(transactionService.getProductCurrentStock(warehouseId, product.getId()));

        return inventoryProductDTO;
    }
}
