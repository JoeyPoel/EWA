package teamx.app.backend.repositories;

import java.util.List;
/**
 * Interface of repository of product
 *
 * @author Joey van der Poel
 */
public interface ProductRepository<Product> {
    List<Product> findAll();

    List<String> findAllTypes();

    void AddProduct(int id, String name, String description, int quantity, int warehouseId);

    void editProduct(int id, String name, String description, int quantity, int warehouseId);

    void removeProduct(int id, String name, String description, int quantity, int warehouseId);

    void AddProductToProductList(String name);
}
