package teamx.app.backend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

/**
 * Project entity
 * Represents a project
 *
 * @author Junior Javier Brito Perez
 * @author Nizar Amine
 * @see ProductLine
 * @see Team
 */
@Data
@Entity
@Table(name = "Projects")
@NoArgsConstructor
@AllArgsConstructor
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String status;
    private String name;
    private String description;

    /*private int team_id;
    private String team_name;*/

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "team_id", nullable = false)
    //@JsonManagedReference
    private Team team;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<ProductLine> productLines;
}
