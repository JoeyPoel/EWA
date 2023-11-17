package teamx.app.backend.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

/**
 * Team Class
 *
 * @author Joey van der Poel
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Team {
    @Id
    private int id;
    private String name;
    private int warehouseId;
    private ArrayList users;
}

