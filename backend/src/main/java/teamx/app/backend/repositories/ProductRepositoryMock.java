package teamx.app.backend.repositories;

import teamx.app.backend.models.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/**
 * Repository for product
 *
 * @author Joey van der Poel
 */
@Repository()
public class ProductRepositoryMock implements ModelRepository<Product> {
    private final List<Product> products;

    public ProductRepositoryMock(){
        this.products = Product.generateRandomProducts();
    }

    @Override
    public List<Product> findAll() {
        return this.products;
    }

    public Product findById(int id) {
        return this.products.stream().filter(product -> product.getId() == id).findFirst().orElse(null);
    }

    @Override
    public Product save(Product product) {
        if (!this.products.contains(product)) {
            this.products.add(product);
            return product;
        }
        this.products.set(this.products.indexOf(product), product);
        return product;
    }

    @Override
    public Product deleteById(int id) {
        return null;
    }


    static List<String> productList = Arrays.asList(
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
    );
}
