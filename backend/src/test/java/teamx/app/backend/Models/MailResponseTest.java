package teamx.app.backend.Models;

import org.junit.jupiter.api.Test;
import teamx.app.backend.models.dto.MailResponse;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Tests fro mail response
 *
 * @author Joey van der Poel
 */
public class MailResponseTest {

    @Test
    void testMailResponseGettersAndSetters() {
        MailResponse mailResponse = new MailResponse();
        mailResponse.setMessage("Mail sent successfully");
        mailResponse.setStatus(true);

        assertEquals("Mail sent successfully", mailResponse.getMessage());
        assertTrue(mailResponse.isStatus());
    }

    @Test
    void testMailResponseEquality() {
        MailResponse mailResponse1 = new MailResponse("Success", true);
        MailResponse mailResponse2 = new MailResponse("Success", true);

        assertEquals(mailResponse1, mailResponse2);
        assertEquals(mailResponse1.hashCode(), mailResponse2.hashCode());
    }

    @Test
    void testMailResponseNotEqual() {
        MailResponse mailResponse1 = new MailResponse("Success", true);
        MailResponse mailResponse2 = new MailResponse("Failed", false);

        assertNotEquals(mailResponse1, mailResponse2);
    }
}
