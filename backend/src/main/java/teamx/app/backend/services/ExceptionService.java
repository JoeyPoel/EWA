package teamx.app.backend.services;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.function.Supplier;

@Service
public class ExceptionService {
    public <T> ResponseEntity<T> handle(Supplier<T> supplier) {
        try {
            return ResponseEntity.ok(supplier.get());
        } catch (ResponseStatusException e) {
            throw e;
        } catch (IllegalArgumentException | IllegalStateException | NullPointerException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
