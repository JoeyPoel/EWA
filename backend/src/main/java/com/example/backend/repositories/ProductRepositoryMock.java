package com.example.backend.repositories;

import com.example.backend.models.Product;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("OFFERS.MOCK")
public class ProductRepositoryMock implements ProductRepository<Product> {

    @Override
    public List findAll() {
        return this.offers;
    }

    private List<Product> offers;

    public ProductRepositoryMock(){
        this.offers = List.of(
            Product.CreateSampleOffer(1, "ProductName"),
            Product.CreateSampleOffer(1, "ProductName"),
            Product.CreateSampleOffer(1, "ProductName"),
            Product.CreateSampleOffer(1, "ProductName"),
            Product.CreateSampleOffer(1, "ProductName"),
            Product.CreateSampleOffer(1, "ProductName")
            );
    }
}
