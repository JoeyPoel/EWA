package teamx.app.backend.rest;

import org.springframework.http.ResponseEntity;

/**
 * Controller interface.
 *
 * @author Junior Javier Brito Perez
 */
public interface Controller<T> {
    ResponseEntity<?> getAll();
    ResponseEntity<?> getById(int id);
    ResponseEntity<?> add(T t);
    ResponseEntity<?> update(int id, T t);
    ResponseEntity<?> delete(int id);
}

