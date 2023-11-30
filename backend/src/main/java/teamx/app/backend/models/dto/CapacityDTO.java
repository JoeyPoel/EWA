package teamx.app.backend.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CapacityDTO {
    private Long id;
    private Long warehouseId;
    private String categoryName;
    private Long categoryId;
    private int capacity;
    private int minimumStockLevel;
}
