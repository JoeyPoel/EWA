package teamx.app.backend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import teamx.app.backend.utils.DTO.CapacityDTO;
import teamx.app.backend.utils.Model;

@Data
@Builder
@Entity(name = "WarehouseProductCategoryCapacities")
@NoArgsConstructor
@AllArgsConstructor
public class Capacity implements Model<CapacityDTO>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int capacity;
    private int minimumStockLevel;

    @ManyToOne
    @JsonIgnore
    private Warehouse warehouse;

    @ManyToOne
    @JsonIgnore
    private ProductCategory productCategory;

    @Override
    public CapacityDTO toDTO() {
        return CapacityDTO.builder()
                .id(id)
                .capacity(capacity)
                .minimumStockLevel(minimumStockLevel)
                .warehouseId(warehouse != null ? warehouse.getId() : null)
                .categoryId(productCategory != null ? productCategory.getId() : null)
                .categoryName(productCategory != null ? productCategory.getName() : null)
                .build();
    }
}
