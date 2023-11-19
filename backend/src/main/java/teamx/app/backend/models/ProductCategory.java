package teamx.app.backend.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

/**
 * ProductCategory entity
 * Represents a category of products
 *
 * @author Junior Javier Brito Perez
 * @see Product
 */
@Data
@Table
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class ProductCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;

    @OneToMany
    @JsonBackReference
    private List<WarehouseProductCategoryCapacity> warehouseProductCategoryCapacities;

    @OneToMany
    @JsonBackReference
    private List<Product> products;
}
