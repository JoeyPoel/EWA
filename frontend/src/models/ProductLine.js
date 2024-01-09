import {Model} from "@/models/Model";

export class productLine extends Model{
    constructor(transactionId,productId, quantity) {
        super();
        this.transactionId = transactionId;
        this.productId = productId;
        this.quantity = quantity;
    }
}