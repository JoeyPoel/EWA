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

    /**
     * Creates a Product from a JSON object.
     *
     * @returns {boolean} - True if the two objects are equal, false otherwise.
     * @param other
     */

    equals(other) {
        // Ensure both a and b are instances of Product
        if (!(this instanceof Product && other instanceof Product)) {
            return false;
        }

        // Compare each property
        return this.id === other.id &&
            this.name === other.name &&
            this.description === other.description &&
            this.price === other.price &&
            this.categoryId === other.categoryId;
    }
}