import {Model} from "@/models/Model";

export class Capacity extends Model {
    constructor(id, warehouseId, categoryName, categoryId, capacity, minimumStockLevel) {
        super();
        this.id = id;
        this.warehouseId = warehouseId;
        this.categoryName = categoryName;
        this.categoryId = categoryId;
        this.capacity = capacity;
        this.minimumStockLevel = minimumStockLevel;
    }
}