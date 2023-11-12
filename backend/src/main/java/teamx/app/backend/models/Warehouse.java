package teamx.app.backend.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

/**
 * This class is a model for the Warehouse.
 *
 * @author Junior Javier Brito Perez
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Warehouse {
    @Id
    private int id;
    private String name;
    private String Location;
    private String address;
    private String postcode;
    private String country;
    private String contactName;
    private String contactEmail;
    private String contactPhone;

    static public ArrayList<Warehouse> generateRandomWarehouses(int amount){
        ArrayList<Warehouse> warehouses = new ArrayList<>();
        for (int i = 0; i < amount; i++){
            Warehouse warehouse = new Warehouse();
            warehouse.setId(i);
            warehouse.setName("warehouse" + i + " name");
            warehouse.setLocation("warehouse" + i + " location");
            warehouse.setAddress("warehouse" + i + " Address");
            warehouse.setCountry("warehouse" + i + " Country");
            warehouse.setPostcode("warehouse" + i + " Postcode");
            warehouse.setContactName("warehouse" + i + " ContactName");
            warehouse.setContactEmail("warehouse" + i + " ContactEmail");
            warehouse.setContactPhone("warehouse" + i + " ContactPhone");
            warehouses.add(warehouse);
        }
        return warehouses;
    }
}

