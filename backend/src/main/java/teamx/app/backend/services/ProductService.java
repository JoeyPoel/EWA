package teamx.app.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import teamx.app.backend.models.Product;
import teamx.app.backend.models.ProductCategory;
import teamx.app.backend.repositories.ProductCategoryRepository;
import teamx.app.backend.repositories.ProductRepository;
import teamx.app.backend.utils.DTO.ProductDTO;

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

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public List<ProductCategory> findAllProductCategories() {
        List<ProductCategory> productCategories = productCategoryRepository.findAll();
        if (productCategories.isEmpty()) {
            throw new NoSuchElementException("No product categories found");
        }
        return productCategories;
    }

    public Product findById(Long productId) {
        return productRepository.findById(productId).orElse(null);
    }

    private Product save(Product product) {
        return productRepository.save(product);
    }

    public Product add(ProductDTO productDTO) {
        return mapToEntity(new Product(), productDTO);
    }

    public Product update(Long productId, ProductDTO productDTO) {
        if (!productId.equals(productDTO.getId())) throw new IllegalArgumentException("Product ID does not match");
        return mapToEntity(findById(productId), productDTO);
    }

    public Product delete(Long id) {
        Product existingProduct = productRepository.findById(id).orElse(null);
        if (existingProduct == null) {
            return null;
        }
        productRepository.deleteById(id);
        return existingProduct;
    }

    protected List<Long> findAllActiveIds() {
        return productRepository
                .findAllByTransactionsIsNotEmpty()
                .stream()
                .map(Product::getId)
                .toList();
    }

    protected List<Product> findAllByIds(List<Long> productIds) {
        return productRepository.findAllById(productIds);
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

    public List<Product> findAllActive() {
        return productRepository.findAllByTransactionsIsNotEmpty();
    }
}