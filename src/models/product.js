export class product{
    constructor(id, name, quantity) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
    }

    static productList = [
        "Solar panels",
        "Solar Cables",
        "Main Connectors (AC)",
        "Inverter",
        "Storage Unit",
        "Montage Material"
    ];

    static createDummyProduct(id){
        return new product(id, this.productList[id], Math.floor(Math.random() * 100))
    }
}