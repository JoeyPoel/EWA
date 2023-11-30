package teamx.app.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import teamx.app.backend.models.Team;
import teamx.app.backend.models.User;
import teamx.app.backend.models.dto.TeamDTO;
import teamx.app.backend.repositories.TeamRepository;

import java.util.List;

@Service
public class TeamService {
    private final TeamRepository teamRepository;
    private final WarehouseService warehouseService;
    private final UserService userService;

    @Autowired
    public TeamService(TeamRepository teamRepository, WarehouseService warehouseService, UserService userService) {
        this.teamRepository = teamRepository;
        this.warehouseService = warehouseService;
        this.userService = userService;
    }

    protected List<Team> getAll() {
        return teamRepository.findAll();
    }

    public List<TeamDTO> getAllDTO() {
        return getAll().stream().map(this::convertToDTO).toList();
    }

    protected Team getById(Long id) {
        return teamRepository.findById(id).orElse(null);
    }

    public TeamDTO getByIdDTO(Long id) {
        Team team = teamRepository.findById(id).orElse(null);
        if (team == null) {
            return null;
        }
        return convertToDTO(team);
    }

    protected Team add(Team team) {
        return teamRepository.save(team);
    }

    public TeamDTO addDTO(TeamDTO teamDTO) {
        return convertToDTO(add(convertToEntity(teamDTO)));
    }

    protected Team updateDTO(Team team, Long id) {
        Team existingTeam = teamRepository.findById(id).orElse(null);
        if (existingTeam == null || team == null || !team.getId().equals(id)) {
            return null;
        }
        existingTeam.setName(team.getName());
        existingTeam.setWarehouse(team.getWarehouse());
        return teamRepository.save(existingTeam);
    }

    public TeamDTO updateDTO(TeamDTO teamDTO, Long id) {
        return convertToDTO(updateDTO(convertToEntity(teamDTO), id));
    }

    protected Team delete(Long id) {
        Team existingTeam = teamRepository.findById(id).orElse(null);
        if (existingTeam == null) {
            return null;
        }
        teamRepository.deleteById(id);
        return existingTeam;
    }

    public TeamDTO deleteDTO(Long id) {
        return convertToDTO(delete(id));
    }

    protected List<Team> getAllByWarehouseId(Long warehouseId) {
        return teamRepository.getAllByWarehouse_Id(warehouseId);
    }

    public List<TeamDTO> getAllByWarehouseIdDTO(Long warehouseId) {
        return getAllByWarehouseId(warehouseId).stream().map(this::convertToDTO).toList();
    }

    private TeamDTO convertToDTO(Team team) {
        TeamDTO teamDTO = new TeamDTO();
        teamDTO.setId(team.getId());
        teamDTO.setName(team.getName());
        teamDTO.setWarehouseId(team.getWarehouse().getId());
        teamDTO.setLeaderId(team.getLeader().getId() != null ? team.getLeader().getId() : null);
        teamDTO.setMembersIds(team.getMembers().stream().map(User::getId).toArray(Long[]::new));
        return teamDTO;
    }

    private Team convertToEntity(TeamDTO teamDTO) {
        Team team = new Team();
        team.setId(teamDTO.getId());
        team.setName(teamDTO.getName());
        team.setWarehouse(warehouseService.getById(teamDTO.getWarehouseId()));
        team.setLeader(userService.findById(teamDTO.getLeaderId()).orElse(null));
        team.setMembers(userService.getAllByIds(teamDTO.getMembersIds()));
        return team;
    }
}
