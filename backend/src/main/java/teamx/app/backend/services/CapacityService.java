package teamx.app.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import teamx.app.backend.models.Capacity;
import teamx.app.backend.repositories.CapacityRepository;
import teamx.app.backend.utils.DTO.CapacityDTO;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Capacity service
 *
 * @author Joey van der Poel
 */
@Service
public class CapacityService {

    private final CapacityRepository capacityRepository;

    @Autowired
    public CapacityService(CapacityRepository capacityRepository) {
        this.capacityRepository = capacityRepository;
    }

    public List<CapacityDTO> getAllCapacities() {
        List<Capacity> capacities = capacityRepository.findAll();
        return capacities.stream()
                .map(Capacity::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<CapacityDTO> getCapacityById(Long id) {
        Optional<Capacity> capacity = capacityRepository.findById(id);
        return capacity.map(Capacity::toDTO);
    }

    public CapacityDTO createCapacity(CapacityDTO capacityDTO) {
        Capacity capacity = new Capacity();
        mapDTOToCapacity(capacityDTO, capacity);
        return capacityRepository.save(capacity).toDTO();
    }

    public CapacityDTO updateCapacity(Long id, CapacityDTO capacityDTO) {
        Optional<Capacity> optionalCapacity = capacityRepository.findById(id);
        if (optionalCapacity.isPresent()) {
            Capacity capacity = optionalCapacity.get();
            mapDTOToCapacity(capacityDTO, capacity);
            return capacityRepository.save(capacity).toDTO();
        }
        return null; // Handle case where capacity with the given id is not found
    }

    public void deleteCapacity(Long id) {
        capacityRepository.deleteById(id);
    }

    // Helper method to map DTO to Entity
    private void mapDTOToCapacity(CapacityDTO capacityDTO, Capacity capacity) {
        capacity.setCapacity(capacityDTO.getCapacity());
        capacity.setMinimumStockLevel(capacityDTO.getMinimumStockLevel());
        // Map other fields accordingly (e.g., warehouse, product category, etc.)
    }
}
