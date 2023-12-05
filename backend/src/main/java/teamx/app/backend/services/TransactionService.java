package teamx.app.backend.services;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import teamx.app.backend.models.Product;
import teamx.app.backend.models.ProductCategory;
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
                                                           Date endDate, long interval) {
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
        for (Date date = startDate; date.before(endDate); date = new Date(date.getTime() + interval)) {
            stockHistory.add(findProductStockAtDate(warehouseId, productId, date));
        }
        return stockHistory;
    }

    protected Date getOldestTransactionDate() {
        return transactionRepository.findAll()
                .stream()
                .map(Transaction::getTransactionDate)
                .min(Date::compareTo)
                .orElseThrow(null);
    }

    protected Date getOldestTransactionDateByWarehouse(Long warehouseId) {
        return transactionRepository.getAllByWarehouse(warehouseService.findById(warehouseId))
                .stream()
                .map(Transaction::getTransactionDate)
                .min(Date::compareTo)
                .orElseThrow(null);
    }

    protected Date getOldestTransactionDateByProduct(Long productId) {
        return transactionRepository.getAllByProduct(productService.findById(productId))
                .stream()
                .map(Transaction::getTransactionDate)
                .min(Date::compareTo)
                .orElseThrow(null);
    }

    protected Date getLatestTransactionDate() {
        return transactionRepository.findAll()
                .stream()
                .map(Transaction::getTransactionDate)
                .max(Date::compareTo)
                .orElseThrow(null);
    }

    protected Date getLatestTransactionDateByWarehouse(Long warehouseId) {
        return transactionRepository.getAllByWarehouse(warehouseService.findById(warehouseId))
                .stream()
                .map(Transaction::getTransactionDate)
                .max(Date::compareTo)
                .orElseThrow(null);
    }

    protected Date getLatestTransactionDateByProduct(Long productId) {
        return transactionRepository.getAllByProduct(productService.findById(productId))
                .stream()
                .map(Transaction::getTransactionDate)
                .max(Date::compareTo)
                .orElseThrow(null);
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