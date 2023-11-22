package teamx.app.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import teamx.app.backend.models.Product;
import teamx.app.backend.models.Transaction;
import teamx.app.backend.models.dto.WarehouseProductDTO;
import teamx.app.backend.repositories.ProductRepository;
import teamx.app.backend.repositories.TransactionRepository;

import java.sql.Date;
import java.util.List;

@Service
public class TransactionService {
    TransactionRepository transactionRepository;
    ProductRepository productRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository, ProductRepository productRepository) {
        this.transactionRepository = transactionRepository;
        this.productRepository = productRepository;
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public List<Transaction> getTransactionsByOrderId(Long id) {
        return transactionRepository.findByOrderId(id);
    }

    public List<Transaction> getTransactionsByProductId(Long id) {
        return transactionRepository.findByProductId(id);
    }

    public List<Transaction> getTransactionsByProjectId(Long id) {
        return transactionRepository.findByProjectId(id);
    }

    public List<Transaction> getTransactionsByWarehouseId(Long id) {
        return transactionRepository.getTransactionsByWarehouseId(id);
    }

    public Integer getCurrentStock(Long warehouseId, Long productId) {
        List<Integer> projectsProductsQuantitiesInFlow = transactionRepository.getAllByWarehouseIdAndTransactionFlowAndTransactionDateBefore(warehouseId, Transaction.Flow.IN, new Date(System.currentTimeMillis()));
        List<Integer> projectsProductsQuantitiesOutFlow = transactionRepository.getAllByWarehouseIdAndTransactionFlowAndTransactionDateBefore(warehouseId, Transaction.Flow.OUT, new Date(System.currentTimeMillis()));
        Integer currentStock = 0;
        for (Integer quantity : projectsProductsQuantitiesInFlow) {
            currentStock += quantity;
        }
        for (Integer quantity : projectsProductsQuantitiesOutFlow) {
            currentStock -= quantity;
        }
        return currentStock;
    }

//    public List<WarehouseProductDTO> getWarehouseInventory(Long warehouseId) {
//        List<Product> productList = productRepository.findAll();
//
//        List<WarehouseProductDTO> warehouseProductDTOs = transactionRepository.getWarehouseInventory(warehouseId);
//        return warehouseProductDTOs;
//    }

    public Transaction getTransactionById(Long id) {
        return transactionRepository.findById(id).orElse(null);
    }

    public Transaction addTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }
}
