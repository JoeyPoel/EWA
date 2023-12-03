import {Adaptor} from "@/services/Adaptor";

export class ChartsAdaptor extends Adaptor {
    constructor(URL) {
        super(URL);
    }

    async asyncFindInventoryBarData(warehouseId) {
        const options = {
            method: "GET", headers: {"Content-Type": "application/json"},
        }

        const response = await this.fetchJson(this.resourceUrl + "/inventories/" + warehouseId + "/history", options);

        return response ? response : null;
    }

    async asyncFindAllInventoryBar() {
        const options = {
            method: "GET", headers: {"Content-Type": "application/json"},
        }

        const response = await this.fetchJson(this.resourceUrl + "/inventories/history", options);

        return response ? response : null;
    }
}