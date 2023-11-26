export class Order {
    constructor(id, orderLines, orderDate, deliveryDate, warehouseId, projectId, userId) {
        this.id = id;
        this.orderLines = orderLines;
        this.orderDate = orderDate;
        this.deliveryDate = deliveryDate;
        this.warehouseId = warehouseId;
        this.projectId = projectId;
        this.userId = userId;
    }
}