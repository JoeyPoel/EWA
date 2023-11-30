package teamx.app.backend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * ProductCategory entity
 * Represents a category of products
 *
 * @author Junior Javier Brito Perez
 * @see Product
 */
@Data
@Entity(name = "ProductCategories")
@NoArgsConstructor
@AllArgsConstructor
public class ProductCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;

    @OneToMany(mappedBy = "productCategory")
    @JsonIgnore
    private List<Capacity> warehouseProductCategoryCapacities;
}
