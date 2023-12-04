import {Model} from "@/models/Model";

export class InventoryProject extends Model{
    constructor(productId, quantity, warehouseName, transactionType, transactionDate, productName) {
        super();
        this.productId = productId;
        this.quantity = quantity;
        this.warehouseName = warehouseName;
        this.transactionType = transactionType;
        this.transactionDate = transactionDate;
        this.productName = productName;
    }
}