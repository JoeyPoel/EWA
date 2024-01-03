package teamx.app.backend.models.dto;
import lombok.Data;
/**
 * DTO fro mail requests
 *
 * @author Joey van der Poel
 */
@Data
public class MailRequest {

    private String name;
    private String to;
    private String from;
    private String subject;

}
