package teamx.app.backend.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

/**
 * Project entity
 * Represents a project
 *
 * @author Junior Javier Brito Perez
 * @see ProductLine
 * @see Team
 */
@Data
@Table
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn
    @JsonBackReference
    private Team team;

    @OneToMany
    @JsonManagedReference
    private List<ProductLine> productLines;

    @OneToMany
    @JsonManagedReference
    private List<Task> tasks;
}
