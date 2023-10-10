export class Warehouse {
    constructor(id, name, location, address, postcode, country, contactName, contactEmail, contactPhone, note) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.address = address;
        this.postcode = postcode;
        this.country = country;
        this.contactName = contactName;
        this.contactEmail = contactEmail;
        this.contactPhone = contactPhone;
        this.note = note;
    }

    static warehouseList = [
        "Solar clarity",
        "Solar groen",
        "4Blue",
        "Stralend Groen",
        "Ziezo-Solar",
        "Baywa"
    ];

    static locationList = [
        "Amsterdam",
        "Rotterdam",
        "Den Haag",
        "Utrecht",
        "Eindhoven",
        "Tilburg",
        "Groningen",
        "Almere"
    ];

    static getImage() {
        return require("@/assets/warehouse.png");
    }

    static getRandomInt(max) {
        return Math.floor(Math.random() * Math.floor(max));
    }

    static createDummyWarehouse(id) {
        return new Warehouse(
            id,
            this.warehouseList[this.getRandomInt(this.warehouseList.length)],
            this.locationList[this.getRandomInt(this.locationList.length)],
            "Kerkstraat 1",
            "1234 AB",
            "Nederland",
            "Jan Jansen",
            "janjansen@gmail.com",
            "0612345678",
            "Dit is een dummy warehouse"
        );
    }

    static equals(warehouse1, warehouse2) {
        if (!(warehouse1 instanceof Warehouse) || !(warehouse2 instanceof Warehouse)) return false;
        for (const key in warehouse1) {
            if (warehouse1[key] !== warehouse2[key]) {
                return false;
            }
        }
        return true;
    }

    static copy(warehouse) {
        return new Warehouse(warehouse.id, warehouse.name, warehouse.location, warehouse.address,
            warehouse.postcode, warehouse.country, warehouse.contactName, warehouse.contactEmail,
            warehouse.contactPhone, warehouse.note);
    }
}