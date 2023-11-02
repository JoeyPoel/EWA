package com.example.backend.rest;
import com.example.backend.models.Product;
import com.example.backend.repositories.ProductRepository;
import com.example.backend.repositories.ProductRepositoryMock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@CrossOrigin("http://localhost:8080/")
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
    public ResponseEntity<String> addProduct(@RequestBody Product product) {
        try {
            productRepository.AddProduct(1,product.getName(),0, 0 ); // Save the product to the database
            return new ResponseEntity<>("Product added successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
