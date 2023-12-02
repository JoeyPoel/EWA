package teamx.app.backend.models.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class InventoryProductDTO {
    private Long productId;
    private Long warehouseId;
    private String name;
    private String description;
    private Double price;
    private int quantity;
}
