package teamx.app.backend.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import teamx.app.backend.models.Product;
import teamx.app.backend.models.ProductCategory;
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
        ProductCategory category = new ProductCategory();
        category.setName("Test Category");
        category.setDescription("Test Category Description");
        entityManager.persist(category);

        warehouse = new Warehouse();
        warehouse.setName("Test Warehouse");
        warehouse.setLocation("Test Location");
        warehouse.setCountry("Test Country");
        warehouse.setPostcode("1234AB");
        warehouse.setAddress("Test Address");
        entityManager.persist(warehouse);

        Product product = new Product();
        product.setName("Test Product");
        product.setDescription("Test Description");
        product.setPrice(100.0);
        product.setCategory(category);
        entityManager.persist(product);

        Transaction transaction = new Transaction();
        transaction.setProduct(product);
        transaction.setWarehouse(warehouse);
        transaction.setQuantity(10);
        entityManager.persist(transaction);
    }

    @Test
    public void testGetAllByTransactionsWarehouseId() {
        List<Product> products = productRepository.getAllByTransactions_Warehouse_Id(warehouse.getId());

        assertFalse(products.isEmpty(), "Should return non-empty list for specific warehouse ID");

        Product firstProduct = products.get(0);
        assertEquals("Test Product", firstProduct.getName());
        assertEquals("Test Description", firstProduct.getDescription());
        assertEquals(100.0, firstProduct.getPrice());
    }

    @Test
    public void testFindAllByTransactionsIsNotEmpty() {
        List<Product> products = productRepository.findAllByTransactionsIsNotEmpty();
        assertFalse(products.isEmpty(), "Should return non-empty list of products with transactions");
    }

    @Test
    public void testGetAllByTransactionsWarehouseIdWithNoTransactions() {
        Warehouse emptyWarehouse = new Warehouse();
        emptyWarehouse.setName("Empty Warehouse");
        emptyWarehouse.setLocation("No Location");
        emptyWarehouse.setCountry("No Country");
        emptyWarehouse.setPostcode("0000AB");
        emptyWarehouse.setAddress("No Address");
        emptyWarehouse = entityManager.persist(emptyWarehouse);

        List<Product> products = productRepository.getAllByTransactions_Warehouse_Id(emptyWarehouse.getId());
        assertTrue(products.isEmpty(), "Should return an empty list for warehouse with no transactions");
    }

    @Test
    public void testGetAllByNonExistentWarehouseId() {
        Long nonExistentWarehouseId = 999L; // ID that does not exist in the database
        List<Product> products = productRepository.getAllByTransactions_Warehouse_Id(nonExistentWarehouseId);
        assertTrue(products.isEmpty(), "Should return an empty list for non-existent warehouse ID");
    }

    @Test
    public void testFindAll() {
        List<Product> products = productRepository.findAll();
        assertFalse(products.isEmpty(), "Should return non-empty list of products");
    }
}
