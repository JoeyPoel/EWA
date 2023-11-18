package teamx.app.backend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;


/**
 * Warehouse entity
 * Represents a warehouse
 *
 * @author Junior Javier Brito Perez
 */
@Data
@Entity
@Table(name = "Warehouses")
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

    @JsonIgnore
    @OneToMany(mappedBy = "warehouse", cascade = CascadeType.ALL)
    private List<Team> teams;
}

