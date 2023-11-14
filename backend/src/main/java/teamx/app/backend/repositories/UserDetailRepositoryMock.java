package teamx.app.backend.repositories;

import org.springframework.stereotype.Repository;
import teamx.app.backend.models.UserDetail;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Repository
public class UserDetailRepositoryMock implements UserDetailRepository<UserDetail> {
    private ArrayList<UserDetail> users = new ArrayList<>();
    private final int USER_ID_MIN = 0;
    private final int USER_ID_MAX = 100;

    public UserDetailRepositoryMock(){
        UserDetail user = new UserDetail(1, "name", "email", "team", "viewer");
        users.add(user);
    }

    @Override
    public List<UserDetail> findAll() {
        return users;
    }

    public UserDetail findById(int id) {
        return users.stream().filter(user -> user.getUserId() == id).findFirst().orElse(null);
    }

    public UserDetail save(UserDetail user) {
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

    public UserDetail deleteById(int id) {
        UserDetail userToRemove = null;
        for (UserDetail user : users) {
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
