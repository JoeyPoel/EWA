package teamx.app.backend.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Project entity
 * Represents a project
 *
 * @author Junior Javier Brito Perez
 * @see Team
 */
@Data
@Entity(name = "Projects")
@NoArgsConstructor
@AllArgsConstructor
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonManagedReference
    private Team team;

    @OneToMany
    @JsonBackReference
    private List<Task> tasks;

    @OneToMany
    @JsonBackReference
    private List<Transaction> materials;
}
