
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

    public UserDTO(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public UserDTO() {

    }
}