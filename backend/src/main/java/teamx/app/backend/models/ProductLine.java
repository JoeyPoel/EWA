package teamx.app.backend.models;

import jakarta.persistence.*;
import lombok.*;

/**
 * ProductLine entity
 * Represents a product line
 *
 * @author Junior Javier Brito Perez
 * @see Product
 * @see Order
 * @see Project
 */
@Data
@Entity
@Table(name = "ProductLines")
@NoArgsConstructor
@AllArgsConstructor
public class ProductLine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    private int quantity;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
}