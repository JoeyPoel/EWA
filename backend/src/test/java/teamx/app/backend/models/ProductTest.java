package teamx.app.backend.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import teamx.app.backend.utils.DTO;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductTest {
    private Product product;
    private ProductCategory category;

    @BeforeEach
    void setUp() {
        category = ProductCategory.builder()
                .id(1L)
                .name("TestCategory")
                .description("Test Category Description")
                .build();

        product = new Product();
        product.setId(1L);
        product.setName("Test Product");
        product.setDescription("A sample product description");
        product.setPrice(100.0);
        product.setCategory(category);
    }

    @Test
    void testProductDTO() {
        Long expectedId = 1L;
        String expectedName = "Test Product";
        String expectedDescription = "A sample product description";
        Double expectedPrice = 100.0;
        Long expectedCategoryId = 2L;

        DTO.ProductDTO productDTO = DTO.ProductDTO.builder()
                .id(expectedId)
                .name(expectedName)
                .description(expectedDescription)
                .price(expectedPrice)
                .categoryId(expectedCategoryId)
                .build();

        assertEquals(expectedId, productDTO.getId());
        assertEquals(expectedName, productDTO.getName());
        assertEquals(expectedDescription, productDTO.getDescription());
        assertEquals(expectedPrice, productDTO.getPrice());
        assertEquals(expectedCategoryId, productDTO.getCategoryId());
    }

    @Test
    void testProductGettersAndSetters() {
        assertEquals(1L, product.getId());
        assertEquals("Test Product", product.getName());
        assertEquals("A sample product description", product.getDescription());
        assertEquals(100.0, product.getPrice());
        assertEquals(category, product.getCategory());
    }

    @Test
    void testToDTO() {
        DTO.ProductDTO dto = product.toDTO();

        assertNotNull(dto);
        assertEquals(product.getId(), dto.getId());
        assertEquals(product.getName(), dto.getName());
        assertEquals(product.getDescription(), dto.getDescription());
        assertEquals(product.getPrice(), dto.getPrice());
        assertEquals(product.getCategory().getId(), dto.getCategoryId());
    }
}