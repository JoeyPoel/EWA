package teamx.app.backend.repositories;

import java.util.List;

/**
 * Model repository interface.
 *
 * @param <T> Model type.
 * @author Junior Javier Brito Perez
 */
public interface ModelRepository<T> {
    List<T> findAll();
    T findById(int id);
    T save(T t);
    T deleteById(int id);
}
