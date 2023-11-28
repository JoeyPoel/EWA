package teamx.app.backend.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamDTO {
    private Long Id;
    private Long warehouseId;
    private String name;
    private Long leaderId;
    private Long[] membersIds;
}
