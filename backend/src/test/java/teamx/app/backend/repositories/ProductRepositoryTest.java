package teamx.app.backend.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import teamx.app.backend.models.Product;
import teamx.app.backend.models.Transaction;
import teamx.app.backend.models.Warehouse;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ProductRepositoryTests {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ProductRepository productRepository;

    private Warehouse warehouse;

    @BeforeEach
    void setUp() {
        warehouse = new Warehouse();
        warehouse.setName("Test Warehouse");
        warehouse.setLocation("Test Location");
        warehouse.setCountry("Test Country");
        warehouse.setPostcode("1234AB");
        warehouse.setAddress("Test Address");
        warehouse = entityManager.persist(warehouse);

        Product product = new Product();
        product.setName("Test Product");
        product.setDescription("Test Description");
        product.setPrice(100.0);

        Transaction transaction = new Transaction();
        transaction.setProduct(product);
        transaction.setWarehouse(warehouse);
        transaction.setQuantity(10);

        entityManager.persist(product);
        entityManager.persist(transaction);
    }


    @Test
    public void testFindAll() {
        List<Product> products = productRepository.findAll();
        assertFalse(products.isEmpty(), "Should return non-empty list of products");
    }

    @Test
    public void testGetAllByTransactionsWarehouseId() {
        List<Product> products = productRepository.getAllByTransactions_Warehouse_Id(warehouse.getId());
        assertFalse(products.isEmpty(), "Should return non-empty list for specific warehouse ID");
    }

    @Test
    public void testFindAllByTransactionsIsNotEmpty() {
        List<Product> products = productRepository.findAllByTransactionsIsNotEmpty();
        assertFalse(products.isEmpty(), "Should return non-empty list of products with transactions");
    }
}
