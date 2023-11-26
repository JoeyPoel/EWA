package teamx.app.backend.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import teamx.app.backend.models.Product;

import java.util.List;

/**
 * Interface of repository of product
 *
 * @author Junior Javier Brito Perez
 * @see Product
 */
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> getAllByTransactionsIsNotEmpty();
    List<Product> getAllByTransactions_Warehouse_Id(Long warehouseId);
    Page<Product> findAllBy(Pageable pageable);
    Page<Product> findAllByTransactionsIsNotEmpty(Pageable pageable);
}