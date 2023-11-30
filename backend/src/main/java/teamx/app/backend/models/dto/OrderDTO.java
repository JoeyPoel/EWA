package teamx.app.backend.models.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderDTO {
    Long id;
    List<OrderLineDTO> products;
    String orderDate;
    String deliveryDate;
    Long warehouseId;
    Long ProjectId;
    Long userId;
}
