package teamx.app.backend.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Product entity
 * Represents a product
 *
 * @author Jayden Gunhan
 * @author Junior Javier Brito Perez
 */
@Data
@Entity(name = "Products")
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private Double price;

    @ManyToOne
    @JsonManagedReference
    private ProductCategory category;


    @OneToMany
    @JsonBackReference
    private List<Transaction> transactions;
}