package teamx.app.backend.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import teamx.app.backend.models.User;

import java.util.Optional;

/**
 * Interface of repository of user
 * @see User
 *
 * @author Junior Javier Brito Perez
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}