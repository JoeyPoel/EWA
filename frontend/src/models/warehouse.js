export class warehouse{
    constructor(id, name, location) {
        this.id = id;
        this.name = name;
        this.location = location;
    }
    productList = [];

    static warehouseList = [
        "Solar clarity",
        "Solar groen",
        "4Blue",
        "Stralend Groen",
        "Ziezo-Solar",
        "Baywa"
    ];

    addProduct(product) {
        this.productList.push(product);
    }


    static createDummyWarehouse(id){
        return new warehouse(id, this.warehouseList[id], "dummy location");
    }

    get products() {
        return this.productList;
    }

}