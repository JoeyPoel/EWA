package teamx.app.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import teamx.app.backend.models.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
