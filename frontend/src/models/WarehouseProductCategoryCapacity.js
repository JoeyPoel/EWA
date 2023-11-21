import {Model} from "@/models/Model";

export class WarehouseProductCategoryCapacity extends Model {
    constructor(id, capacity, warehouse, productCategory, minimumStockLevel) {
        super();
        this.id = id;
        this.capacity = capacity;
        this.warehouse = warehouse;
        this.productCategory = productCategory;
        this.minimumStockLevel = minimumStockLevel;
    }
}