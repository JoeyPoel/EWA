package teamx.app.backend.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    Long id;
    List<OrderLineDTO> products;
    String orderDate;
    String deliveryDate;
    Long warehouseId;
    Long ProjectId;
    Long userId;
}
