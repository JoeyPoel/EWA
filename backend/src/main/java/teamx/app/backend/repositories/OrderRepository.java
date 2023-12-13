package teamx.app.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import teamx.app.backend.models.InventoryOrder;

import java.util.List;

public interface OrderRepository extends JpaRepository<InventoryOrder, Long> {
    List<InventoryOrder> findAllByOrderedById(Long userId);

}
