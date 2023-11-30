package teamx.app.backend.models.dto;

import lombok.Data;

import java.sql.Date;

@Data
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
