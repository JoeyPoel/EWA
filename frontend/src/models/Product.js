export class Product {
    constructor(id, name, description, quantity, warehouseId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.warehouseId = warehouseId;
    }

    static productList = [
        "Solar panels",
        "Solar Cables",
        "Main Connectors (AC)",
        "Inverter",
        "Storage Unit",
        "Montage Material",
        'Battery Pack',
        'LED Light',
        'Solar Inverter',
        'Electric Motor',
        'Charging Station'
    ];

    static createDummyProduct(id, warehouseId){
        return new Product(id, this.productList[id],"Dummy Description" ,Math.floor(Math.random() * 100), warehouseId)
    }

    static createRandomDummyProduct(id) {
        return new Product(id,
            this.productList[this.getRandomProductNumber()],
            "Dummy Description",
            Math.floor(Math.random() * 100),
            this.getRandomProductNumber()
        );
    }

    static getRandomProductNumber() {
        return Math.floor(Math.random() * this.productList.length);
    }
}