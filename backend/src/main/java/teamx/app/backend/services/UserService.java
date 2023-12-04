package teamx.app.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import teamx.app.backend.models.Team;
import teamx.app.backend.models.User;
import teamx.app.backend.repositories.UserRepository;

import java.util.List;
import java.util.Objects;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User login(User user) {
        User foundUser = userRepository
                .findByEmail(user.getEmail())
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")
                );

        if (!Objects.equals(foundUser.getPassword(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Incorrect password");
        }

        return foundUser;
    }

    public List<User> findByRole(User.Role role) {
        return userRepository.findByRole(role);
    }


    public List<User> getAllByTeamId(Long teamId) {
        if (teamId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Team id is null");
        }

        List<User> users = userRepository.getAllByTeam_Id(teamId);

        if (users.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Users not found");
        }

        return users;
    }

    public User findByEmail(String email) {
        return userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")
                );
    }

    public List<User> getAll() {
        List<User> users = userRepository.findAll();

        if (users.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Users not found");
        }

        return users;
    }

    public User getById(Long id) {
        return userRepository
                .findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")
                );
    }

    public User add(User user) {
        if (userRepository.existsById(user.getId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User with this id already exists");
        }

        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User with this email already exists");
        }

        return userRepository.save(user);
    }

    public User update(User user, Long id) {
        User existingUser = getById(id);

        existingUser.setName(user.getName());
        existingUser.setEmail(user.getEmail());
        existingUser.setPassword(user.getPassword());
        existingUser.setRole(user.getRole());
        existingUser.setTeam(user.getTeam());

        return userRepository.save(existingUser);
    }

    public User delete(Long id) {
        User user = getById(id);

        userRepository.deleteById(id);

        if (userRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User could not be deleted");
        }

        return user;
    }


    public List<User> getAllByIds(List<Long> membersIds) {
        List<User> users = userRepository.getAllByIdIn(membersIds);

        if (users.size() != membersIds.size()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Users not found");
        }

        return users;
    }

    public List<User> getAllByNoTeam() {
        List<User> users = userRepository.getAllByTeamIsNull();

        if (users.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Users not found");
        }

        return users;
    }

    public List<User> setTeam(List<Long> membersIds, Team team) {
        validateInput(membersIds, team);
        List<User> users = getAllByIds(membersIds);
        unsetUsersFromTeam(team, membersIds);
        return setUserTeamAndSave(users, team);
    }

    private void validateInput(List<Long> membersIds, Team team) {
        if (membersIds == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User IDs are null");
        }
        if (team == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Team is null");
        }
    }

    private void unsetUsersFromTeam(Team team, List<Long> membersIds) {
        List<User> usersToUnset = userRepository.getAllByTeam_IdAndIdNotIn(team.getId(), membersIds);
        usersToUnset.forEach(user -> user.setTeam(null));
        userRepository.saveAll(usersToUnset);
    }

    private List<User> setUserTeamAndSave(List<User> users, Team team) {
        users.forEach(user -> user.setTeam(team));
        List<User> savedUsers = userRepository.saveAll(users);
        if (savedUsers.size() != users.size()) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Users could not be set to team");
        }
        return savedUsers;
    }
}