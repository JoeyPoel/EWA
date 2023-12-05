package teamx.app.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import teamx.app.backend.models.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
