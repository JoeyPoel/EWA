package teamx.app.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import teamx.app.backend.models.Team;
import teamx.app.backend.models.User;
import teamx.app.backend.repositories.TeamRepository;
import teamx.app.backend.utils.DTO.TeamDTO;

import java.util.List;
import java.util.NoSuchElementException;

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

    public List<TeamDTO> findAll() {
        List<Team> teams = teamRepository.findAll();
        return teams.stream()
                .map(Team::toDTO)
                .toList();
    }

    protected Team findById(Long id) {
        return teamRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementException("Team not found with id " + id));
    }

    public TeamDTO findByIdDTO(Long id) {
        return findById(id).toDTO();
    }

    private Team save(Team team) {
        return teamRepository.save(team);
    }

    public TeamDTO add(TeamDTO teamDTO) {
        Team savedTeam = mapToEntity(teamDTO, new Team());
        userService.setTeam(teamDTO.getMembersIds(), savedTeam);
        return savedTeam.toDTO();
    }

    public TeamDTO update(Long teamId, TeamDTO teamDTO) {
        Team existingTeam = findById(teamId);
        userService.setTeam(teamDTO.getMembersIds(), existingTeam);
        Team savedTeam = save(mapToEntity(teamDTO, existingTeam));
        return savedTeam.toDTO();
    }

    public TeamDTO delete(Long id) {
        Team existingTeam = findById(id);
        userService.setTeam(existingTeam.getMembers().stream().map(User::getId).toList(), null);
        projectService.setTeam(existingTeam.getProjects(), null);
        teamRepository.deleteById(id);
        return existingTeam.toDTO();
    }

    public List<TeamDTO> findAllByWarehouseId(Long warehouseId) {
        List<Team> teams = teamRepository.getAllByWarehouse_Id(warehouseId);
        return teams.stream().map(Team::toDTO).toList();
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
