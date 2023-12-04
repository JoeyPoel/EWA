import {Model} from "@/models/Model";

export class Product extends Model {
    constructor(id, name, description, price, categoryId) {
        super();
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.categoryId = categoryId;
    }
}