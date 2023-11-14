package teamx.app.backend.repositories;

import org.springframework.stereotype.Repository;
import teamx.app.backend.models.Team;

import java.util.ArrayList;
import java.util.List;

@Repository
public class TeamRepositoryMock implements ModelRepository<Team> {

    private List<Team> teams = new ArrayList<>();

    public TeamRepositoryMock(){
        for (int i = 0; i < 4; i++) {
            teams.add(new Team(i, "teamName",i));
        }
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


    public void put(int id, String name, int warehouseId) {
        for (int i = 0; i < teams.size(); i++) {
            if(teams.get(i).getId() == id){
                Team newTeam = new Team(id, name, warehouseId);
                teams.set(i, newTeam);
            }
        }
    }

//    @Override
//    public Team delete(int index) {
//        teams.remove(index);
//    }

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
