package teamx.app.backend.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Warehouse entity
 * Represents a warehouse
 *
 * @author Junior Javier Brito Perez
 */
@Data
@Entity(name = "Warehouses")
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
}

