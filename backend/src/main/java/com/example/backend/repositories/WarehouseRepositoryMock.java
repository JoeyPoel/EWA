package com.example.backend.repositories;

import com.example.backend.models.Warehouse;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository("WAREHOUSE.MOCK")
public class WarehouseRepositoryMock implements WarehouseRepository<Warehouse> {
    private List<Warehouse> warehouses = new ArrayList<>();
    static List<String> warehouseList = Arrays.asList(
            "Solar clarity",
            "Solar groen",
            "4Blue",
            "Stralend Groen",
            "Ziezo-Solar",
            "Baywa"
    );

    static List<String> locationList = Arrays.asList(
            "Amsterdam",
            "Rotterdam",
            "Den Haag",
            "Utrecht",
            "Eindhoven",
            "Tilburg",
            "Groningen",
            "Almere"
    );

    static int getRandomInt(int max) {
        return (int) Math.floor(Math.random() * Math.floor(max));
    }

    public WarehouseRepositoryMock() {
        for (int i = 0; i < warehouseList.size(); i++) {
            warehouses.add(
                    Warehouse.CreateSampleWarehouse(
                            i,
                            warehouseList.get(i),
                            locationList.get(getRandomInt(locationList.size())),
                            "Kerkstraat 1",
                            "1234 AB",
                            "Nederland",
                            "Jan Jansen",
                            "janjansen@gmail.com",
                            "0612345678",
                            "Dit is een dummy warehouse"
                    )
            );
        }
    }

    @Override
    public List<Warehouse> findAll() {
        return this.warehouses;
    }
}
