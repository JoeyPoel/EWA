import {Model} from "@/models/Model";

export class Project extends Model{
    constructor(id, name, description, location, clientName, clientEmail, clientPhone, startDate, endDate, status,
                teamId) {
        super();
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
        this.teamId = teamId;
    }

    static statusList = [
        { value: "IN_PROGRESS", displayName: "In Progress" },
        { value: "FINISHED", displayName: "Finished" },
    ];

    static getStatusColor(project) {
        switch (project.status) {
            case "IN_PROGRESS":
                return "blue";
            case "FINISHED" || "DONE":
                return "green";
            case "TODO":
                return "red";
            default:
                return "grey";
        }
    }

    static getStatusDisplayName(status) {
        return Project.statusList.find(s => s.value === status)?.displayName;
    }
}
