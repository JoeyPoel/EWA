package teamx.app.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import teamx.app.backend.models.User;
import teamx.app.backend.models.dto.UserDTO;
import teamx.app.backend.repositories.UserRepository;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDTO login(User user) {
        User foundUser = findByEmail(user.getEmail());
        if (foundUser == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        if (!foundUser.getPassword().equals(user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Incorrect password");
        }
        return convertToDTO(foundUser);
    }

    protected List<User> getAllByTeamId(Long teamId) {
        return userRepository.getAllByTeam_Id(teamId);
    }

    public List<UserDTO> getAllByTeamIdDTO(Long teamId) {
        return getAllByTeamId(teamId).stream().map(this::convertToDTO).toList();
    }
    protected User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    public UserDTO findByEmailDTO(String email) {
        return convertToDTO(findByEmail(email));
    }

    protected List<User> findAll() {
        return userRepository.findAll();
    }

    public List<UserDTO> findAllDTO() {
        return findAll().stream().map(this::convertToDTO).toList();
    }

    protected User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public UserDTO findByIdDTO(Long id) {
        return convertToDTO(findById(id));
    }

    protected User save(User user) {
        return userRepository.save(user);
    }

    public UserDTO saveDTO(UserDTO userDTO) {
        return convertToDTO(save(convertToEntity(userDTO)));
    }

    protected User deleteById(Long id) {
        User user = findById(id);
        userRepository.deleteById(id);
        return user;
    }

    public UserDTO deleteByIdDTO(Long id) {
        return convertToDTO(deleteById(id));
    }

    protected List<User> getAllByIds(List<Long> membersIds) {
        return userRepository.findAllById(membersIds);
    }

    protected List<User> getAllByNoTeam() {
        return userRepository.getAllByTeamIsNull();
    }

    public List<UserDTO> getAllByNoTeamDTO() {
        return getAllByNoTeam().stream().map(this::convertToDTO).toList();
    }

    public List<UserDTO> getAllByIdsDTO(List<Long> membersIds) {
        return getAllByIds(membersIds).stream().map(this::convertToDTO).toList();
    }

    private UserDTO convertToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setRole(String.valueOf(user.getRole()));
        return userDTO;
    }

    private User convertToEntity(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setRole(User.Role.valueOf(userDTO.getRole()));
        return user;
    }
}