package teamx.app.backend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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
    @NotNull(message = "Name is required")
    private String name;
    @NotNull(message = "Description is required")
    private String description;
    @NotNull(message = "Price is required")
    private Double price;

    @ManyToOne
    @NotNull(message = "Category is required")
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
                .categoryId(category != null ? category.getId() : null)
                .build();
    }
}