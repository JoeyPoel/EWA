package teamx.app.backend.repositories;

import org.springframework.stereotype.Repository;
import teamx.app.backend.models.Team;

import java.util.ArrayList;
import java.util.List;

@Repository
public class TeamRepositoryMock implements TeamRepository {

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


//    @Override
//    public void save(Team team) {
//        teams.add(team);
//    }

    @Override
    public void put(int id, String name, int warehouseId) {
        for (int i = 0; i < teams.size(); i++) {
            if(teams.get(i).getId() == id){
                Team newTeam = new Team(id, name, warehouseId);
                teams.set(i, newTeam);
            }
        }
    }

    @Override
    public void delete(int index) {
        teams.remove(index);
    }

    @Override
    public Object findById(int id) {
        for (int i = 0; i < teams.size(); i++) {
            if(teams.get(i).getId() == id){
                return teams.get(i);
            }
        }
        return null;
    }

    @Override
    public Object deleteById(int id) {
        for (int i = 0; i < teams.size(); i++) {
            if(teams.get(i).getId() == id){
                return teams.remove(teams.get(i));
            }
        }
        return null;
    }
}
