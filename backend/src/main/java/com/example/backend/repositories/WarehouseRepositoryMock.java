package com.example.backend.repositories;
import com.example.backend.models.Warehouse;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("WAREHOUSE.MOCK")
public class WarehouseRepositoryMock {
    private List<Warehouse> warehouses;

    public WarehouseRepositoryMock(){
        this.warehouses = List.of(
                Warehouse.CreateSampleWarehouse(1, "WarehouseName"),
                Warehouse.CreateSampleWarehouse(1, "WarehouseName"),
                Warehouse.CreateSampleWarehouse(1, "WarehouseName"),
                Warehouse.CreateSampleWarehouse(1, "WarehouseName"),
                Warehouse.CreateSampleWarehouse(1, "WarehouseName"),
                Warehouse.CreateSampleWarehouse(1, "WarehouseName")
                );
    }
}
