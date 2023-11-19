package teamx.app.backend.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Order entity
 * Represents an order of products
 *
 * @author Junior Javier Brito Perez
 * @see ProductLine
 */
@Data
@Entity
@Table(name = "Orders")
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;

    @ColumnDefault("false")
    private boolean isDelivered;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime orderDate;

    @ManyToOne
    @JoinColumn
    @JsonManagedReference
    private Warehouse warehouse;

    @ManyToOne
    @JoinColumn
    @JsonManagedReference
    private User orderedBy;

    @OneToMany
    @JsonManagedReference
    private List<ProductLine> orderLines;
}
