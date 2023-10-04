export class product{
    constructor(id, name, description, quantity) {
        this.id = id;
        this.name = name;
        this.description = description;
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
        return new product(id, this.productList[id],"Dummy Description" ,Math.floor(Math.random() * 100))
    }
}