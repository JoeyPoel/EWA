import {Model} from "@/models/Model";

export class Team extends Model {
    constructor(id, warehouseId, name, leaderId, membersIds) {
        super();
        this.id = id;
        this.warehouseId = warehouseId;
        this.name = name;
        this.leaderId = leaderId;
        this.membersIds = membersIds;
    }
}
