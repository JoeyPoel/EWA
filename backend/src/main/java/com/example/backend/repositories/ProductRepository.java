package com.example.backend.repositories;

import java.util.List;

public interface ProductRepository<Product> {
    List<Product> findAll();

    List<String> findAllTypes();
}
