package teamx.app.backend.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import teamx.app.backend.models.*;
import teamx.app.backend.repositories.*;
import teamx.app.backend.utils.DTO;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final TransactionRepository transactionRepository;

    private final WarehouseRepository warehouseRepository;

    private final UserRepository userRepository;

    private final ProductRepository productRepository;
    private final ProjectRepository projectRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository, TransactionRepository transactionRepository,
                        WarehouseRepository warehouseRepository, UserRepository userRepository,
                        ProductRepository productRepository, ProjectRepository projectRepository) {
        this.orderRepository = orderRepository;
        this.transactionRepository = transactionRepository;
        this.warehouseRepository = warehouseRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.projectRepository = projectRepository;
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

    public InventoryOrder deleteById(Long id) {
        InventoryOrder order = findOrderByIdOrThrow(id);
        orderRepository.deleteById(id);
        return order;
    }

    @Transactional
    public InventoryOrder add(DTO.OrderDTO orderDTO) {
        InventoryOrder order = mapDTOToEntity(new InventoryOrder(), orderDTO);
        return orderRepository.save(order);
    }

    @Transactional
    public InventoryOrder update(Long id, DTO.OrderDTO orderDTO) {
        if (!id.equals(orderDTO.getId())) throw new IllegalArgumentException("Order ID does not match");
        return orderRepository.save(mapDTOToEntity(findOrderByIdOrThrow(id), orderDTO));
    }

    public InventoryOrder mapDTOToEntity(InventoryOrder order, DTO.OrderDTO orderDTO) {
        order.setWarehouse(getWarehouseById(orderDTO.getWarehouseId()));
        order.setOrderedBy(getUserById(orderDTO.getUserId()));
        order.setDescription(orderDTO.getDescription());
        order.setOrderDate(orderDTO.getOrderDate());
        order.setDeliveryDate(orderDTO.getDeliveryDate());
        order.setStatus(InventoryOrder.Status.valueOf(orderDTO.getStatus()));

        if (orderDTO.getProducts() != null && !orderDTO.getProducts().isEmpty()) {
            orderDTO.getProducts().forEach(orderLineDTO -> processOrderLineDTO(order, orderLineDTO));
        }
        return order;
    }

    private Warehouse getWarehouseById(Long id) {
        return id != null ?
                warehouseRepository.findById(id).orElseThrow() :
                null;
    }

    private User getUserById(Long id) {
        return id != null ?
                userRepository.findById(id).orElseThrow() :
                null;
    }

    private void processOrderLineDTO(InventoryOrder order, DTO.OrderLineDTO orderLineDTO) {
        if (orderLineDTO.getTransactionId() != null) {
            order.getTransactions().stream()
                    .filter(transaction -> transaction.getId().equals(orderLineDTO.getTransactionId()))
                    .findFirst()
                    .ifPresent(transaction -> {
                        transaction.setQuantity(orderLineDTO.getQuantity());
                        transactionRepository.save(transaction);
                    });
        } else {
            order.getTransactions().add(createNewTransactionFromOrderLineDTO(order, orderLineDTO));
        }
    }

    private Transaction createNewTransactionFromOrderLineDTO(InventoryOrder order, DTO.OrderLineDTO orderLineDTO) {
        return transactionRepository.save(
                Transaction.builder()
                        .quantity(orderLineDTO.getQuantity())
                        .product(getProductById(orderLineDTO.getProductId()))
                        .transactionType(Transaction.Type.INVENTORY_ORDER)
                        .inventoryOrder(order)
                        .transactionDate(order.getOrderDate())
                        .warehouse(order.getWarehouse())
                        .build()
        );
    }

    private Product getProductById(Long id) {
        return id != null ?
                productRepository.findById(id)
                        .orElseThrow() : null;
    }
}
