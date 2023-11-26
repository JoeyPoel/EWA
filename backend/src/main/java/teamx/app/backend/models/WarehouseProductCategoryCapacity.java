package teamx.app.backend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity(name = "WarehouseProductCategoryCapacities")
@NoArgsConstructor
@AllArgsConstructor
public class WarehouseProductCategoryCapacity {
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
}
