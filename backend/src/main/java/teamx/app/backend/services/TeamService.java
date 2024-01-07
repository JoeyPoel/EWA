package teamx.app.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import teamx.app.backend.models.Team;
import teamx.app.backend.models.User;
import teamx.app.backend.repositories.TeamRepository;
import teamx.app.backend.utils.DTO.TeamDTO;

import java.util.List;
import java.util.NoSuchElementException;
/**
 * Service handling operations related to Team entities.
 * Manages CRUD operations for Team entities, including retrieval, addition, updating, and deletion.
 *
 * @author Joey van der poel
 * @author Junior Javier Brito Perez
 */
@Service
public class TeamService {
    private final TeamRepository teamRepository;
    private final WarehouseService warehouseService;
    private final UserService userService;

    private final ProjectService projectService;

    @Autowired
    public TeamService(TeamRepository teamRepository, WarehouseService warehouseService, UserService userService,
                       ProjectService projectService) {
        this.teamRepository = teamRepository;
        this.warehouseService = warehouseService;
        this.userService = userService;
        this.projectService = projectService;
    }

    /**
     * Retrieves all teams.
     *
     * @return List of all Team entities.
     */
    public List<Team> findAll() {
        return teamRepository.findAll();
    }

    /**
     * Finds a team by its ID.
     *
     * @param id The ID of the team to retrieve.
     * @return The found Team entity.
     * @throws NoSuchElementException if no team is found with the given ID.
     */
    public Team findById(Long id) {
        return teamRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementException("Team not found with id " + id));
    }

    /**
     * Persists a team entity.
     *
     * @param team The team entity to be saved.
     * @return The saved Team entity.
     */
    private Team save(Team team) {
        return teamRepository.save(team);
    }

    /**
     * Creates and adds a new Team entity based on the provided TeamDTO.
     *
     * @param teamDTO The TeamDTO object containing details for the new team.
     * @return The newly created Team entity.
     */
    public Team add(TeamDTO teamDTO) {
        Team savedTeam = mapToEntity(teamDTO, new Team());
        userService.setTeam(teamDTO.getMembersIds(), savedTeam);
        return savedTeam;
    }

    /**
     * Updates an existing Team entity with the provided TeamDTO details.
     *
     * @param teamId   The ID of the team to be updated.
     * @param teamDTO  The TeamDTO object containing updated team details.
     * @return The updated Team entity.
     */
    public Team update(Long teamId, TeamDTO teamDTO) {
        Team existingTeam = findById(teamId);
        userService.setTeam(teamDTO.getMembersIds(), existingTeam);
        return save(mapToEntity(teamDTO, existingTeam));
    }


    /**
     * Deletes a team by its ID.
     *
     * @param id The ID of the team to delete.
     * @return The deleted Team entity.
     * @throws NoSuchElementException if no team is found with the given ID.
     */
    public Team delete(Long id) {
        Team existingTeam = findById(id);
//        userService.setTeam(existingTeam.getMembers().stream().map(User::getId).toList(), null);
//        projectService.setTeam(existingTeam.getProjects(), null);
        teamRepository.deleteById(id);
        return existingTeam;
    }

    /**
     * Retrieves all teams associated with a specific warehouse.
     *
     * @param warehouseId The ID of the warehouse to retrieve teams for.
     * @return List of Team entities associated with the given warehouse ID.
     */
    public List<Team> findAllByWarehouseId(Long warehouseId) {
        return teamRepository.getAllByWarehouse_Id(warehouseId);
    }

    private Team mapToEntity(TeamDTO dto, Team entity) {
        entity.setName(dto.getName());
        if (dto.getWarehouseId() != null) {
            entity.setWarehouse(warehouseService.findById(dto.getWarehouseId()));
        }
        if (dto.getLeaderId() != null) {
            entity.setLeader(userService.getById(dto.getLeaderId()));
        }
        return save(entity);
    }
}
