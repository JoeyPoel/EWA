export class warehouse{
    constructor(id, name, location) {
        this.id = id;
        this.name = name;
        this.location = location;
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

    static getRandomInt(max) {
        return Math.floor(Math.random() * Math.floor(max));
    }

    static createDummyWarehouse(id){
        return new warehouse(
            id,
            this.warehouseList[this.getRandomInt(this.warehouseList.length)],
            this.locationList[this.getRandomInt(this.locationList.length)]
        );
    }
}