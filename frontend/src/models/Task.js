import {Model} from "@/models/Model";

export class Task extends Model {
    constructor(id, name, description, deadline, status, personalTodoListOwnerId, personalTodoListOwnerName) {
        super();
        this.id = id;
        this.name = name;
        this.description = description;
        this.deadline = deadline;
        //this.priority = priority;
        this.status = status;
        this.personalTodoListOwnerId = personalTodoListOwnerId;
        this.personalTodoListOwnerName = personalTodoListOwnerName;
    }

    static priorityList = [
        "LOW",
        "MEDIUM",
        "HIGH"
    ];

    static statusList = [
        { value: "TODO", displayName: "TODO"} ,
        { value: "IN_PROGRESS", displayName: "In Progress" } ,
        { value: "DONE", displayName: "Done"}
    ];

}