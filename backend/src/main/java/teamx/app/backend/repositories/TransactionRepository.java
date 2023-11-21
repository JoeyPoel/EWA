package teamx.app.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import teamx.app.backend.models.Transaction;

import java.sql.Date;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> getTransactionsByProjectId(Long projectId);
    List<Transaction> getTransactionsByWarehouseId(Long warehouseId);
    List<Transaction> getTransactionsByProductId(Long productId);
    List<Transaction> getTransactionsByProductIdAndWarehouseIdAndTransactionDateBefore(Long productId, Long warehouseId
    , Date transactionDateBefore);


    List<Integer> findTransactionQuantitiesByWarehouseIdAndProductIdAndTransactionFlowAndTransactionDateBefore(
            Long warehouseId, Long productId, Transaction.Flow flow, Date transactionDateBefore);
    
    List<Integer> findTransactionQuantitiesByWarehouseIdAndProductIdAndTransactionTypeAndTransactionDateBefore(
            Long warehouseId, Long productId, Transaction.Type transactionType, Date transactionDateBefore);
    
    List<Integer> findTransactionQuantitiesByTransferFromIdAndWarehouseIdAndTransactionDateBefore(
            Long transferFromId, Long warehouseId, Date transactionDateBefore);

    List<Integer> findTransactionQuantitiesByWarehouseIdAndProductIdAndTransactionTypeAndTransactionDateBetween(
            Long warehouseId, Long productId, Transaction.Type transactionType, Date startDate, Date endDate);

    List<Transaction> findByProjectId(Long id);

    List<Transaction> findByProductId(Long id);

    List<Transaction> findByOrderId(Long id);
}
