package com.example.backend.rest;
import com.example.backend.models.Warehouse;
import com.example.backend.repositories.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/warehouses")
public class WarehouseController {
    @Autowired
    WarehouseRepository<Warehouse> warehouseRepository;
    @GetMapping("test")
    public List<Warehouse> getTestOffers() {
        return List.of(
                new Warehouse(1),
                new Warehouse(2)
        );
    }

    @GetMapping("getAll")
    public List<Warehouse> getAll(){
        return this.warehouseRepository.findAll();
    }
}
