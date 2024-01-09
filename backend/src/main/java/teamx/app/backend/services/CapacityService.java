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
 * Service handling operations related to capacity.
 * Responsible for managing capacities, retrieving, creating, updating, and deleting capacity data.
 * @author Joey van der Poel
 */
@Service
public class CapacityService {

    private final CapacityRepository capacityRepository;

    @Autowired
    public CapacityService(CapacityRepository capacityRepository) {
        this.capacityRepository = capacityRepository;
    }

    /**
     * Retrieves all capacities.
     * @return List of CapacityDTO containing all capacities.
     */
    public List<CapacityDTO> getAllCapacities() {
        List<Capacity> capacities = capacityRepository.findAll();
        return capacities.stream()
                .map(Capacity::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a capacity by its ID.
     * @param id ID of the capacity to retrieve.
     * @return Optional of CapacityDTO containing the found capacity, if present.
     */
    public Optional<CapacityDTO> getCapacityById(Long id) {
        Optional<Capacity> capacity = capacityRepository.findById(id);
        return capacity.map(Capacity::toDTO);
    }

    /**
     * Creates a new capacity.
     * @param capacityDTO CapacityDTO object containing capacity data.
     * @return CapacityDTO of the created capacity.
     */
    public CapacityDTO createCapacity(CapacityDTO capacityDTO) {
        Capacity capacity = new Capacity();
        mapDTOToCapacity(capacityDTO, capacity);
        return capacityRepository.save(capacity).toDTO();
    }

    /**
     * Updates an existing capacity by its ID.
     * @param id ID of the capacity to update.
     * @param capacityDTO CapacityDTO object containing updated capacity data.
     * @return CapacityDTO of the updated capacity if found, otherwise null.
     */
    public CapacityDTO updateCapacity(Long id, CapacityDTO capacityDTO) {
        Optional<Capacity> optionalCapacity = capacityRepository.findById(id);
        if (optionalCapacity.isPresent()) {
            Capacity capacity = optionalCapacity.get();
            mapDTOToCapacity(capacityDTO, capacity);
            return capacityRepository.save(capacity).toDTO();
        }
        return null; // Handle case where capacity with the given id is not found
    }
    /**
     * Deletes a capacity by its ID.
     * @param id ID of the capacity to delete.
     */
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
