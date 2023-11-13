package teamx.app.backend.repositories;

import teamx.app.backend.models.Team;

import java.util.List;
/**
 *
 *
 * @author Joey van der Poel
 */
public interface TeamRepository<Team> {
    List<Team> findAll();

//    void save(Team team);

    void put(int id, String name, int warehouseId);

    void delete(int index);

    Team findById(int id);

    Team deleteById(int id);
}
