package com.example.backend.repositories;

import java.util.List;

public interface ProductRepository<Offer> {
    List<Offer> findAll();
}
