package teamx.app.backend.repositories;

import org.springframework.stereotype.Repository;
import teamx.app.backend.models.Team;

import java.util.ArrayList;
import java.util.List;

@Repository
public class TeamRepositoryMock implements ModelRepository<Team> {

    private List<Team> teams = new ArrayList<>();

    public TeamRepositoryMock(){
        teams.add(new Team(1, "Team 1",1));
        teams.add(new Team(2, "Team 2",2));
        teams.add(new Team(3, "Team 3",3));
        teams.add(new Team(4, "Team 4",4));
    }


    @Override
    public List<Team> findAll() {
        return this.teams;
    }


    @Override
    public Team save(Team team) {
        teams.add(team);
        return team;
    }


    public Team put(int id, Team team) {
        for (int i = 0; i < teams.size(); i++) {
            if(teams.get(i).getId() == id){
                Team newTeam = new Team(id, team.getName(), team.getWarehouseId());
                teams.set(i, newTeam);
                return newTeam;
            }
        }
        return null;
    }

    @Override
    public Team findById(int id) {
        for (int i = 0; i < teams.size(); i++) {
            if(teams.get(i).getId() == id){
                return teams.get(i);
            }
        }
        return null;
    }

    @Override
    public Team deleteById(int id) {
        for (int i = 0; i < teams.size(); i++) {
            if(teams.get(i).getId() == id){
                Team removedTeam = teams.get(i);
                teams.remove(teams.get(i));
                return removedTeam;
            }
        }
        return null;
    }
}
