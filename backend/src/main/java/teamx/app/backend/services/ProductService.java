package teamx.app.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import teamx.app.backend.models.Product;
import teamx.app.backend.models.ProductCategory;
import teamx.app.backend.models.dto.ProductDTO;
import teamx.app.backend.repositories.ProductCategoryRepository;
import teamx.app.backend.repositories.ProductRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductCategoryRepository productCategoryRepository;

    @Autowired
    public ProductService(ProductRepository productRepository, ProductCategoryRepository productCategoryRepository) {
        this.productRepository = productRepository;
        this.productCategoryRepository = productCategoryRepository;
    }

    public List<ProductDTO> findAll() {
        List<Product> products = productRepository.findAll();
        if (products.isEmpty()) {
            throw new NoSuchElementException("No products found");
        }
        return mapToDTO(products);
    }

    public List<ProductCategory> findAllProductCategories() {
        List<ProductCategory> productCategories = productCategoryRepository.findAll();
        if (productCategories.isEmpty()) {
            throw new NoSuchElementException("No product categories found");
        }
        return productCategories;
    }

    public Product findById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new NoSuchElementException("Product not found with id " + productId));
    }

    public ProductDTO findDTOById(Long productId) {
        return mapToDTO(findById(productId));
    }

    private Product save(Product product) {
        return productRepository.save(product);
    }

    public ProductDTO add(ProductDTO productDTO) {
        Product product = mapToEntity(new Product(), productDTO);
        return mapToDTO(product);
    }

    public ProductDTO update(Long productId, ProductDTO productDTO) {
        if (!productId.equals(productDTO.getId())) throw new IllegalArgumentException("Product ID does not match");
        Product product = mapToEntity(findById(productId), productDTO);
        return mapToDTO(product);
    }

    public ProductDTO delete(Long id) {
        Product existingProduct = productRepository.findById(id).orElse(null);
        if (existingProduct == null) {
            return null;
        }
        productRepository.deleteById(id);
        return mapToDTO(existingProduct);
    }

    private Product mapToEntity(Product product, ProductDTO productDTO) {
        product.setId(productDTO.getId());
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        if (productDTO.getCategoryId() != null) {
            product.setCategory(
                    productCategoryRepository
                            .findById(productDTO.getCategoryId())
                            .orElseThrow(() ->
                                    new NoSuchElementException("Product category not found with id " +
                                            productDTO.getCategoryId() + " for product " + productDTO.getId())
                            )
            );
        }
        return save(product);
    }

    private ProductDTO mapToDTO(Product product) {
        return new ProductDTO(product);
    }

    private List<ProductDTO> mapToDTO(List<Product> products) {
        return products
                .stream()
                .map(this::mapToDTO)
                .toList();
    }
}
