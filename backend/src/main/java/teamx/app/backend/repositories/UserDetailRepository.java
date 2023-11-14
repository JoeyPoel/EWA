package teamx.app.backend.repositories;

import java.util.List;

public interface UserDetailRepository<T>{
    List<T> findAll();
}
