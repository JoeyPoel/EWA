package teamx.app.backend.models.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class InventoryProjectDTO {

    // Product Fields
    private Long id;
    private int quantity;
    private String ProductName;
    private String warehouseName;
    private String transactionType;
    private Date transactionDate;

    // Task Fields
    private String taskName;
    private String taskDescription;
    private String taskDeadline;
    private String taskStatus;
    private Long userId;
}
