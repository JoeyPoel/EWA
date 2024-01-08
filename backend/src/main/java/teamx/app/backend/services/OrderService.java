package teamx.app.backend.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import teamx.app.backend.models.InventoryOrder;
import teamx.app.backend.repositories.OrderRepository;

import java.util.List;

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

    public List<InventoryOrder> findAll() {
        return orderRepository.findAll();
    }
    public List<InventoryOrder> findAllByWarehouseId(Long warehouseId) {
        return orderRepository.findAllByWarehouse_Id(warehouseId);
    }
}
