package teamx.app.backend.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryProjectDTO {

    private Long id;
    private int quantity;
    private String ProductName;
    private String warehouseName;
    private String transactionType;
    private Date transactionDate;
}
