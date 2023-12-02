package teamx.app.backend.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

/**
 * Transaction entity
 * Represents a transaction
 *
 * @author Junior Javier Brito Perez
 * @see Product
 * @see Warehouse
 */
@Data
@Entity(name = "Transactions")
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int quantity;

    @Enumerated(EnumType.STRING)
    private Type transactionType;
    @ManyToOne
    @JsonIgnore
    private Product product;
    @JsonIgnore
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date transactionDate;
    @ManyToOne
    @JsonIgnore
    private Warehouse warehouse;
    @ManyToOne
    @JsonIgnore
    private Project project;
    @ManyToOne
    @JsonIgnore
    private Warehouse transferFrom;
    @ManyToOne
    @JsonIgnore
    private Order order;


    public boolean isPositiveTransaction() {
        return this.getTransactionType() == Type.ORDER ||
                (this.getTransactionType() == Type.ADJUSTMENT ||
                        this.getTransactionType() == Type.RETURN ||
                        this.getTransactionType() == Type.OTHER)
                        && this.getQuantity() > 0;
    }

    public enum Type {
        ORDER, PROJECT_MATERIAL, TRANSFER, ADJUSTMENT, RETURN, DAMAGED, LOST, EXTRA_MATERIAL_FOR_PROJECT, OTHER
    }
}
