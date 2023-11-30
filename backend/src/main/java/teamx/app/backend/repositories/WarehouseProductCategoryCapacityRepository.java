package teamx.app.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import teamx.app.backend.models.Capacity;

import java.util.List;

public interface WarehouseProductCategoryCapacityRepository extends
        JpaRepository<Capacity, Long> {
    List<Capacity> findAllByWarehouseId(Long warehouseId);
}
