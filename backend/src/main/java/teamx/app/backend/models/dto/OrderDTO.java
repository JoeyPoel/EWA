package teamx.app.backend.models.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class OrderDTO {
    private Long id;
    private List<OrderLineDTO> products;
    private String orderDate;
    private String deliveryDate;
    private Long warehouseId;
    private Long ProjectId;
    private Long userId;
}
