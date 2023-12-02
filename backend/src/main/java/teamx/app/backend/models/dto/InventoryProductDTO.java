package teamx.app.backend.models.dto;

import lombok.Data;

@Data
public class InventoryProductDTO {
    private Long productId;
    private Long warehouseId;
    private String name;
    private String description;
    private Double price;
    private int quantity;
}
