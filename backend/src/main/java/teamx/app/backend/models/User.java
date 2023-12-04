package teamx.app.backend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

/**
 * User entity
 * Represents a user
 *
 * @author Junior Javier Brito Perez
 * @see Team
 */
@Data
@Entity(name = "Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToOne()
    private Team team;

    @Transient
    private Long teamId;

    @PostLoad
    void fillTransient() {
        if (team != null) {
            this.teamId = team.getId();
        }
    }

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

    public enum Role {
        ADMIN,
        USER
    }
}