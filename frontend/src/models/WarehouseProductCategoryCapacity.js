import {Model} from "@/models/Model";

export class WarehouseProductCategoryCapacity extends Model {
    constructor(id, capacity, warehouseId, categoryId, minimumStockLevel, categoryName) {
        super();
        this.id = id;
        this.capacity = capacity;
        this.warehouseId = warehouseId;
        this.categoryName = categoryName;
        this.categoryId = categoryId;
        this.minimumStockLevel = minimumStockLevel;
    }
}