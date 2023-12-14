package teamx.app.backend.services;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import teamx.app.backend.models.Product;
import teamx.app.backend.models.Transaction;
import teamx.app.backend.models.Warehouse;
import teamx.app.backend.repositories.TransactionRepository;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * The TransactionService class is responsible for handling transaction-related operations.
 * It interacts with the TransactionRepository, ProductService, and WarehouseService classes.
 *
 * @author Junior Javier Brito Perez
 */
@Service
public class TransactionService {
    TransactionRepository transactionRepository;
    ProductService productService;
    WarehouseService warehouseService;

    @Autowired
    public TransactionService(
            TransactionRepository transactionRepository,
            ProductService productService,
            WarehouseService warehouseService) {
        this.transactionRepository = transactionRepository;
        this.productService = productService;
        this.warehouseService = warehouseService;
    }

    private int getStockLevel(List<Transaction> productTransactions) {
        return productTransactions.stream()
                .mapToInt(
                        transaction -> transaction.isPositiveTransaction() ?
                                transaction.getQuantity() :
                                -transaction.getQuantity()
                )
                .sum();
    }

    public List<Transaction> findAllByProduct(@NonNull Long productId) {
        return transactionRepository.getAllByProduct(productService.findById(productId));
    }

    public List<Transaction> findAllByWarehouse(@NonNull Long warehouseId) {
        return transactionRepository.getAllByWarehouse(warehouseService.findById(warehouseId));
    }

    public int findProductCurrentStock(Long warehouseId, Long productId) {
        Warehouse warehouse = null;
        if (warehouseId != null) {
            warehouse = warehouseService.findById(warehouseId);
        }

        Product product = productService.findById(productId);
        Date date = new Date(System.currentTimeMillis());

        List<Transaction> productTransactions = warehouseId == null ?
                transactionRepository.getAllByProductAndTransactionDateBefore(product, date) :
                transactionRepository.getAllByWarehouseAndProductAndTransactionDateBefore(warehouse, product, date);

        return getStockLevel(productTransactions);
    }

    public List<Integer> findProductStockHistoryByInterval(
            Long warehouseId, Long productId, Date startDate, Date endDate, long interval) {
        List<Integer> stockHistory = new ArrayList<>();
        for (Date date = startDate; date.before(endDate); date = new Date(date.getTime() + interval)) {
            stockHistory.add(findProductStockAtDate(warehouseId, productId, date));
        }
        return stockHistory;
    }

    private int findProductStockAtDate(Long warehouseId, Long productId, Date date) {
        Warehouse warehouse = null;
        if (warehouseId != null) {
            warehouse = warehouseService.findById(warehouseId);
        }

        Product product = productService.findById(productId);

        List<Transaction> productTransactions = warehouseId == null ?
                transactionRepository.getAllByProductAndTransactionDateBefore(product, date) :
                transactionRepository.getAllByWarehouseAndProductAndTransactionDateBefore(warehouse, product, date);

        return getStockLevel(productTransactions);
    }
}