package teamx.app.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import teamx.app.backend.models.Team;
import teamx.app.backend.repositories.TeamRepository;

import java.util.List;

@Service
public class TeamService {
    private final TeamRepository teamRepository;

    @Autowired
    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public List<Team> getAll() {
        List<Team> teams = teamRepository.findAll();
        if (teams.isEmpty()) {
            return null;
        }
        return teams;
    }

    public Team getTeamById(Long id) {
        return teamRepository.findById(id).orElse(null);
    }

    public Team addTeam(Team team) {
        return teamRepository.save(team);
    }

    // TODO: Throw exception if team is null or id is null or team.id != id
    public Team updateTeam(Team team, Long id) {
        Team existingTeam = teamRepository.findById(id).orElse(null);
        if (existingTeam == null || team == null || !team.getId().equals(id)) {
            return null;
        }
        existingTeam.setName(team.getName());
        existingTeam.setWarehouse(team.getWarehouse());
        return teamRepository.save(existingTeam);
    }

    public Team deleteTeam(Long id) {
        Team existingTeam = teamRepository.findById(id).orElse(null);
        if (existingTeam == null) {
            return null;
        }
        teamRepository.deleteById(id);
        return existingTeam;
    }
}
