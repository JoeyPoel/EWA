export class Warehouse {
    constructor(id, name, location, address, postcode, country, contactName, contactEmail, contactPhone) {
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

    static equals(a, b) {
        if (a instanceof Warehouse && b instanceof Warehouse) {
            for (const key of Object.keys(a)) {
                if (a[key] !== b[key]) {
                    return false;
                }
            }
        }
        return true;
    }

    static copy(a) {
        return Object.assign(new Warehouse(), a);
    }

    static fromJson(json) {
        return Object.assign(new Warehouse(), json);
    }
}