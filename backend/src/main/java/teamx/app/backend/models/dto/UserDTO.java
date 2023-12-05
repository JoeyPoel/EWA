package teamx.app.backend.models.dto;

import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private String role;
    private Long teamId;
    private String jwtToken;

    public UserDTO(Long userId, String email, String role, String jwtToken) {
        this.id = userId;
        this.email = email;
        this.role = role;
        this.jwtToken = jwtToken;
    }

    public UserDTO() {

    }
}