import {Model} from "@/models/Model";

export class Dataset extends Model{
    constructor(label, backgroundColor, data) {
        super();
        this.label = label;
        this.backgroundColor = backgroundColor;
        this.data = data;
    }
}