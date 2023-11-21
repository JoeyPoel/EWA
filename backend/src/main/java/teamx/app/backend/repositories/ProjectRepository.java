package teamx.app.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import teamx.app.backend.models.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
