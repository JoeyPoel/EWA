import {Model} from "@/models/Model";

export class InventoryProduct extends Model{
    constructor(productId, warehouseId, name, description, price, quantity) {
        super();
        this.productId = productId;
        this.warehouseId = warehouseId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
    }
}