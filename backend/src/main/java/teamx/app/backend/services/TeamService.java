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

    public List<Team> getAll() {
        return teamRepository.findAll();
    }

    public Team getById(Long id) {
        return teamRepository.findById(id).orElse(null);
    }

    public Team add(Team team) {
        if (team.getId() != null && teamRepository.existsById(team.getId())) {
            return null;
        }
        log.info("Adding Team: " + team.getName() + team.getMembers() + team.getLeader() + team.getWarehouse());
        log.info("Adding team members: " + team.getMembers());
        return teamRepository.save(team);
    }

    public Team update(Team team, Long id) {
        log.error("Updating Team: " + team.getName() + team.getMembers() + team.getLeader() + team.getWarehouse());
        Team existingTeam = teamRepository.findById(id).orElse(null);
        log.info("Updating Team: " + team.getName() + team.getMembers() + team.getLeader() + team.getWarehouse());
        log.info("Updating team members: " + team.getMembers());
        if (existingTeam == null || !team.getId().equals(id)) {
            return null;
        }

        existingTeam.setName(team.getName());
        existingTeam.setWarehouse(team.getWarehouse());
        existingTeam.setLeader(team.getLeader());
        existingTeam.setMembers(team.getMembers());
        return teamRepository.save(existingTeam);
    }

    public Team delete(Long id) {
        Team existingTeam = teamRepository.findById(id).orElse(null);
        if (existingTeam == null) {
            return null;
        }
        teamRepository.deleteById(id);
        return existingTeam;
    }

    public List<Team> getAllByWarehouseId(Long warehouseId) {
        return teamRepository.getAllByWarehouse_Id(warehouseId);
    }

    public TeamDTO convertToDTO(Team team) {
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

    public Team convertToEntity(TeamDTO teamDTO) {
        log.error("TeamDTO to convert to entity: " + teamDTO);
        if (teamDTO == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "TeamDTO not found is null");
        }
        Team team = new Team();
        team.setId(teamDTO.getId());
        team.setName(teamDTO.getName());
        team.setWarehouse(warehouseService.getById(teamDTO.getWarehouseId()));
        team.setLeader(userService.findById(teamDTO.getLeaderId()));
        return team;
    }
}
