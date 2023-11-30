import {Model} from "@/models/Model";

export class Order extends Model{
    constructor(id, products, orderDate, deliveryDate, warehouseId, projectId, userId) {
        super();
        this.id = id;
        this.products = products;
        this.orderDate = orderDate;
        this.deliveryDate = deliveryDate;
        this.warehouseId = warehouseId;
        this.projectId = projectId;
        this.userId = userId;
    }
}