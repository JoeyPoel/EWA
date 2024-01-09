package teamx.app.backend.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import teamx.app.backend.models.InventoryOrder;
import teamx.app.backend.services.OrderService;
import teamx.app.backend.utils.DTO.OrderDTO;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity<List<OrderDTO>> findAll() {
        return ResponseEntity.ok(orderService.findAll().stream().map(InventoryOrder::toDTO).toList());
    }

    @GetMapping("/warehouse/{warehouseId}")
    public ResponseEntity<List<OrderDTO>> findAllByWarehouseId(@PathVariable Long warehouseId) {
        return ResponseEntity.ok(
                orderService.findAllByWarehouseId(warehouseId).stream().map(InventoryOrder::toDTO).toList()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<OrderDTO> deleteById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.deleteById(id).toDTO());
    }

//       public InventoryOrder add(InventoryOrder order) {
//        return orderRepository.save(order);
//    }
//
//    public InventoryOrder update(Long id, InventoryOrder order) {
//        if (!id.equals(order.getId())) throw new IllegalArgumentException("Order ID does not match");
//        return orderRepository.save(order);
//    }
    @PostMapping
    public ResponseEntity<OrderDTO> add(@RequestBody OrderDTO orderDTO) {
        return ResponseEntity.ok(orderService.add(orderDTO).toDTO());
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderDTO> update(@PathVariable Long id, @RequestBody OrderDTO orderDTO) {
        return ResponseEntity.ok(orderService.update(id, orderDTO).toDTO());
    }
}
