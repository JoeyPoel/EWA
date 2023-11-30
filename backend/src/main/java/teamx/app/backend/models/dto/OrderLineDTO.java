package teamx.app.backend.models.dto;

import lombok.Data;

@Data
public class OrderLineDTO {
    private Long productId;
    private int quantity;
}
