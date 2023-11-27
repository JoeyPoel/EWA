export class Order {
    constructor(id, products, orderDate, deliveryDate, warehouseId, projectId, userId) {
        this.id = id;
        this.products = products;
        this.orderDate = orderDate;
        this.deliveryDate = deliveryDate;
        this.warehouseId = warehouseId;
        this.projectId = projectId;
        this.userId = userId;
    }
}