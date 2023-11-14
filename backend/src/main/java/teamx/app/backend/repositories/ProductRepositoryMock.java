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
@Repository("PRODUCTS.MOCK")
public class ProductRepositoryMock implements ProductRepository<Product> {

    @Override
    public List<Product> findAll() {
        return this.products;
    }
    @Override
    public List<String> findAllTypes() {
        return productList;
    }
    private final List<Product> products;

    public ProductRepositoryMock(){
        this.products = Product.generateRandomProducts();
    }

    @Override
    public void AddProduct(int id, String name, String description, int quantity, int warehouseId){
        this.products.add(new Product(id, warehouseId, description, quantity, name));
    }

    @Override
    public void editProduct(int id, String name, String description, int quantity, int warehouseId){
        Product product = new Product(id, warehouseId, description, quantity, name);
        for (int i = 0; i < this.products.size(); i++) {
            if(this.products.get(i).getId() == product.getId() && this.products.get(i).getWarehouseId() == product.getWarehouseId() ){
                this.products.set(i,product);
            }
        }
    }

    @Override
    public void removeProduct(int id, String name, String description, int quantity, int warehouseId){
        Product product = new Product(id, warehouseId, description, quantity, name);
        for (int i = 0; i < this.products.size(); i++) {
            if(this.products.get(i).getId() == product.getId() && this.products.get(i).getWarehouseId() == product.getWarehouseId() ){
                this.products.remove(product);
            }
        }
    }

    @Override
    public void AddProductToProductList(String name){
        productList.add(name);
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
