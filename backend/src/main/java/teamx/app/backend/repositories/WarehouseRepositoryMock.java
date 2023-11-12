package teamx.app.backend.repositories;

import org.springframework.stereotype.Repository;
import teamx.app.backend.models.Warehouse;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
    * This class is a mock repository for the Warehouse model.
    *
    * @author Junior Javier Brito Perez
 */
@Repository
public class WarehouseRepositoryMock implements ModelRepository<Warehouse>{
    private final ArrayList<Warehouse> warehouses;
    private final int WAREHOUSE_COUNT = 10;
    private final int WAREHOUSE_ID_MIN = 100;
    private final int WAREHOUSE_ID_MAX = 1000;

    WarehouseRepositoryMock() {
        warehouses = Warehouse.generateRandomWarehouses(WAREHOUSE_COUNT);
    }

    @Override
    public List<Warehouse> findAll() {
        return warehouses;
    }

    @Override
    public Warehouse findById(int id) {
        return warehouses.stream().filter(warehouse -> warehouse.getId() == id).findFirst().orElse(null);
    }

    @Override
    public Warehouse save(Warehouse warehouse) {
        if (!warehouses.contains(warehouse)) {
            if (warehouse.getId() == 0) {
                do {
                    warehouse.setId(new Random().nextInt(WAREHOUSE_ID_MAX) + WAREHOUSE_ID_MIN);
                } while (warehouses.contains(warehouse));
            }
            warehouses.add(warehouse);
            return warehouse;
        }
        warehouses.set(warehouses.indexOf(warehouse), warehouse);
        return warehouse;
    }

    @Override
    public Warehouse deleteById(int id) {
        return warehouses.remove(id);
    }
}

