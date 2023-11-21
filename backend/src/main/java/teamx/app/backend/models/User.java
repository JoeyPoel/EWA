package teamx.app.backend.models;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

/**
 * User entity
 * Represents a user
 *
 * @author Junior Javier Brito Perez
 * @see Team
 */
@Data
@Entity(name = "Users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;
    enum Role {
        ADMIN,
        USER
    }

    @ManyToOne()
    @JsonIgnore
    @JoinColumn(name = "team_id")
    private Team team;
}