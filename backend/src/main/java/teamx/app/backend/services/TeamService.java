package teamx.app.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import teamx.app.backend.models.Team;
import teamx.app.backend.models.dto.TeamDTO;
import teamx.app.backend.repositories.TeamRepository;

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
        return mapToDTO(teams);
    }

    protected Team findById(Long id) {
        return teamRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementException("Team not found with id " + id));
    }

    public TeamDTO findByIdDTO(Long id) {
        return mapToDTO(findById(id));
    }

    private Team save(Team team) {
        return teamRepository.save(team);
    }

    public TeamDTO add(TeamDTO teamDTO) {
        Team savedTeam = mapToEntity(teamDTO, new Team());
        userService.setTeamById(teamDTO.getMembersIds(), savedTeam);
        return mapToDTO(savedTeam);
    }

    public TeamDTO update(Long teamId, TeamDTO teamDTO)  {
        Team existingTeam = findById(teamId);
        userService.setTeamById(teamDTO.getMembersIds(), existingTeam);
        Team savedTeam = save(mapToEntity(teamDTO, existingTeam));
        return mapToDTO(savedTeam);
    }

    public TeamDTO delete(Long id) {
        Team existingTeam = findById(id);
        userService.setTeamByUser(existingTeam.getMembers(), null);
        projectService.setTeam(existingTeam.getProjects(), null);
        teamRepository.deleteById(id);
        return new TeamDTO(existingTeam);
    }

    public List<TeamDTO> findAllByWarehouseId(Long warehouseId) {
        List<Team> teams = teamRepository.getAllByWarehouse_Id(warehouseId);
        return mapToDTO(teams);
    }

    private Team mapToEntity(TeamDTO dto, Team entity) {
        entity.setName(dto.getName());
        if (dto.getWarehouseId() != null) {
            entity.setWarehouse(warehouseService.findById(dto.getWarehouseId()));
        }
        if (dto.getLeaderId() != null) {
            entity.setLeader(userService.findById(dto.getLeaderId()));
        }
        return save(entity);
    }

    private TeamDTO mapToDTO(Team entity) {
        return new TeamDTO(entity);
    }

    private List<TeamDTO> mapToDTO(List<Team> entities) {
        return entities.stream().map(TeamDTO::new).toList();
    }
}
