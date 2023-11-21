package teamx.app.backend.models;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

/**
 * User entity
 * Represents a user
 *
 * @author Junior Javier Brito Perez
 * @see Team
 */
@Data
@Entity
@Table(name = "Users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    @JsonIgnore
    private String password;
    private String role;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "team_id", nullable = false)
    private Team team;
}