package teamx.app.backend.Models;

import org.junit.jupiter.api.Test;
import teamx.app.backend.models.dto.MailRequest;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Tests fro mail request
 *
 * @author Joey van der Poel
 */
public class MailRequestTest {

    @Test
    void testMailRequestGettersAndSetters() {
        MailRequest mailRequest = new MailRequest();
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
        MailRequest mailRequest1 = new MailRequest();
        mailRequest1.setName("John Doe");
        mailRequest1.setTo("recipient@example.com");
        mailRequest1.setFrom("sender@example.com");
        mailRequest1.setSubject("Test Subject");

        MailRequest mailRequest2 = new MailRequest();
        mailRequest2.setName("John Doe");
        mailRequest2.setTo("recipient@example.com");
        mailRequest2.setFrom("sender@example.com");
        mailRequest2.setSubject("Test Subject");

        assertEquals(mailRequest1, mailRequest2);
        assertEquals(mailRequest1.hashCode(), mailRequest2.hashCode());
    }

    @Test
    void testMailRequestNotEqual() {
        MailRequest mailRequest1 = new MailRequest();
        mailRequest1.setName("John Doe");
        mailRequest1.setTo("recipient1@example.com");
        mailRequest1.setFrom("sender@example.com");
        mailRequest1.setSubject("Test Subject");

        MailRequest mailRequest2 = new MailRequest();
        mailRequest2.setName("John Doe");
        mailRequest2.setTo("recipient2@example.com");
        mailRequest2.setFrom("sender@example.com");
        mailRequest2.setSubject("Test Subject");

        assertNotEquals(mailRequest1, mailRequest2);
    }
}
