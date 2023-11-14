package teamx.app.backend.repositories;


import org.springframework.stereotype.Repository;
import teamx.app.backend.models.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class UserRepository {
    private final List<User> users = new ArrayList<>();

    public UserRepository() {
        users.add(new User(1,"x@x.com", "xxx", "admin"));
        users.add(new User(2,"z@z.com", "zzz", "viewer"));
    }

    public User findByEmail(String email) {
        for (User user : users) {
            if(Objects.equals(user.getEmail(), email)){
                return user;
            }
        }
        return null;
    }
}
