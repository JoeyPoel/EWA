package teamx.app.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import teamx.app.backend.models.Product;
import teamx.app.backend.repositories.ProductRepository;

import java.util.List;
import java.util.Optional;

/**
 * Product Controller
 * This class is a REST controller for the product model.
 *
 * @author Joey van der Poel
 * @author Junior Javier Brito Perez
 * @see Product
 * @see ProductRepository
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("products")
public class ProductController {

    private final ProductRepository productRepository;

    @Autowired
    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("/test")
    public ResponseEntity<?> getTestOffers() {
        return new ResponseEntity<>(productRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/getAllProducts")
    public ResponseEntity<List<Product>> getAllProducts() {
        try {
            List<Product> products = productRepository.findAll();
            if (products.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No products found");
            }
            return new ResponseEntity<>(products, HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error retrieving products");
        }
    }

    @GetMapping("/getProductById/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        try {
            Product product = productRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NO_CONTENT, "No product found"));
            return new ResponseEntity<>(product, HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error retrieving product");
        }
    }

    @PostMapping("/addProduct")
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        try {
            Product newProduct = productRepository.save(product);
            return new ResponseEntity<>(newProduct, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error adding product");
        }
    }

    @PutMapping("/updateProduct/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product newProductData) {
        try {
            Optional<Product> originalProductData = productRepository.findById(id);

            if (originalProductData.isPresent()) {
                Product updatedProductData = updateProductData(newProductData, originalProductData);

                Product updatedProduct = productRepository.save(updatedProductData);
                return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Warehouse not found");
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error updating warehouse");
        }
    }

    @DeleteMapping("/deleteProduct/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable Long id) {
        try {
            Optional<Product> product = productRepository.findById(id);

            if (product.isPresent()) {
                productRepository.deleteById(id);
                return new ResponseEntity<>(product.get(), HttpStatus.OK);
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error deleting product");
        }
    }

    private Product updateProductData(Product newProductData, Optional<Product> originalProductData) {
        Product updatedProductData = originalProductData.get();
        updatedProductData.setName(newProductData.getName());
        updatedProductData.setDescription(newProductData.getDescription());
        updatedProductData.setCategory(newProductData.getCategory());
        return updatedProductData;
    }
}
