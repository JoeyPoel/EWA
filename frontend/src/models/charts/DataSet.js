import {Model} from "@/models/Model";

export class Dataset extends Model {
    constructor(label, data, backgroundColor) {
        super();
        this.label = label;
        this.data = data;
        this.backgroundColor = backgroundColor;
    }
}