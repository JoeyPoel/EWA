package teamx.app.backend.models;

public class User {
    private int userId;
    private String name;
    private String email;
    private String team;
    private String role;

    public User(int userId) {
        this.userId = userId;
    }

    public static User createUser(int userId, String name, String email, String team, String role) {
        User user = new User(userId);
        user.setName(name);
        user.setEmail(email);
        user.setTeam(team);
        user.setRole(role);
        return user;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
