package teamx.app.backend.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Warehouse entity
 * Represents a warehouse
 *
 * @author Junior Javier Brito Perez
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Warehouses")
public class Warehouse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Warehouse name is required")
    private String name;

    @NotNull(message = "Warehouse location is required")
    private String location;

    @NotNull(message = "Warehouse address is required")
    private String address;

    @NotNull(message = "Warehouse postcode is required")
    @Pattern(regexp = "^[0-9]{4}[a-zA-Z]{2}$", message = "Postcode must be 4 digits followed by 2 letters")
    private String postcode;

    @NotNull(message = "Warehouse city is required")
    private String country;
    private String contactName;
    private String contactEmail;
    private String contactPhone;
}

