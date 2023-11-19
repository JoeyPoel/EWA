package teamx.app.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import teamx.app.backend.models.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
