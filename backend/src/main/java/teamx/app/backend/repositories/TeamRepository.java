package teamx.app.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import teamx.app.backend.models.Team;

import java.util.List;

/**
 * Interface of repository of team
 *
 * @author Junior Javier Brito Perez
 * @see Team
 */
public interface TeamRepository extends JpaRepository<Team, Long> {
    List<Team> getAllByWarehouse_Id(Long warehouseId);
}
