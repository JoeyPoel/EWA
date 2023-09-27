export class warehouse{
    constructor(id, name, location) {
        this.id = id;
        this.name = name;
        this.location = location;
    }

    static createDummyWarehouse(id){
        return new warehouse(id, "dummy warehouse", "dummy location")
    }
}