package teamx.app.backend.models;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import teamx.app.backend.models.Product;
import teamx.app.backend.repositories.ProductRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
public class ProductTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void testCreateProduct() {
        System.out.println("Test create product");
        Product product = new Product();
        product.setName("Test Product");
        product.setPrice(100.0);

        productRepository.save(product);

        Product foundProduct = productRepository.findById(product.getId()).orElse(null);
        assertEquals(product.getName(), foundProduct.getName());
        assertEquals(product.getPrice(), foundProduct.getPrice());
    }

    @Test
    public void testCreateTwoProducts() {
        // Create first product
        Product product1 = new Product();
        product1.setName("Test Product 1");
        product1.setPrice(100.0);
        productRepository.save(product1);

        // Create second product
        Product product2 = new Product();
        product2.setName("Test Product 2");
        product2.setPrice(200.0);
        productRepository.save(product2);

        // Retrieve and compare the products
        Product foundProduct1 = productRepository.findById(product1.getId()).orElse(null);
        Product foundProduct2 = productRepository.findById(product2.getId()).orElse(null);

        // Assert that the products are not the same
        assertNotEquals(foundProduct1.getId(), foundProduct2.getId());
        assertNotEquals(foundProduct1.getName(), foundProduct2.getName());
        assertNotEquals(foundProduct1.getPrice(), foundProduct2.getPrice());

        // Assert that the retrieved products match the original products
        assertEquals(product1.getName(), foundProduct1.getName());
        assertEquals(product1.getPrice(), foundProduct1.getPrice());
        assertEquals(product2.getName(), foundProduct2.getName());
        assertEquals(product2.getPrice(), foundProduct2.getPrice());
    }
}