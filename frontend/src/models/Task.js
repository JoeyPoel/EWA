export class Task {
    constructor(id, name, description, deadline, priority, status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.deadline = deadline;
        this.priority = priority;
        this.status = status;
    }

    static priorityList = [
        "LOW",
        "MEDIUM",
        "HIGH"
    ];

    static statusList = [
        "TODO",
        "IN_PROGRESS",
        "DONE"
    ];
}