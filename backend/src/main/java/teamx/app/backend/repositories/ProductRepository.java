package teamx.app.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import teamx.app.backend.models.Product;
import teamx.app.backend.models.dto.WarehouseStockDTO;

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
    List<WarehouseStockDTO> findStockLevelsByProductId(Long productId);
}