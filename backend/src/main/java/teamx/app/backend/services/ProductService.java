package teamx.app.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import teamx.app.backend.models.Product;
import teamx.app.backend.models.ProductCategory;
import teamx.app.backend.models.dto.ProductDTO;
import teamx.app.backend.repositories.ProductCategoryRepository;
import teamx.app.backend.repositories.ProductRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductCategoryRepository productCategoryRepository;

    @Autowired
    public ProductService(ProductRepository productRepository, ProductCategoryRepository productCategoryRepository) {
        this.productRepository = productRepository;
        this.productCategoryRepository = productCategoryRepository;
    }

    public List<Product> getAllProducts() {
        List<Product> products = productRepository.findAll();
        if (products.isEmpty()) {
            return null;
        }
        return products;
    }

    public List<ProductDTO> getAllProductsDTO() {
        List<Product> products = getAllProducts();
        if (products == null) {
            return null;
        }
        List<ProductDTO> productDTOs = new ArrayList<>();
        for (Product product : products) {
            productDTOs.add(convertToDTO(product));
        }
        return productDTOs;
    }

    public List<ProductCategory> getAllProductCategories() {
        List<ProductCategory> productCategories = productCategoryRepository.findAll();
        if (productCategories.isEmpty()) {
            return null;
        }
        return productCategories;
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public ProductDTO getProductDTOById(Long id) {
        return convertToDTO(getProductById(id));
    }

    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    public ProductDTO addProductDTO(ProductDTO productDTO) {
        return convertToDTO(addProduct(convertToEntity(productDTO)));
    }

    public Product updateProduct(Product product, Long id) {
        Product existingProduct = productRepository.findById(id).orElse(null);
        if (existingProduct == null || product == null || !Objects.equals(product.getId(), id)) {
            return null;
        }
        existingProduct.setName(product.getName());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setCategory(product.getCategory());
        return productRepository.save(existingProduct);
    }

    public ProductDTO updateProductDTO(ProductDTO productDTO, Long id) {
        return convertToDTO(updateProduct(convertToEntity(productDTO), id));
    }

    public Product deleteProduct(Long id) {
        Product existingProduct = productRepository.findById(id).orElse(null);
        if (existingProduct == null) {
            return null;
        }
        productRepository.deleteById(id);
        return existingProduct;
    }

    public ProductDTO deleteProductDTO(Long id) {
        return convertToDTO(deleteProduct(id));
    }

    public ProductDTO convertToDTO(Product product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setDescription(product.getDescription());
        productDTO.setPrice(product.getPrice());
        productDTO.setCategoryId(product.getCategory().getId());
        return productDTO;
    }

    public Product convertToEntity(ProductDTO productDTO) {
        Product product = new Product();
        product.setId(productDTO.getId());
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setCategory(productCategoryRepository.findById(productDTO.getCategoryId()).orElse(null));
        return product;
    }
}
