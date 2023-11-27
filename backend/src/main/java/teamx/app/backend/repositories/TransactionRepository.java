package teamx.app.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import teamx.app.backend.models.Product;
import teamx.app.backend.models.Transaction;
import teamx.app.backend.models.Warehouse;

import java.sql.Date;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> getAllByWarehouseAndProductAndAndTransactionDateBefore(
            Warehouse warehouse, Product product, Date date);

    List<Transaction> getAllByProduct(Product product);
}
