export class User{
    constructor(id, name, email, password, role, team) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.team = team;
    }

    static loginUser(email, password) {
        return new User(null, null, email, password, null, null);
    }

    static roleList = [
        "Admin",
        "User",
    ];
}