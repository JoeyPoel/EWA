package teamx.app.backend.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WarehouseProductDTO {
    private Long productId;
    private Long warehouseId;
    private String name;
    private String description;
    private Double price;
    private int quantity;
}
