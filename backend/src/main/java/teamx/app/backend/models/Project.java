package teamx.app.backend.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
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
    private String name;
    private String description;
    private String location;
    private String clientName;
    private String clientEmail;
    private String clientPhone;
    private Date startDate;
    private Date endDate;

    @Enumerated(EnumType.STRING)
    private Status status;
    enum Status {
        PENDING,
        CONFIRMED,
        IN_PROGRESS,
        FINISHED,
        CANCELED
    }

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
