export class Task {
    constructor(id, project, name, description, deadline, priority, status, assignedTo) {
        this.id = id;
        this.project = project;
        this.name = name;
        this.description = description;
        this.deadline = deadline;
        this.priority = priority;
        this.status = status;
        this.assignedTo = assignedTo;
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