import {Model} from "@/models/Model";

export class User extends Model{
    constructor(id, name, email, password, role, teamId) {
        super();
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.teamId = teamId;
    }

    static loginUser(email, password) {
        return new User(null, null, email, password, null, null);
    }

    static roleList = [
        "Admin",
        "User",
    ];
}