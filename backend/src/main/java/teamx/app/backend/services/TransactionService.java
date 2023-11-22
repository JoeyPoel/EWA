package teamx.app.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import teamx.app.backend.models.Product;
import teamx.app.backend.models.Transaction;
import teamx.app.backend.models.Warehouse;
import teamx.app.backend.repositories.TransactionRepository;

import java.sql.Date;
import java.util.List;

@Service
public class TransactionService {
    TransactionRepository transactionRepository;
    ProductService productService;
    WarehouseService warehouseService;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository, ProductService productService, WarehouseService warehouseService) {
        this.transactionRepository = transactionRepository;
        this.productService = productService;
        this.warehouseService = warehouseService;
    }

    public int getProductCurrentStock(Long warehouseId, Long productId) {
        Warehouse warehouse = warehouseId != null ? warehouseService.getWarehouseById(warehouseId) : null;

        int incomingStock = transactionRepository
                .getAllByWarehouseAndProductAndTransactionFlowAndTransactionDateBefore(
                        warehouse,
                        productService.getProductById(productId),
                        Transaction.Flow.IN,
                        new Date(System.currentTimeMillis())
                )
                .stream()
                .mapToInt(Transaction::getQuantity)
                .sum();


        int outgoingStock = transactionRepository
                .getAllByWarehouseAndProductAndTransactionFlowAndTransactionDateBefore(
                        warehouse,
                        productService.getProductById(productId),
                        Transaction.Flow.OUT,
                        new Date(System.currentTimeMillis())
                )
                .stream()
                .mapToInt(Transaction::getQuantity)
                .sum();

        return incomingStock - outgoingStock;
    }
}