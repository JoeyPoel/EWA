package teamx.app.backend.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import teamx.app.backend.models.Project;


/**
 * Interface of repository of project
 *
 * @author Nizar Amine
 * @see Project
 */
public interface ProjectRepository extends JpaRepository<Project, Long> {

}
