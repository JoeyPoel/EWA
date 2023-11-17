package teamx.app.backend.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

/**
 * Team Class
 *
 * @author Joey van der Poel
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Team {
    private int id;
    private String name;
    private int warehouseId;

    static public ArrayList<Team> generateRandomTeam(int amount){
        ArrayList<Team> warehouses = new ArrayList<>();
        for (int i = 0; i < amount; i++){
            Team team = new Team();
            team.setId(i);
            team.setName("teamName");
            team.setWarehouseId(i);
            warehouses.add(team);
        }
        return warehouses;
    }
}

