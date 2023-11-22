package teamx.app.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import teamx.app.backend.models.Transaction;
import teamx.app.backend.repositories.TransactionRepository;

import java.sql.Date;
import java.util.List;

@Service
public class TransactionService {
    TransactionRepository transactionRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
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
        List<Integer> projectsProductsQuantitiesInFlow = transactionRepository
                .findTransactionQuantitiesByWarehouseIdAndProductIdAndTransactionFlowAndTransactionDateBefore(
                warehouseId, productId, Transaction.Flow.IN, new Date(System.currentTimeMillis()));

        List<Integer> transferInflow = transactionRepository
                .findTransactionQuantitiesByWarehouseIdAndProductIdAndTransactionTypeAndTransactionDateBefore(
                warehouseId, productId, Transaction.Type.TRANSFER, new Date(System.currentTimeMillis()));

        System.out.println(transferInflow);
        List<Integer> projectsProductsQuantitiesOutFlow = transactionRepository
                .findTransactionQuantitiesByWarehouseIdAndProductIdAndTransactionFlowAndTransactionDateBefore(
                warehouseId, productId, Transaction.Flow.OUT, new Date(System.currentTimeMillis()));

        List<Integer> transferOutflowQuantity = transactionRepository
                .findTransactionQuantitiesByTransferFromIdAndWarehouseIdAndTransactionDateBefore(
                warehouseId, productId, new Date(System.currentTimeMillis()));


        Integer totalInflow = projectsProductsQuantitiesInFlow.stream().mapToInt(Integer::intValue).sum() +
                transferInflow.stream().mapToInt(Integer::intValue).sum();
        Integer totalOutflow = projectsProductsQuantitiesOutFlow.stream().mapToInt(Integer::intValue).sum() +
                transferOutflowQuantity.stream().mapToInt(Integer::intValue).sum();
        return totalInflow - totalOutflow;
    }

    public List<Integer> getTransactionQuantitiesByWarehouseIdAndByProductIdAndTransactionTypeAndDateBetween(
            Long warehouseId, Long productId, Transaction.Type transactionType, Date startDate, Date endDate) {
        return transactionRepository
                .findTransactionQuantitiesByWarehouseIdAndProductIdAndTransactionTypeAndTransactionDateBetween(
                warehouseId, productId, transactionType, startDate, endDate);
    }

    public Transaction getTransactionById(Long id) {
        return transactionRepository.findById(id).orElse(null);
    }

    public Transaction addTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }
}
