package teamx.app.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import teamx.app.backend.models.WarehouseProductCategoryCapacity;

import java.util.List;

public interface WarehouseProductCategoryCapacityRepository extends
        JpaRepository<WarehouseProductCategoryCapacity, Long> {
    List<WarehouseProductCategoryCapacity> findAllByWarehouseId(Long warehouseId);
}
