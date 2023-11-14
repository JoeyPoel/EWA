package teamx.app.backend.repositories;

import org.springframework.stereotype.Repository;
import teamx.app.backend.models.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Repository
public class UserRepositoryMock implements UserRepository<User> {
    private ArrayList<User> users = new ArrayList<>();
    private final int USER_ID_MIN = 0;
    private final int USER_ID_MAX = 100;

    public UserRepositoryMock(){
        User user = new User(1, "name", "email", "team", "viewer");
        users.add(user);
    }

    @Override
    public List<User> findAll() {
        return users;
    }

    public User findById(int id) {
        return users.stream().filter(user -> user.getUserId() == id).findFirst().orElse(null);
    }

    public User save(User user) {
        if (!users.contains(user)) {
            if (user.getUserId() == 0) {
                do {
                    user.setUserId(new Random().nextInt(USER_ID_MAX) + USER_ID_MIN);
                } while (users.contains(user));
            }
            users.add(user);
            return user;
        }
        users.set(users.indexOf(user), user);
        return user;
    }

    public User deleteById(int id) {
        User userToRemove = null;
        for (User user : users) {
            if (user.getUserId() == id) {
                userToRemove = user;
                break;
            }
        }
        if (userToRemove != null) {
            users.remove(userToRemove);
        }
        return userToRemove;
    }
}
