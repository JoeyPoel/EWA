package teamx.app.backend.models.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import teamx.app.backend.models.Project;

import java.sql.Date;

@Data
@NoArgsConstructor
public class ProjectDTO {
    private long id;
    private String name;
    private String description;
    private String location;
    private String clientName;
    private String clientEmail;
    private String clientPhone;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date endDate;
    private String status;
    private Long teamId;

    public ProjectDTO(Project project){
        this.id = project.getId();
        this.name = project.getName();
        this.description = project.getDescription();
        this.location = project.getLocation();
        this.clientName = project.getClientName();
        this.clientEmail = project.getClientEmail();
        this.clientPhone = project.getClientPhone();
        this.startDate = project.getStartDate();
        this.endDate = project.getEndDate();
        this.status = project.getStatus() != null ? project.getStatus().name() : null;
        this.teamId = project.getTeam() != null ? project.getTeam().getId() : null;
    }
}
