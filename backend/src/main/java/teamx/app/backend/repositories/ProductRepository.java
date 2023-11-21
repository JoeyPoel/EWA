package teamx.app.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import teamx.app.backend.models.Product;

/**
 * Interface of repository of product
 *
 * @author Junior Javier Brito Perez
 * @see Product
 */
public interface ProductRepository extends JpaRepository<Product, Long> {
}