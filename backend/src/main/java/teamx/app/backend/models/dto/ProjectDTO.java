package teamx.app.backend.models.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ProjectDTO {
    private Long id;
    private String name;
    private String description;
    private String location;
    private String clientName;
    private String clientEmail;
    private String clientPhone;
    private Date startDate;
    private Date endDate;
    private String status;
    private Long teamId;
}
