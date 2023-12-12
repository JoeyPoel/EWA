package teamx.app.backend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import teamx.app.backend.utils.DTO.ProductDTO;
import teamx.app.backend.utils.Model;

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
public class Product implements Model<ProductDTO> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private Double price;

    @ManyToOne
    private ProductCategory category;

    @JsonIgnore
    @OneToMany(mappedBy = "product")
    private List<Transaction> transactions;

    @Override
    public ProductDTO toDTO() {
        return ProductDTO.builder()
                .id(id)
                .name(name)
                .description(description)
                .price(price)
                .categoryId(category.getId())
                .build();
    }
}