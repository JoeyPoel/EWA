package teamx.app.backend.Models;

import org.junit.jupiter.api.Test;
import teamx.app.backend.utils.DTO;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

/**
 * Tests fro mail request
 *
 * @author Joey van der Poel
 */
public class MailRequestTest {

    @Test
    void testMailRequestGettersAndSetters() {
        DTO.MailRequest mailRequest = new DTO.MailRequest();
        mailRequest.setName("John Doe");
        mailRequest.setTo("recipient@example.com");
        mailRequest.setFrom("sender@example.com");
        mailRequest.setSubject("Test Subject");

        assertEquals("John Doe", mailRequest.getName());
        assertEquals("recipient@example.com", mailRequest.getTo());
        assertEquals("sender@example.com", mailRequest.getFrom());
        assertEquals("Test Subject", mailRequest.getSubject());
    }

    @Test
    void testMailRequestEquality() {
        DTO.MailRequest mailRequest1 = new DTO.MailRequest();
        mailRequest1.setName("John Doe");
        mailRequest1.setTo("recipient@example.com");
        mailRequest1.setFrom("sender@example.com");
        mailRequest1.setSubject("Test Subject");

        DTO.MailRequest mailRequest2 = new DTO.MailRequest();
        mailRequest2.setName("John Doe");
        mailRequest2.setTo("recipient@example.com");
        mailRequest2.setFrom("sender@example.com");
        mailRequest2.setSubject("Test Subject");

        assertEquals(mailRequest1, mailRequest2);
        assertEquals(mailRequest1.hashCode(), mailRequest2.hashCode());
    }

    @Test
    void testMailRequestNotEqual() {
        DTO.MailRequest mailRequest1 = new DTO.MailRequest();
        mailRequest1.setName("John Doe");
        mailRequest1.setTo("recipient1@example.com");
        mailRequest1.setFrom("sender@example.com");
        mailRequest1.setSubject("Test Subject");

        DTO.MailRequest mailRequest2 = new DTO.MailRequest();
        mailRequest2.setName("John Doe");
        mailRequest2.setTo("recipient2@example.com");
        mailRequest2.setFrom("sender@example.com");
        mailRequest2.setSubject("Test Subject");

        assertNotEquals(mailRequest1, mailRequest2);
    }
}
