export class Project {
    constructor(id, name, description, location, clientName, clientEmail, clientPhone, startDate,
                endDate, status, team, tasks, materials) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.location = location;
        this.clientName = clientName;
        this.clientEmail = clientEmail;
        this.clientPhone = clientPhone;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.team = team;
        this.tasks = tasks;
        this.materials = materials;
    }

    static statusList = [
        { value: "PENDING", displayName: "Pending" },
        { value: "CONFIRMED", displayName: "Confirmed" },
        { value: "IN_PROGRESS", displayName: "In Progress" },
        { value: "FINISHED", displayName: "Finished" },
        { value: "CANCELED", displayName: "Canceled" }
    ];
}
