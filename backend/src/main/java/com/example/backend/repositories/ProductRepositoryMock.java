package com.example.backend.repositories;

import com.example.backend.models.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository("OFFERS.MOCK")
public class ProductRepositoryMock implements ProductRepository<Product> {

    @Override
    public List<Product> findAll() {
        return this.products;
    }
    @Override
    public List<String> findAllTypes() {
        return productList;
    }

    private List<Product> products = new ArrayList<>();
    static List<String> productList = Arrays.asList(
            "Solar panels",
            "Solar Cables",
            "Main Connectors (AC)",
            "Inverter",
            "Storage Unit",
            "Montage Material",
            "Battery Pack",
            "LED Light",
            "Solar Inverter",
            "Electric Motor",
            "Charging Station"
    );

    public ProductRepositoryMock(){
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < productList.size(); j++) {
                products.add(Product.CreateSampleOffer(j, productList.get(j), "Dummy Description", (int) Math.floor(Math.random() * 100), i));
            }
        }
    }
}
