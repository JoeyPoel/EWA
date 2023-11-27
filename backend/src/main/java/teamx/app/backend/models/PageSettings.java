package teamx.app.backend.models;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
@Data
public class PageSettings {
    private int pageNumber;
    private int pageSize;
    private List<Order> sorts;

    public Sort buildSort() {
        Sort sort = Sort.unsorted();
        if (sorts == null) {
            return sort;
        }
        for (Order order : sorts) {

            sort = sort.and(Sort.by(Sort.Direction.fromString(order.getKey()), order.getOrder()));
        }
        return sort;
    }

    @Getter
    @AllArgsConstructor
    class Order {
        private String key;
        private String order;
    }
}
