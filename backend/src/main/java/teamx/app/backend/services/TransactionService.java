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
        List<Transaction> productTransactions = transactionRepository
                .getAllByWarehouseAndProductAndAndTransactionDateBefore(
                        warehouse,
                        productService.getProductById(productId),
                        new Date(System.currentTimeMillis())
                );
        return getCurrentStock(productTransactions);
    }

    private static int getCurrentStock(List<Transaction> productTransactions) {
        int currentStock = 0;
        for (Transaction transaction : productTransactions) {

            if (transaction.getTransactionType() == Transaction.Type.ORDER ||
                    (transaction.getTransactionType() == Transaction.Type.ADJUSTMENT ||
                            transaction.getTransactionType() == Transaction.Type.RETURN ||
                            transaction.getTransactionType() == Transaction.Type.OTHER) &&
                            transaction.getQuantity() > 0) {
                currentStock += transaction.getQuantity();
            } else {
                currentStock -= transaction.getQuantity();
            }
        }
        return currentStock;
    }
}