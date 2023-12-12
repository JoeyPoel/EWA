package teamx.app.backend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import teamx.app.backend.utils.DTO.TeamDTO;
import teamx.app.backend.utils.Model;

import java.util.List;

/**
 * Team entity
 * Represents a team
 *
 * @author Joey van der Poel
 * @author Junior Javier Brito Perez
 * @see User
 */
@Data
@Builder
@Entity(name = "Teams")
@NoArgsConstructor
@AllArgsConstructor
public class Team implements Model<TeamDTO> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToOne
    @JsonIgnore
    private Warehouse warehouse;

    // TODO: Can a team have multiple leaders?
    @ManyToOne
    @JsonIgnore
    private User leader;

    @JsonIgnore
    @OneToMany
    @JoinColumn(name = "team_id")
    private List<User> members;

    @JsonIgnore
    @OneToMany
    @JoinColumn(name = "team_id")
    private List<Project> projects;

    @Override
    public TeamDTO toDTO() {
        return TeamDTO.builder()
                .id(id)
                .name(name)
                .warehouseId(warehouse.getId())
                .leaderId(leader.getId())
                .membersIds(members.stream().map(User::getId).toList())
                .build();
    }
}

