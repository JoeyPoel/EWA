package teamx.app.backend.models.dto;

import lombok.Data;

@Data
public class OrderLineDTO {
    Long productId;
    int quantity;
}
