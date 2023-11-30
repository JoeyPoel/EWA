import {Model} from "@/models/Model";

export class OrderLine extends Model{
    constructor(productId, quantity) {
        super();
        this.productId = productId;
        this.quantity = quantity;
    }
}