package teamx.app.backend.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Entity(name = "Teams")
@NoArgsConstructor
@AllArgsConstructor
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToOne
    @JsonManagedReference
    private Warehouse warehouse;

    @OneToMany
    @JoinColumn(name = "team_id")
    @JsonManagedReference
    private List<User> members;

    @OneToMany
    @JsonBackReference
    private List<Project> projects;
}

