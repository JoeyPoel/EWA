package teamx.app.backend.models.dto;

import lombok.Data;

@Data
public class TeamDTO {
    private Long Id;
    private Long warehouseId;
    private String name;
    private Long leaderId;
    private Long[] membersIds;
}
