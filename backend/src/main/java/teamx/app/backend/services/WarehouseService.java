package teamx.app.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import teamx.app.backend.models.Warehouse;
import teamx.app.backend.repositories.WarehouseRepository;

import java.util.List;
import java.util.Objects;

@Service
public class WarehouseService {
    private final WarehouseRepository warehouseRepository;

    @Autowired
    public WarehouseService(WarehouseRepository warehouseRepository) {
        this.warehouseRepository = warehouseRepository;
    }

    public List<Warehouse> getAllWarehouses() {
        List<Warehouse> warehouses = warehouseRepository.findAll();
        if (warehouses.isEmpty()) {
            return null;
        }
        return warehouses;
    }

    public Warehouse getWarehouseById(Long id) {
        return warehouseRepository.findById(id).orElse(null);
    }

    public Warehouse addWarehouse(Warehouse warehouse) {
        return warehouseRepository.save(warehouse);
    }

    public Warehouse updateWarehouseById(Long id, Warehouse warehouse) {
        Warehouse existingWarehouse = warehouseRepository.findById(id).orElse(null);
        if (existingWarehouse == null || warehouse == null || !Objects.equals(warehouse.getId(), id)) {
            return null;
        }
        existingWarehouse.setName(warehouse.getName());
        existingWarehouse.setLocation(warehouse.getLocation());
        existingWarehouse.setAddress(warehouse.getAddress());
        existingWarehouse.setPostcode(warehouse.getPostcode());
        existingWarehouse.setCountry(warehouse.getCountry());
        existingWarehouse.setContactName(warehouse.getContactName());
        existingWarehouse.setContactEmail(warehouse.getContactEmail());
        existingWarehouse.setContactPhone(warehouse.getContactPhone());
        return warehouseRepository.save(existingWarehouse);
    }

    public Warehouse deleteWarehouseById(Long id) {
        Warehouse existingWarehouse = warehouseRepository.findById(id).orElse(null);
        if (existingWarehouse == null) {
            return null;
        }
        warehouseRepository.deleteById(id);
        return existingWarehouse;
    }
}
