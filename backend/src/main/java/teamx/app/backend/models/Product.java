package teamx.app.backend.models;

import jakarta.persistence.*;
import lombok.*;

/**
 * Product entity
 * Represents a product
 *
 * @author Jayden Gunhan
 * @author Junior Javier Brito Perez
 */
@Data
@Entity
@Table(name = "Products")
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private ProductCategory category;
}