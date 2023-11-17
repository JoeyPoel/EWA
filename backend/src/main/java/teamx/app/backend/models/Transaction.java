package teamx.app.backend.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    private int id;
    private int productId;
    private int amount;
    private LocalDateTime transactionDate;

    private enum type {ORDER, RETURN, TRANSFER, ADJUSTMENT}

    public class Order extends Transaction {
        private int orderId;
        private Warehouse warehouse;
    }

    public class Return extends Transaction {
        private int orderId;
        private Warehouse warehouse;
    }

    public class Transfer extends Transaction {
        private int orderId;
        private Warehouse warehouseFrom;
        private Warehouse warehouseTo;
    }

    public class Adjustment extends Transaction {
        private int orderId;
        private Warehouse warehouse;
    }
}
