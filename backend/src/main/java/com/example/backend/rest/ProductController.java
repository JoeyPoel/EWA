package com.example.backend.rest;
import com.example.backend.models.Product;
import com.example.backend.models.ProductRequest;
import com.example.backend.models.Warehouse;
import com.example.backend.repositories.ProductRepository;
import com.example.backend.repositories.ProductRepositoryMock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    ProductRepository<Product> productRepository;
    @GetMapping("/test")
    public List<Product> getTestOffers() {
        return List.of(
                new Product(1),
                new Product(2)
        );
    }

    @GetMapping("/getAll")
    public List<Product> getAll(){
        return this.productRepository.findAll();
    }

    @GetMapping("/getAllTypes")
    public List<String> getAllTypes(){
        return this.productRepository.findAllTypes();
    }

    @PostMapping("/addProduct")
    public ResponseEntity<String> addProduct(@RequestBody ProductRequest productRequest) {
        try {
            String productName = productRequest.getName();

            // Check if the required fields are present in the request
            if (productName == null) {
                return new ResponseEntity<>("Product name is required.", HttpStatus.BAD_REQUEST);
            }

            List<Warehouse> warehouses = productRequest.getWarehouses(); // Extract the list of warehouses

            for (Warehouse warehouse : warehouses) {
                int warehouseId = warehouse.getId();
                int quantity = productRequest.getQuantity(); // Get the quantity from the warehouse

                // Generate the ID as per your logic
                int generatedId = generateProductId();

                // Call the AddProduct method for each warehouse
                productRepository.AddProduct(generatedId, productName, "Dummy Description", quantity, warehouseId);
            }

            return new ResponseEntity<>("Product added successfully for all warehouses", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    // Generate product ID logic
    private int generateProductId() {
        // Implement your logic for generating a unique product ID
        // This can be a simple auto-increment or any custom ID generation strategy
        // Return the generated ID
        return 0;
    }
}
