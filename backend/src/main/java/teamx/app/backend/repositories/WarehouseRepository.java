package teamx.app.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import teamx.app.backend.models.Warehouse;

/**
 * Interface of repository of warehouse
 *
 * @author Junior Javier Brito Perez
 * @see Warehouse
 */
@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {
}
