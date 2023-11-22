package teamx.app.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import teamx.app.backend.models.Product;
import teamx.app.backend.models.dto.InventoryProductDTO;
import teamx.app.backend.repositories.ProductRepository;
import teamx.app.backend.repositories.WarehouseRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class InventoryService {
    private final ProductRepository productRepository;
    private final WarehouseRepository warehouseRepository;
    private final TransactionService transactionService;

    @Autowired
    public InventoryService(ProductRepository productRepository, WarehouseRepository warehouseRepository,
                            TransactionService transactionService) {
        this.productRepository = productRepository;
        this.warehouseRepository = warehouseRepository;
        this.transactionService = transactionService;
    }

    public List<Product> getAllProductsHavingTransactions() {
        return productRepository.getAllByTransactionsIsNotEmpty();
    }

    public List<Product> getProductsByWarehouseId(Long warehouseId) {
        return productRepository.getAllByTransactions_Warehouse_Id(warehouseId);
    }

    public InventoryProductDTO convertToInventoryProductDTO(Long warehouseId, Product product) {
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

    public List<InventoryProductDTO> convertToInventoryProductDTO(Long warehouseId, List<Product> products) {
        List<InventoryProductDTO> inventoryProductDTOs = new ArrayList<>();
        for (Product product : products) {
            inventoryProductDTOs.add(convertToInventoryProductDTO(warehouseId, product));
        }
        return inventoryProductDTOs;
    }

    public Product convertToProduct(InventoryProductDTO inventoryProductDTO) {
        Product product = new Product();
        product.setId(inventoryProductDTO.getProductId());
        product.setName(inventoryProductDTO.getName());
        product.setDescription(inventoryProductDTO.getDescription());
        product.setPrice(inventoryProductDTO.getPrice());
        return product;
    }

    public List<Product> convertToProduct(List<InventoryProductDTO> inventoryProductDTOs) {
        List<Product> products = new ArrayList<>();
        for (InventoryProductDTO inventoryProductDTO : inventoryProductDTOs) {
            products.add(convertToProduct(inventoryProductDTO));
        }
        return products;
    }
}
