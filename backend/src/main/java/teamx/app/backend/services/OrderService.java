package teamx.app.backend.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import teamx.app.backend.models.InventoryOrder;
import teamx.app.backend.repositories.OrderRepository;
import teamx.app.backend.repositories.TransactionRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final TransactionRepository transactionRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository, TransactionRepository transactionRepository) {
        this.orderRepository = orderRepository;
        this.transactionRepository = transactionRepository;
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

    private InventoryOrder findOrderByIdOrThrow(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Order not found with id: " + id));
    }

    @Transactional
    public InventoryOrder deleteById(Long id) {
        InventoryOrder order = findOrderByIdOrThrow(id);
        orderRepository.deleteById(id);
        return order;
    }
}
