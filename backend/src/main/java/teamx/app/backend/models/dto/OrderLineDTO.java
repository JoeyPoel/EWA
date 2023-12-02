package teamx.app.backend.models.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderLineDTO {
    private Long productId;
    private int quantity;
}
