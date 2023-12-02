package teamx.app.backend.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import teamx.app.backend.models.Team;
import teamx.app.backend.models.User;
import teamx.app.backend.models.dto.TeamDTO;
import teamx.app.backend.repositories.TeamRepository;

import java.util.List;

@Service
@Slf4j
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
        if (team.getId() != null && teamRepository.existsById(team.getId())) {
            return null;
        }
        log.info("Adding Team: " + team.getName() + team.getMembers() + team.getLeader() + team.getWarehouse());
        log.info("Adding team members: " + team.getMembers());
        return teamRepository.save(team);
    }

    public TeamDTO addDTO(TeamDTO teamDTO) {
        log.info("Adding team members: ", teamDTO.getMembersIds());
        Team team = convertToEntity(teamDTO);
        log.info("Adding team members: ", team.getMembers());
        Team addedTeam = add(team);
        log.info("Added team members: ", addedTeam.getMembers());
        return convertToDTO(addedTeam);
    }

    protected Team update(Team team, Long id) {
        log.error("Updating Team: " + team.getName() + team.getMembers() + team.getLeader() + team.getWarehouse());
        Team existingTeam = teamRepository.findById(id).orElse(null);
        log.info("Updating Team: " + team.getName() + team.getMembers() + team.getLeader() + team.getWarehouse());
        log.info("Updating team members: " + team.getMembers());
        if (existingTeam == null || team == null || !team.getId().equals(id)) {
            return null;
        }

        existingTeam.setName(team.getName());
        existingTeam.setWarehouse(team.getWarehouse());
        existingTeam.setLeader(team.getLeader());
        existingTeam.setMembers(team.getMembers());
        return teamRepository.save(existingTeam);
    }

    public TeamDTO updateDTO(TeamDTO teamDTO, Long id) {
        log.error("TeamDTO: " + teamDTO);
        Team team = convertToEntity(teamDTO);
        log.error("Updating Team: " + team.getName() + team.getMembers() + team.getLeader() + team.getWarehouse());
        log.error("Updating team members: " + team.getMembers());
        Team updatedTeam = update(team, id);
        log.error("Updated team: " + updatedTeam);
        if (updatedTeam == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not update team");
        }
        return convertToDTO(updatedTeam);
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
        log.error("Team to convert to DTO: " + team);
        if (team == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Team not found is null");
        }
        TeamDTO teamDTO = new TeamDTO();
        teamDTO.setId(team.getId());
        teamDTO.setName(team.getName());
        teamDTO.setWarehouseId(team.getWarehouse().getId());
        teamDTO.setLeaderId(team.getLeader() != null ? team.getLeader().getId() : null);
        teamDTO.setMembersIds(team.getMembers().stream().map(User::getId).toList());
        log.error("Team to convert to DTO converted: " + teamDTO);
        return teamDTO;
    }

    private Team convertToEntity(TeamDTO teamDTO) {
        log.error("TeamDTO to convert to entity: " + teamDTO);
        if (teamDTO == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "TeamDTO not found is null");
        }
        return new Team(
                teamDTO.getId(),
                teamDTO.getName(),
                teamDTO.getWarehouseId() != null ? warehouseService.getById(teamDTO.getWarehouseId()) : null,
                teamDTO.getLeaderId() != null ? userService.findById(teamDTO.getLeaderId()) : null,
                teamDTO.getMembersIds() != null ? userService.getAllByIds(teamDTO.getMembersIds()) : null
        );
    }
}
