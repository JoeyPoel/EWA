package teamx.app.backend.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import teamx.app.backend.models.Product;
import teamx.app.backend.models.dto.InventoryProductDTO;
import teamx.app.backend.repositories.ProductRepository;

import java.util.List;

@Slf4j
@Service
public class InventoryService {
    private final ProductRepository productRepository;
    private final TransactionService transactionService;

    @Autowired
    public InventoryService(ProductRepository productRepository, TransactionService transactionService) {
        this.productRepository = productRepository;
        this.transactionService = transactionService;
    }

    protected List<Product> getAll() {
        return productRepository.getAllByTransactionsIsNotEmpty();
    }

    public List<InventoryProductDTO> getAllDTO() {
        return getAll().stream().map(product -> convertToDTO(null, product)).toList();
    }

    protected List<Product> getByWarehouseId(Long warehouseId) {
        return productRepository.getAllByTransactions_Warehouse_Id(warehouseId);
    }

    public List<InventoryProductDTO> getByWarehouseIdDTO(Long warehouseId) {
        return getByWarehouseId(warehouseId)
                .stream().map(product -> convertToDTO(warehouseId, product)).toList();
    }

    protected InventoryProductDTO convertToDTO(Long warehouseId, Product product) {
        System.out.println("warehouseId: " + warehouseId);
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
