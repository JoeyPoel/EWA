package teamx.app.backend.models.dto;

import lombok.Data;

import java.sql.Date;

@Data
public class TransactionDTO {
    private Long id;
    private int quantity;
    private String transactionType;
    private Date transactionDate;
    private Long productId;
    private Long warehouseId;
    private Long transferFromWarehouseId;
    private Long ProjectId;
}
