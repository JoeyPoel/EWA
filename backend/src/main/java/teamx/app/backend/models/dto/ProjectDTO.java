package teamx.app.backend.models.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.sql.Date;

@Data
public class ProjectDTO {
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
    private String status;
    private Long teamId;
}
