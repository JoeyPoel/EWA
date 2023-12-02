package teamx.app.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import teamx.app.backend.models.ProductCategory;
import teamx.app.backend.models.Warehouse;
import teamx.app.backend.models.Capacity;
import teamx.app.backend.models.dto.CapacityDTO;
import teamx.app.backend.repositories.ProductCategoryRepository;
import teamx.app.backend.repositories.WarehouseProductCategoryCapacityRepository;
import teamx.app.backend.repositories.WarehouseRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class WarehouseService {
    private final WarehouseRepository warehouseRepository;
    private final WarehouseProductCategoryCapacityRepository warehouseProductCategoryCapacityRepository;
    private final ProductCategoryRepository productCategoryRepository;

    @Autowired
    public WarehouseService(WarehouseRepository warehouseRepository, WarehouseProductCategoryCapacityRepository warehouseProductCategoryCapacityRepository, ProductCategoryRepository productCategoryRepository) {
        this.warehouseRepository = warehouseRepository;
        this.warehouseProductCategoryCapacityRepository = warehouseProductCategoryCapacityRepository;
        this.productCategoryRepository = productCategoryRepository;
    }

    public List<Warehouse> getAll() {
        List<Warehouse> warehouses = warehouseRepository.findAll();
        if (warehouses.isEmpty()) {
            return null;
        }
        return warehouses;
    }

    public Warehouse getById(Long id) {
        return warehouseRepository.findById(id).orElse(null);
    }

    public Warehouse add(Warehouse warehouse) {
        return warehouseRepository.save(warehouse);
    }

    public Warehouse updateById(Long id, Warehouse warehouse) {
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

    public Warehouse deleteById(Long id) {
        Warehouse existingWarehouse = warehouseRepository.findById(id).orElse(null);
        if (existingWarehouse == null) {
            return null;
        }
        warehouseRepository.deleteById(id);
        return existingWarehouse;
    }

    public List<CapacityDTO> getCapacity(Long id) {
        List<Capacity> warehouseCapacity = warehouseProductCategoryCapacityRepository.findAllByWarehouseId(id);
        warehouseCapacity.addAll(getMissingCapacityCategories(id));
        List<CapacityDTO> warehouseCapacityDTO = new ArrayList<>();
        for (Capacity capacity : warehouseCapacity) {
            warehouseCapacityDTO.add(convertToCapacityDTO(capacity));
        }
        return warehouseCapacityDTO;
    }

    public List<Capacity> getMissingCapacityCategories(Long id) {
        List<ProductCategory> productCategories = productCategoryRepository.findAll();
        List<Capacity> warehouseCapacity = warehouseProductCategoryCapacityRepository.findAllByWarehouseId(id);

        for (Capacity capacity : warehouseCapacity) {
            productCategories.removeIf(productCategory -> Objects.equals(productCategory.getId(), capacity.getProductCategory().getId()));
        }

        List<Capacity> missingWarehouseCapacity = new ArrayList<>();

        for (ProductCategory productCategory : productCategories) {
            Capacity capacity = new Capacity();
            capacity.setProductCategory(productCategory);
            capacity.setCapacity(0);
            capacity.setMinimumStockLevel(0);
            capacity.setWarehouse(warehouseRepository.findById(id).orElse(null));
            missingWarehouseCapacity.add(capacity);
        }

        return missingWarehouseCapacity;
    }

    public CapacityDTO addCapacity(Long id, CapacityDTO capacity) {
        Warehouse warehouse = warehouseRepository.findById(id).orElse(null);
        if (warehouse == null) {
            return null;
        }
        Capacity newCapacity = convertToCapacityEntity(capacity);
        return convertToCapacityDTO(warehouseProductCategoryCapacityRepository.save(newCapacity));
    }

    public CapacityDTO updateCapacityById(Long id, CapacityDTO capacity) {
        Capacity existingCapacity = warehouseProductCategoryCapacityRepository.findById(id).orElse(null);
        if (existingCapacity == null || capacity == null || !Objects.equals(capacity.getId(), id)) {
            return null;
        }
        existingCapacity.setCapacity(capacity.getCapacity());
        existingCapacity.setMinimumStockLevel(capacity.getMinimumStockLevel());
        return convertToCapacityDTO(warehouseProductCategoryCapacityRepository.save(existingCapacity));
    }

    public CapacityDTO convertToCapacityDTO
            (Capacity capacity) {
        CapacityDTO dto = new CapacityDTO();
        dto.setId(capacity.getId());
        dto.setWarehouseId(capacity.getWarehouse().getId());
        dto.setCategoryName(capacity.getProductCategory().getName());
        dto.setCategoryId(capacity.getProductCategory().getId());
        dto.setCapacity(capacity.getCapacity());
        dto.setMinimumStockLevel(capacity.getMinimumStockLevel());
        return dto;
    }

    public Capacity convertToCapacityEntity(CapacityDTO dto) {
        Capacity capacity = new Capacity();
        capacity.setId(dto.getId());
        capacity.setWarehouse(warehouseRepository.findById(dto.getWarehouseId()).orElse(null));
        capacity.setProductCategory(productCategoryRepository.findById(dto.getCategoryId()).orElse(null));
        capacity.setCapacity(dto.getCapacity());
        capacity.setMinimumStockLevel(dto.getMinimumStockLevel());
        return capacity;
    }
}
