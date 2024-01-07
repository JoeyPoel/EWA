package teamx.app.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BackendApplicationTests {

    @Autowired
    private BackendApplication application;

    @Test
    void springBootApplicationContextLoadsOk() {
        assertNotNull(application, "Application context should be loaded");
        System.out.println("Application auto-configuration has succeeded.");
    }

    @Test
    public void jUnit5HasBeenConfiguredForTesting() {
        assertThrows(ArithmeticException.class, () -> { int a = 0; int b = 1 / a; });
    }
}