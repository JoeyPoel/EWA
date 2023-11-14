package teamx.app.backend.rest;
import teamx.app.backend.models.Product;
import teamx.app.backend.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import java.util.List;
/**
 * Product Controller
 *
 * @author Joey van der Poel
 */
@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductRepository<Product> productRepository;

    @Autowired
    public ProductController(ProductRepository<Product> productRepository) {
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

    @GetMapping("/allTypes")
    public List<String> getAllTypes() {
        return this.productRepository.findAllTypes();
    }

    @PostMapping("/addProduct")
    public ResponseEntity<String> addProduct(@RequestBody List<Product> products) {
        try {
            for (Product product : products) {
                // Call the AddProduct method for each warehouse
                productRepository.AddProduct(product.getId(), product.getName(), product.getDescription(), product.getQuantity(), product.getWarehouseId());
            }
            productRepository.AddProductToProductList(products.get(0).getName());

            return new ResponseEntity<>("Product added successfully for all warehouses", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
