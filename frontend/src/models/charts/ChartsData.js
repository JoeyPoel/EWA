import {Model} from "@/models/Model";

export class ChartsData extends Model {
    constructor(labels, datasets) {
        super();
        this.labels = labels;
        this.datasets = datasets;
    }
}

