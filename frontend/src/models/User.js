export class User{
    // constructor(id, name, email, team, role, password, tasksAssigned, ordersMade, personalTodoList) {
    //     this.id = id;
    //     this.name = name;
    //     this.email = email;
    //     this.team = team;
    //     this.role = role;
    //     this.password = password;
    //     this.tasksAssigned = tasksAssigned;
    //     this.ordersMade = ordersMade;
    //     this.personalTodoList = personalTodoList;
    // }

    constructor(email, password) {
        this.email = email;
        this.password = password;
    }

    static roleList = [
        "Admin",
        "User",
    ];
}