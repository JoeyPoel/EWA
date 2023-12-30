package teamx.app.backend.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class NotificationTest {

    private Notification notification;
    private User user;
    private Date currentDate;

    @BeforeEach
    void setUp() {
        currentDate = new Date();
        user = new User();

        notification = new Notification();
        notification.setId(1L);
        notification.setLink("https://example.com");
        notification.setMessage("Test Notification");
        notification.setRead(false);
        notification.setDate(currentDate);
        notification.setUser(user);
    }

    @Test
    void testNotificationGettersAndSetters() {
        assertEquals(1L, notification.getId());
        assertEquals("https://example.com", notification.getLink());
        assertEquals("Test Notification", notification.getMessage());
        assertFalse(notification.isRead());
        assertEquals(currentDate, notification.getDate());
        assertEquals(user, notification.getUser());
    }

    @Test
    void testJsonDateFormat() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String formattedDate = dateFormat.format(currentDate);
        assertEquals(formattedDate, dateFormat.format(notification.getDate()), "Date format should match the specified JsonFormat");
    }
}
