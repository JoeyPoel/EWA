package com.example.backend.repositories;

import java.util.List;

public interface WarehouseRepository<Warehouse> {
    List<Warehouse> findAll();
}
