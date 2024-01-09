package teamx.app.backend.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import teamx.app.backend.utils.DTO.UserDTO;
import teamx.app.backend.utils.Model;


/**
 * User entity
 * Represents a user
 *
 * @author Junior Javier Brito Perez
 * @see Team
 */
@Data
@Builder
@Entity(name = "Users")
@NoArgsConstructor
@AllArgsConstructor
public class User implements Model<UserDTO> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Name is required")
    private String name;

    @NotNull(message = "Email is required")
    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Role is required")
    private Role role;

    @ManyToOne()
    private Team team;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name=" + name +
                ", email=" + email +
                ", role=" + role +
                ", team=" + (team != null ? team.getId() : null) +
                '}';
    }

    @Override
    public UserDTO toDTO() {
        return UserDTO.builder()
                .id(id)
                .name(name)
                .email(email)
                .role(String.valueOf(role))
                .teamId(team != null ? team.getId() : null)
                .jwtToken(null)
                .build();
    }

    public UserDTO toDTO(String jwtToken) {
        return UserDTO.builder()
                .id(id)
                .name(name)
                .email(email)
                .role(String.valueOf(role))
                .teamId(team != null ? team.getId() : null)
                .jwtToken(jwtToken)
                .build();
    }

    public enum Role {
        ADMIN,
        USER
    }
}