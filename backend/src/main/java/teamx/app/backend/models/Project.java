package teamx.app.backend.models;

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

    @ManyToOne
    @JoinColumn(name = "team_id", nullable = false)
    private Team team;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<ProductLine> productLines;
}
