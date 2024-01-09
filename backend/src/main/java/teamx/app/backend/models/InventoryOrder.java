package teamx.app.backend.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import teamx.app.backend.utils.DTO;
import teamx.app.backend.utils.DTO.OrderDTO;
import teamx.app.backend.utils.Model;

import java.sql.Date;
import java.util.List;

/**
 * InventoryOrder entity
 * Represents an inventoryOrder of products
 *
 * @author Junior Javier Brito Perez
 */
@Data
@Builder
@Entity(name = "InventoryOrders")
@Table(name = "INVENTORYORDERS")
@NoArgsConstructor
@AllArgsConstructor
public class InventoryOrder implements Model<OrderDTO> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;

    @Enumerated(EnumType.STRING)
    private Status status = Status.PENDING;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date orderDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date deliveryDate;

    @ManyToOne
    @JsonIgnore
    private Warehouse warehouse;

    @ManyToOne
    @JsonIgnore
    private User orderedBy;

    @OneToMany(mappedBy = "inventoryOrder", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Transaction> transactions;

    @Override
    public OrderDTO toDTO() {
        return OrderDTO.builder().id(id).products(transactions.stream().map(transaction -> new DTO.OrderLineDTO(transaction.getProduct().getId(), transaction.getQuantity())).toList()).description(description).orderDate(orderDate).deliveryDate(deliveryDate).warehouseId(warehouse.getId()).ProjectId(transactions.get(0).getProject() != null ? transactions.get(0).getProject().getId() : null).userId(orderedBy.getId()).build();
    }

    public enum Status {
        PENDING, DELIVERED, DELAYED, CANCELLED
    }
}
