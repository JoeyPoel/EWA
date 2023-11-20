package teamx.app.backend.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    enum Type {
        ORDER, PROJECT_MATERIAL, TRANSFER, ADJUSTMENT, RETURN, DAMAGED, LOST, EXTRA_MATERIAL_FOR_PROJECT, OTHER
    }

    @ManyToOne
    @JsonManagedReference
    private Product product;

    @JsonIgnore
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date transactionDate;

    @ManyToOne
    @JsonBackReference
    private Warehouse warehouse;

    @ManyToOne
    @JsonBackReference
    private Project project;

    @ManyToOne
    @JsonManagedReference
    @JsonBackReference
    private Warehouse transferFrom;

    @ManyToOne
    @JsonBackReference
    private Order order;
}
