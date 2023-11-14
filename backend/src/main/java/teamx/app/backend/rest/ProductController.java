package teamx.app.backend.rest;
import org.springframework.web.server.ResponseStatusException;
import teamx.app.backend.models.Product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import teamx.app.backend.repositories.ProductRepositoryMock;

import java.util.List;
/**
 * Product Controller
 *
 * @author Joey van der Poel
 */
@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("products")
public class ProductController {

    private final ProductRepositoryMock productRepository;

    @Autowired
    public ProductController(ProductRepositoryMock productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("/test")
    public List<Product> getTestOffers() {
        return List.of(
                new Product(1),
                new Product(2)
        );
    }

    @GetMapping("/all")
    public List<Product> getAll() {
        return this.productRepository.findAll();
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody Product product) {
        if (productRepository.findById(product.getId()) != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Product already exists");
        }

        productRepository.save(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }
}
