import {Model} from "@/models/Model";

export class Warehouse extends Model {
    constructor(id, name, location, address, postcode, country, contactName, contactEmail, contactPhone) {
        super();
        this.id = id;
        this.name = name;
        this.location = location;
        this.address = address;
        this.postcode = postcode;
        this.country = country;
        this.contactName = contactName;
        this.contactEmail = contactEmail;
        this.contactPhone = contactPhone;
    }
}