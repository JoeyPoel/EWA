package teamx.app.backend.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import teamx.app.backend.models.Product;
import teamx.app.backend.repositories.ProductRepository;

import java.util.List;

/**
 * Product Controller
 * This class is a REST controller for the product model.
 *
 * @author Joey van der Poel
 * @author Junior Javier Brito Perez
 * @see Product
 * @see ProductRepository
 */
@CrossOrigin(origins = "http://localhost:8080")
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

    @PostMapping("/addProduct")
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        try {
            Product newProduct = productRepository.save(product);
            return new ResponseEntity<>(newProduct, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error adding product");
        }
    }
}
