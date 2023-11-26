package teamx.app.backend.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDTO {
    Long id;
    int quantity;
    String transactionType;
    Date transactionDate;
    Long productId;
    Long warehouseId;
    Long transferFromWarehouseId;
    Long ProjectId;
}
