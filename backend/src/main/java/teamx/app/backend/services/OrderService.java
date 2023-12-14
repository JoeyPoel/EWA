package teamx.app.backend.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import teamx.app.backend.repositories.OrderRepository;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    protected Long totalOrders() {
        return orderRepository.count();
    }

    public Long totalOrdersByWarehouse(Long warehouseId) {
        return orderRepository.countByWarehouseId(warehouseId);
    }
}
