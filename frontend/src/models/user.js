import {Warehouse} from "@/models/warehouse";

export class User{
    constructor(id, name, email, team, role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.team = team;
        this.role = role;
    }

    static userList = [
        "John Doe",
        "Jane Smith",
        "Bob Johnson",
        "Alice Brown",
        "David Lee",
        "Emily Davis",
    ];

    static createDummyUser(id){
        return new User(id, this.userList[id], "dummy@email.com", Warehouse.warehouseList[id], "Viewer");
    }
}