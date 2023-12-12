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

    public List<Team> findAll() {
        return teamRepository.findAll();
    }

    public Team findById(Long id) {
        return teamRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementException("Team not found with id " + id));
    }

    private Team save(Team team) {
        return teamRepository.save(team);
    }

    public Team add(TeamDTO teamDTO) {
        Team savedTeam = mapToEntity(teamDTO, new Team());
        userService.setTeam(teamDTO.getMembersIds(), savedTeam);
        return savedTeam;
    }

    public Team update(Long teamId, TeamDTO teamDTO) {
        Team existingTeam = findById(teamId);
        userService.setTeam(teamDTO.getMembersIds(), existingTeam);
        return save(mapToEntity(teamDTO, existingTeam));
    }

    public Team delete(Long id) {
        Team existingTeam = findById(id);
        userService.setTeam(existingTeam.getMembers().stream().map(User::getId).toList(), null);
        projectService.setTeam(existingTeam.getProjects(), null);
        teamRepository.deleteById(id);
        return existingTeam;
    }

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
