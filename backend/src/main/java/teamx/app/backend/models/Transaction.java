package teamx.app.backend.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Transaction entity
 * Represents a transaction
 *
 * @author Junior Javier Brito Perez
 * @see Product
 * @see Warehouse
 */
@Data
@Entity
@Table(name = "Transactions")
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int amount;

    @ManyToOne
    @JoinColumn
    private Product product;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime transactionDate;

    @ManyToOne
    @JoinColumn
    private Warehouse warehouse;

    @ManyToOne
    @JoinColumn
    private Project project;

    @ManyToOne
    @JoinColumn
    private Warehouse warehouseFrom;
}
