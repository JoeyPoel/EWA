package teamx.app.backend.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is a model for the Warehouse.
 *
 * @author Junior Javier Brito Perez
 */
@Entity
@Table(name = "Warehouses")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Warehouse {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String location;
    private String address;
    private String postcode;
    private String country;
    private String contactName;
    private String contactEmail;
    private String contactPhone;

    static public ArrayList<Warehouse> generateRandomWarehouses(int amount) {
        ArrayList<Warehouse> warehouses = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            Warehouse warehouse = new Warehouse();
            warehouse.setId((long) i);
            warehouse.setName(mockWarehouseNames().get(i));
            warehouse.setAddress(mockWarehouseAddresses().get(i));
            warehouse.setLocation(locations().get(i));
            warehouse.setCountry("The Netherlands");
            warehouse.setPostcode(mockWarehousePostcodes().get(i));
            warehouse.setContactName(mockWarehouseContactNames().get(i));
            warehouse.setContactEmail(mockWarehouseContactEmails().get(i));
            warehouse.setContactPhone(mockWarehouseContactPhones().get(i));
            warehouses.add(warehouse);
        }
        return warehouses;
    }

    private static List<String> mockWarehouseNames() {

        return List.of("Solar warehouse", "Wind warehouse", "Water warehouse", "Nuclear warehouse", "Coal warehouse", "Gas warehouse", "Oil warehouse", "Biomass warehouse", "Hydrogen warehouse");
    }

    private static List<String> locations() {
        return List.of("Amsterdam", "Rotterdam", "Den Haag", "Utrecht", "Eindhoven", "Tilburg", "Groningen", "Almere");
    }

    private static List<String> mockWarehouseAddresses() {
        return List.of("Amsterdamseweg 1", "Rotterdamseweg 2", "Den Haagseweg 3", "Utrechtseweg 4", "Eindhovenseweg 5", "Tilburgseweg 6", "Groningseweg 7", "Almereweg 8");
    }

    private static List<String> mockWarehousePostcodes() {
        return List.of("1111AA", "2222BB", "3333CC", "4444DD", "5555EE", "6666FF", "7777GG", "8888HH");
    }

    private static List<String> mockWarehouseContactNames() {
        return List.of("John Doe", "Jane Doe", "John Smith", "Jane Smith", "John Johnson", "Jane Johnson", "John Williams", "Jane Williams");
    }

    private static List<String> mockWarehouseContactEmails() {
        return List.of("jane@gmail.com", "john@gmail.com", "smith@gmail.com", "doe@gmail.com", "johnson@gmail.com", "williams@gmail.com");
    }

    private static List<String> mockWarehouseContactPhones() {
        return List.of("0612345678", "0612345679", "0612345670", "0612345671", "0612345672", "0612345673", "0612345674", "0612345675");
    }
}

