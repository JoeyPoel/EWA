package teamx.app.backend.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;

/**
 * Project entity
 * Represents a project
 * @author Nizar Amine
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

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date startDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date endDate;

    @Enumerated(EnumType.STRING)
    private Status status;
    public enum Status {
        IN_PROGRESS,
        FINISHED
    }

    @ManyToOne
    @JsonIgnore
    private Team team;


    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Task> tasks;

    @OneToMany(mappedBy = "project",  cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Transaction> materials;
}
