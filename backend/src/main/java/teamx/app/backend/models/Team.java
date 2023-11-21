package teamx.app.backend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Team entity
 * Represents a team
 *
 * @author Joey van der Poel
 * @author Junior Javier Brito Perez
 * @see User
 */
@Data
@Entity
@Table(name = "Teams")
@NoArgsConstructor
@AllArgsConstructor
//@JsonIgnoreProperties({"warehouse"})
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToOne
    @JsonIgnore
    private Warehouse warehouse;

    @JsonManagedReference
    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL)
    private List<User> members;
}

