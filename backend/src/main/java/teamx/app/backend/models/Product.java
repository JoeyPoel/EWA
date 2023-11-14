package teamx.app.backend.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Product class
 *
 * @author Jayden Gunhan
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

    private static String[] productList = {
            "Solar panels",
            "Solar Cables",
            "Main Connectors (AC)",
            "Inverter",
            "Storage Unit",
            "Montage Material",
            "Battery Pack",
            "LED Light",
            "Solar Inverter",
            "Electric Motor",
            "Charging Station"
    };

    public static List<Product> generateRandomProducts(){
        List<Product> products = new ArrayList<>();
        int id = 0;
        for (int i = 0; i < 6; i++){
            for (int j = 0; j < productList.length; j++) {
                products.add(new Product(id++, i, "Dummy Description", (int) Math.floor(Math.random() * 100), productList[j]));
            }
        }

        return products;
    }
}