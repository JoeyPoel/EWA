package teamx.app.backend.services;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import teamx.app.backend.models.Product;
import teamx.app.backend.models.Transaction;
import teamx.app.backend.models.Warehouse;
import teamx.app.backend.models.dto.TransactionDTO;
import teamx.app.backend.repositories.TransactionRepository;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

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


    public List<TransactionDTO> findAllByProduct(@NonNull Long productId) {
        List<TransactionDTO> transactions = transactionRepository
                .getAllByProduct(productService.findById(productId))
                .stream()
                .map(TransactionDTO::new)
                .toList();

        if (transactions.isEmpty()) {
            throw new NoSuchElementException("No transactions found for product with id " + productId);
        }

        return transactions;
    }

    public List<TransactionDTO> findAllByWarehouse(@NonNull Long warehouseId) {
        List<TransactionDTO> transactions = transactionRepository
                .getAllByWarehouse(warehouseService.findById(warehouseId))
                .stream()
                .map(TransactionDTO::new)
                .toList();

        if (transactions.isEmpty()) {
            throw new NoSuchElementException("No transactions found for warehouse with id " + warehouseId);
        }

        return transactions;
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

    public List<Integer> findProductStockHistoryByInterval(Long warehouseId, Long productId, Date startDate,
                                                           Date endDate) {
        Warehouse warehouse = null;
        if (warehouseId != null) {
            warehouse = warehouseService.findById(warehouseId);
        }

        Product product = productService.findById(productId);

        List<Transaction> productTransactions = warehouseId == null ?
                transactionRepository.getAllByProductAndTransactionDateBetween(product, startDate, endDate) :
                transactionRepository.getAllByWarehouseAndProductAndTransactionDateBetween(warehouse, product,
                        startDate, endDate);

        List<Date> transactionDates = productTransactions.stream()
                .map(Transaction::getTransactionDate)
                .toList();

        List<Integer> stockHistory = new ArrayList<>();
        int stockLevelAtStartDate = findProductStockAtDate(warehouseId, productId, startDate);
        stockHistory.add(stockLevelAtStartDate);

        int totalDays = (int) ((endDate.getTime() - startDate.getTime()) / (1000 * 60 * 60 * 24));

        for (int i = 0; i < totalDays; i++) {
            Date currentDate = new Date(startDate.getTime() + (i * 1000 * 60 * 60 * 24));
            if (transactionDates.contains(currentDate)) {
                int stockLevelAtDate = stockLevelAtStartDate + productTransactions.stream()
                        .filter(transaction -> transaction.getTransactionDate().equals(currentDate))
                        .mapToInt(transaction -> transaction.isPositiveTransaction() ?
                                transaction.getQuantity() :
                                -transaction.getQuantity())
                        .sum();
                stockHistory.add(stockLevelAtDate);
            }
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