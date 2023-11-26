package teamx.app.backend.models;

import lombok.Data;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;

@Data
@Slf4j
@Getter
public class PageSettings {
    private int page;
    private int elementsPerPage;
    private String direction;
    private String key;

    public Sort buildSort() {
        Sort.Direction sortDirection = Sort.Direction.ASC;
        if (direction.equals("desc")) {
            sortDirection = Sort.Direction.DESC;
        }
        log.info("Sort direction: " + sortDirection);
        log.info("Sort key: " + key);
        return Sort.by(sortDirection, key);
    }
}
