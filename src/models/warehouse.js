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

    static createDummyWarehouse(id){
        return new warehouse(id, this.warehouseList[id], "dummy location")
    }
}