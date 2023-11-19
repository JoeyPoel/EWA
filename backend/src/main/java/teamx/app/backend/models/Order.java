package teamx.app.backend.models;

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
    @JoinColumn(name = "warehouse_id", nullable = false)
    private Warehouse warehouse;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User orderedBy;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<ProductLine> orderLines;
}
