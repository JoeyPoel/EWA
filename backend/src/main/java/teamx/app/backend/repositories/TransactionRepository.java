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
}
