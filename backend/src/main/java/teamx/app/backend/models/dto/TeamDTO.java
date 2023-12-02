package teamx.app.backend.models.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import teamx.app.backend.models.Team;
import teamx.app.backend.models.User;

import java.util.List;

@Data
@NoArgsConstructor
public class TeamDTO {
    private Long Id;
    private Long warehouseId;
    private String name;
    private Long leaderId;
    private List<Long> membersIds;

    public TeamDTO(Team team) {
        this.Id = team.getId();
        this.warehouseId = team.getWarehouse() != null ? team.getWarehouse().getId() : null;
        this.name = team.getName();
        this.leaderId = team.getLeader() != null ? team.getLeader().getId() : null;
        this.membersIds = team.getMembers() != null ? team.getMembers().stream().map(User::getId).toList() : null;
    }
}
