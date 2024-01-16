package teamx.app.backend.controllers;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import teamx.app.backend.models.User;
import teamx.app.backend.services.UserService;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    // Kaifie
    @Test
    public void testGetAllUsers() throws Exception {
        // Arrange
        User user1 = new User();
        user1.setEmail("test1@example.com");
        user1.setName("Test User 1");
        user1.setRole(User.Role.USER);

        User user2 = new User();
        user2.setEmail("test2@example.com");
        user2.setName("Test User 2");
        user2.setRole(User.Role.USER);

        List<User> users = Arrays.asList(user1, user2);

        Mockito.when(userService.getAll()).thenReturn(users);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/users"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("[{\"id\":null,\"name\":\"Test User 1\",\"email\":\"test1@example.com\",\"password\":null,\"role\":\"USER\",\"team\":null},{\"id\":null,\"name\":\"Test User 2\",\"email\":\"test2@example.com\",\"password\":null,\"role\":\"USER\",\"team\":null}]"));
    }

    // Kaifie
    @Test
    public void testGetUserById() throws Exception {
        // Arrange
        User user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");
        user.setName("Test User");
        user.setRole(User.Role.USER);

        Mockito.when(userService.getById(1L)).thenReturn(user);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/users/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("{\"id\":1,\"name\":\"Test User\",\"email\":\"test@example.com\",\"password\":null,\"role\":\"USER\",\"team\":null}"));
    }

    // Kaifie
    @Test
    public void testAddUser() throws Exception {
        // Arrange
        User user = new User();
        user.setEmail("test@example.com");
        user.setName("Test User");
        user.setRole(User.Role.USER);

        Mockito.when(userService.add(Mockito.any(User.class))).thenReturn(user);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .content(new ObjectMapper().writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("{\"id\":null,\"name\":\"Test User\",\"email\":\"test@example.com\",\"password\":null,\"role\":\"USER\",\"team\":null}"));
    }
}