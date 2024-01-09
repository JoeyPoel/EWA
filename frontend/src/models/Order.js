import {Model} from "@/models/Model";

export class Order extends Model{
    constructor(id, products,description, orderDate, deliveryDate, warehouseId, projectId, userId) {
        super();
        this.id = id;
        this.products = products;
        this.description = description;
        this.orderDate = orderDate;
        this.deliveryDate = deliveryDate;
        this.warehouseId = warehouseId;
        this.projectId = projectId;
        this.userId = userId;
    }

    static statusList = [
        { value: "PENDING", displayName: "Pending" },
        { value: "DELIVERED", displayName: "Delivered" },
        { value: "DELAYED", displayName: "Delayed" },
        { value: "CANCELLED", displayName: "Cancelled" },
    ];

    static getStatusColor(order) {
        switch (order.status) {
            case "PENDING":
                return "blue";
            case "DELIVERED":
                return "green";
            case "DELAYED":
                return "orange";
            default:
                return "grey";
        }
    }

    static getStatusDisplayName(status) {
        return Order.statusList.find(s => s.value === status)?.displayName;
    }
}