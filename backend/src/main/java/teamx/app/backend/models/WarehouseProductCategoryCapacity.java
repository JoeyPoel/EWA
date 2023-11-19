package teamx.app.backend.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "WarehouseProductCategoryCapacity")
@NoArgsConstructor
@AllArgsConstructor
public class WarehouseProductCategoryCapacity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int capacity;

    @ManyToOne
    @JoinColumn
    @JsonManagedReference
    private Warehouse warehouse;

    @ManyToOne
    @JoinColumn
    @JsonManagedReference
    private ProductCategory productCategory;
}
