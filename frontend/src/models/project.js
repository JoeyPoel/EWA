export class Project {
    constructor(id, status, team, description, name) {
        this.id = id
        this.status = status
        this.team = team
        this.description = description
        this.name = name
    }

    /*static statusList = [
        "In Progress",
        "Completed",
        "On Hold",
        "Cancelled"
    ]*/

    static statusList = [
        { value: "in_progress", displayName: "In Progress" },
        { value: "completed", displayName: "Completed" },
        { value: "on_hold", displayName: "On Hold" },
        { value: "cancelled", displayName: "Cancelled" }
    ];
    static projectList = [
        "Dijk Project",
        "Ijburg Project",
        "OBA School Installatie",
        "Solar Festival Installatie"
    ]

    static teamList = [
        "Team Zuid",
        "Installatie Ploeg Rotterdam",
        "Team Noord",
        "Externe Installatie Ploeg Van Brugge"
    ]

    static descriptionList = [
        "Dit is een beschrijving van het project",
    ]

    static nameList = [
        "Dijk Project",
        "Ijburg Project",
        "OBA School Installatie",
        "Solar Festival Installatie"
    ];

    static getRandomInt(max) {
        return Math.floor(Math.random() * Math.floor(max));
    }

    static createDummyProject(id) {
        return new Project(
            id,
            this.statusList[this.getRandomInt(this.statusList.length)].value,
            this.teamList[this.getRandomInt(this.teamList.length)],
            this.descriptionList[this.getRandomInt(this.descriptionList.length)],
            this.projectList[this.getRandomInt(this.projectList.length)],

        );
    }



}