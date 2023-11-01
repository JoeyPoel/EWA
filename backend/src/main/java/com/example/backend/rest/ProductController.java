package com.example.backend.rest;
import com.example.backend.models.Product;
import com.example.backend.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
}
