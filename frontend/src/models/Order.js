export class Order {
    constructor(id, description, isDelivered, orderDate, deliveryDate, warehouse, orderedBy, transactions) {
        this.id = id;
        this.description = description;
        this.isDelivered = isDelivered;
        this.orderDate = orderDate;
        this.deliveryDate = deliveryDate;
        this.warehouse = warehouse;
        this.orderedBy = orderedBy;
        this.transactions = transactions;
    }
}