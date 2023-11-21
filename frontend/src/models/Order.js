export class Order {
    constructor(id, description, isDelivered, orderDate, deliveryDate) {
        this.id = id;
        this.description = description;
        this.isDelivered = isDelivered;
        this.orderDate = orderDate;
        this.deliveryDate = deliveryDate;
    }
}