package com.example.backend.repositories;

import java.util.List;

public interface ProductRepository<Product> {
    List<Product> findAll();

    List<String> findAllTypes();

    void AddProduct(int id, String name, String description, int quantity, int warehouseId);
}
