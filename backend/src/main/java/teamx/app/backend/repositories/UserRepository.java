package teamx.app.backend.repositories;

import teamx.app.backend.models.User;

import java.util.List;

public interface UserRepository<T>{
    List<T> findAll();
}
