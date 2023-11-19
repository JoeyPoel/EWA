package teamx.app.backend.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Table
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class ProductLine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int quantity;

    @ManyToOne
    @JoinColumn
    @JsonBackReference
    private Product product;


    @ManyToOne
    @JoinColumn
    @JsonBackReference
    private Project project;

    @ManyToOne
    @JoinColumn
    @JsonBackReference
    private Order order;
}