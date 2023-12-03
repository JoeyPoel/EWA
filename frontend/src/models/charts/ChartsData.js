import {Model} from "@/models/Model";

export class ChartsData extends Model {
    constructor(labels, datasets, options) {
        super();
        this.labels = labels;
        this.datasets = datasets;
        this.options = options;
    }
}

