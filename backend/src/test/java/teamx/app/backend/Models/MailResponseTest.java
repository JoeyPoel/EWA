package teamx.app.backend.Models;

import org.junit.jupiter.api.Test;
import teamx.app.backend.utils.DTO;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests fro mail response
 *
 * @author Joey van der Poel
 */
public class MailResponseTest {

    @Test
    void testMailResponseGettersAndSetters() {
        DTO.MailResponse mailResponse = new DTO.MailResponse();
        mailResponse.setMessage("Mail sent successfully");
        mailResponse.setStatus(true);

        assertEquals("Mail sent successfully", mailResponse.getMessage());
        assertTrue(mailResponse.isStatus());
    }

    @Test
    void testMailResponseEquality() {
        DTO.MailResponse mailResponse1 = new DTO.MailResponse("Success", true);
        DTO.MailResponse mailResponse2 = new DTO.MailResponse("Success", true);

        assertEquals(mailResponse1, mailResponse2);
        assertEquals(mailResponse1.hashCode(), mailResponse2.hashCode());
    }

    @Test
    void testMailResponseNotEqual() {
        DTO.MailResponse mailResponse1 = new DTO.MailResponse("Success", true);
        DTO.MailResponse mailResponse2 = new DTO.MailResponse("Failed", false);

        assertNotEquals(mailResponse1, mailResponse2);
    }
}
