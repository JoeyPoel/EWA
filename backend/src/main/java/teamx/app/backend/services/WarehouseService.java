package teamx.app.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import teamx.app.backend.utils.DTO.CapacityDTO;
import teamx.app.backend.models.Capacity;
import teamx.app.backend.models.ProductCategory;
import teamx.app.backend.models.Warehouse;
import teamx.app.backend.repositories.CapacityRepository;
import teamx.app.backend.repositories.ProductCategoryRepository;
import teamx.app.backend.repositories.WarehouseRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@Service
public class WarehouseService {
    private final WarehouseRepository warehouseRepository;
    private final CapacityRepository capacityRepository;
    private final ProductCategoryRepository productCategoryRepository;


    @Autowired
    public WarehouseService(WarehouseRepository warehouseRepository, CapacityRepository capacityRepository,
                            ProductCategoryRepository productCategoryRepository) {
        this.warehouseRepository = warehouseRepository;
        this.capacityRepository = capacityRepository;
        this.productCategoryRepository = productCategoryRepository;
    }

    public List<Warehouse> findAll() {
//        TODO: Return empty list instead of throwing exception in production
        List<Warehouse> warehouses = warehouseRepository.findAll();
        if (warehouses.isEmpty()) {
            throw new NoSuchElementException("No warehouses found");
        }
        return warehouses;
    }

    protected List<Long> findAllIds() {
        return findAll()
                .stream()
                .map(Warehouse::getId)
                .toList();
    }

    public Warehouse findById(Long id) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Warehouse id is null");
        }

        return warehouseRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Warehouse not found"));
    }

    public Warehouse add(Warehouse warehouse) {
        return warehouseRepository.save(warehouse);
    }

    public Warehouse updateById(Long id, Warehouse warehouse) {
        validateInputs(id, warehouse);
        Warehouse existingWarehouse = findExistingWarehouse(id);

        updateProperties(existingWarehouse, warehouse);

        return warehouseRepository.save(existingWarehouse);
    }

    public Warehouse deleteById(Long warehouseId) {
        Warehouse existingWarehouse = warehouseRepository.findById(warehouseId).orElse(null);

        if (existingWarehouse == null) {
            throw new NullPointerException("Warehouse not found");
        }

        warehouseRepository.deleteById(warehouseId);
        return existingWarehouse;
    }

    private void validateInputs(Long id, Warehouse warehouse) {
        if (warehouse == null) {
            throw new NullPointerException("Warehouse is null");
        }

        if (id <= 0 || warehouse.getId() <= 0) {
            throw new IllegalArgumentException("Invalid warehouse id");
        }

        if (!Objects.equals(warehouse.getId(), id)) {
            throw new IllegalStateException("Warehouse id does not match");
        }
    }

    private Warehouse findExistingWarehouse(Long id) {
        return warehouseRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Warehouse not found"));
    }

    private void updateProperties(Warehouse existingWarehouse, Warehouse warehouse) {
        existingWarehouse.setName(warehouse.getName());
        existingWarehouse.setLocation(warehouse.getLocation());
        existingWarehouse.setAddress(warehouse.getAddress());
        existingWarehouse.setPostcode(warehouse.getPostcode());
        existingWarehouse.setCountry(warehouse.getCountry());
        existingWarehouse.setContactName(warehouse.getContactName());
        existingWarehouse.setContactEmail(warehouse.getContactEmail());
        existingWarehouse.setContactPhone(warehouse.getContactPhone());
    }
}
