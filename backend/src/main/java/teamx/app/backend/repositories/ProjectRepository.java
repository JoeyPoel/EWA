package teamx.app.backend.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import teamx.app.backend.models.Project;

import java.sql.Date;
import java.util.Collection;
import java.util.List;


/**
 * Interface of repository of project
 *
 * @author Nizar Amine
 * @see Project
 */
public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> getAllByTeam_Warehouse_Id(Long warehouseId);

    List<Project> findAllByStatusAndTeam_Warehouse_IdAndEndDateBetween(Project.Status status, Long warehouseId,
                                                                       Date startDate, Date endDate);

    List<Project> findAllByStatusAndEndDateBetween(Project.Status status, Date startDate, Date endDate);

    List<Project> findAllByTeam_Warehouse_IdAndEndDateBetween(Long warehouseId, Date startDate, Date endDate);

    List<Project> findAllByEndDateBetween(Date startDate, Date endDate);
}
