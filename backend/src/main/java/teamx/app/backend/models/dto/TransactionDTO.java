package teamx.app.backend.models.dto;

import lombok.Data;
import teamx.app.backend.models.Transaction;

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
    private Long projectId;

    public TransactionDTO(Transaction transaction) {
        this.id = transaction.getId();
        this.productId = transaction.getProduct().getId();
        this.quantity = transaction.getQuantity();
        this.transactionDate = transaction.getTransactionDate();
        this.transactionType = transaction.getTransactionType().toString();
        this.warehouseId = transaction.getWarehouse() != null ? transaction.getWarehouse().getId() : null;
        this.transferFromWarehouseId = transaction.getTransferFrom() != null
                ? transaction.getTransferFrom().getId() : null;
        this.projectId = transaction.getProject() != null ? transaction.getProject().getId() : null;
    }
}
