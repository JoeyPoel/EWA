package teamx.app.backend.models;

import jakarta.persistence.*;
import lombok.*;

/**
 * ProductCategory entity
 * Represents a category of products
 *
 * @author Junior Javier Brito Perez
 * @see Product
 */
@Data
@Entity
@Table(name = "ProductCategories")
@NoArgsConstructor
@AllArgsConstructor
public class ProductCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
}
