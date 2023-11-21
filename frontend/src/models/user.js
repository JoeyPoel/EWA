export class User {
    constructor(id, name, email, password, role, team, tasksAssigned, ordersMade) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.team = team;
        this.tasksAssigned = tasksAssigned;
        this.ordersMade = ordersMade;
    }

    static roleList = [
        "ADMIN",
        "USER"
    ];
}