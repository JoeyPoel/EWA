package teamx.app.backend.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Product class
 *
 * @author Joey van der Poel
 */

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    private int id;
    private int warehouseId;
    private String description;
    private int quantity;
    private String name;


    public Product(int id) {
        this.id = id;
    }
}