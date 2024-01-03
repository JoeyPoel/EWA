package teamx.app.backend.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * DTO fro mail response
 *
 * @author Joey van der Poel
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MailResponse {
    private String message;
    private boolean status;

}