package teamx.app.backend.models;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    @JsonIgnore
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
    enum Role {
        ADMIN,
        USER
    }

    @ManyToOne
    @JsonBackReference
    private Team team;

    @OneToMany
    @JsonBackReference
    private List<Order> ordersMade;
}