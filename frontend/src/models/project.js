export class Project {
    constructor(id, name, description, location, clientName, clientEmail, clientPhone, startDate,
                endDate, status) {
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
    }

    static statusList = [
        "PENDING",
        "CONFIRMED",
        "IN_PROGRESS",
        "FINISHED",
        "CANCELED"
    ];
}
