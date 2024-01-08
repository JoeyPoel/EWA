package teamx.app.backend.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import teamx.app.backend.utils.DTO.ProjectDTO;
import teamx.app.backend.utils.Model;

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
@Builder
@Entity(name = "Projects")
@NoArgsConstructor
@AllArgsConstructor
public class Project implements Model<ProjectDTO> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = "Name is required")
    private String name;
    @NotNull(message = "Description is required")
    private String description;
    private String location;
    private String clientName;
    private String clientEmail;
    private String clientPhone;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @NotNull(message = "Start date is required")
    private Date startDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @NotNull(message = "End date is required")
    private Date endDate;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Status is required")
    private Status status;

    @Override
    public ProjectDTO toDTO() {
        return ProjectDTO.builder()
                .id(id)
                .name(name)
                .description(description)
                .location(location)
                .clientName(clientName)
                .clientEmail(clientEmail)
                .clientPhone(clientPhone)
                .startDate(startDate)
                .endDate(endDate)
                .status(String.valueOf(status))
                .teamId(team != null ? team.getId() : null)
                .build();
    }

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
