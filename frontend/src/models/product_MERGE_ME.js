export class Product{
    constructor(id, vendorId, name, description) {
        this.id = id;
        this.vendorId = vendorId;
        this.name = name;
        this.description = description;
    }

    static productList = [
        "Solar panels",
        "Solar Cables",
        "Main Connectors (AC)",
        "Inverter",
        "Storage Unit",
        "Montage Material",
        "Monitoring System",
        "DC Protection",
        "AC Protection"
    ];

    static createDummyProduct(id, vendors, nameNumber){
        return new Product(id, vendors[Math.floor(Math.random() * vendors.length)],
            Product.productList[nameNumber],
            "Dummy Description")
    }
}