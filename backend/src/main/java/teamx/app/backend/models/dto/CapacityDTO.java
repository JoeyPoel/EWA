package teamx.app.backend.models.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import teamx.app.backend.models.Capacity;

@Data
@NoArgsConstructor
public class CapacityDTO {
    private Long id;
    private Long warehouseId;
    private String categoryName;
    private Long categoryId;
    private int capacity;
    private int minimumStockLevel;

    public CapacityDTO(Capacity capacity) {
        this.id = capacity.getId();
        this.warehouseId = capacity.getWarehouse() != null ? capacity.getWarehouse().getId() : null;
        this.categoryName = capacity.getProductCategory() != null ? capacity.getProductCategory().getName() : null;
        this.categoryId = capacity.getProductCategory() != null ? capacity.getProductCategory().getId() : null;
        this.capacity = capacity.getCapacity();
        this.minimumStockLevel = capacity.getMinimumStockLevel();
    }
}
