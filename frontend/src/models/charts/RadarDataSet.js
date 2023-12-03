import {Dataset} from "@/models/charts/DataSet";

export class RadarDataSet extends Dataset {
    constructor(label, backgroundColor, data, pointBackgroundColor, pointBorderColor,
                pointHoverBackgroundColor, pointHoverBorderColor) {
        super(label, backgroundColor, data);
        this.pointBackgroundColor = pointBackgroundColor;
        this.pointBorderColor = pointBorderColor;
        this.pointHoverBackgroundColor = pointHoverBackgroundColor;
        this.pointHoverBorderColor = pointHoverBorderColor;
    }
}