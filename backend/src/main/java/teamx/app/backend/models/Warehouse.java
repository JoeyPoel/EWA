package teamx.app.backend.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


/**
 * Warehouse entity
 * Represents a warehouse
 *
 * @author Junior Javier Brito Perez
 */
@Data
@Table
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Warehouse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String location;
    private String address;
    private String postcode;
    private String country;
    private String contactName;
    private String contactEmail;
    private String contactPhone;

    @OneToMany
    @JsonBackReference
    private List<Team> teams;

    @OneToMany
    @JsonBackReference
    private List<Order> orders;

    @OneToMany
    @JsonBackReference
    private List<WarehouseProductCategoryCapacity> warehouseProductCategoryCapacities;

    @OneToMany
    @JsonBackReference
    private List<Transaction> transactions;
}

