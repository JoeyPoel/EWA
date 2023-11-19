package teamx.app.backend.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;

/**
 * Order entity
 * Represents an order of products
 *
 * @author Junior Javier Brito Perez
 */
@Data
@Entity(name = "Orders")
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private boolean isDelivered = false;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date orderDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date deliveryDate;

    @ManyToOne
    @JsonManagedReference
    private Warehouse warehouse;

    @ManyToOne
    @JsonManagedReference
    private User orderedBy;

    @OneToMany(mappedBy = "order")
    @JsonManagedReference
    private List<Transaction> transactions;
}
