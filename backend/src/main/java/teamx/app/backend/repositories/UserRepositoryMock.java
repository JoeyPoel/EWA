package teamx.app.backend.repositories;

import teamx.app.backend.models.User;

import java.util.ArrayList;
import java.util.List;

public class UserRepositoryMock implements UserRepository {
    private List<User> users = new ArrayList<>();

    @Override
    public List findAll() {
        return users;
    }

}
