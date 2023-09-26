export class product{
    constructor(id, name, quantity) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
    }

    static createDummyProduct(id){
        return new product(id, "dummy", 1)
    }
}