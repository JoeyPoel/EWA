import {Adaptor} from "@/services/Adaptor";
import {InventoryProductDTO} from "@/models/InventoryProductDTO";

export class InventoryAdaptor extends Adaptor {
    constructor(URL) {
        super(URL);
    }

    async asyncFindAllPaginated(pageSettings) {
        const options = {
            method: "POST", headers: {"Content-Type": "application/json"}, body: JSON.stringify(pageSettings)
        }
        console.log(options);

        const response = await this.fetchJson(this.resourceUrl + "/getAllPaginated", options);
        if (response) {
            console.log(response);
            return response
        }
    }

    async asyncFindAllProductsHavingTransactions() {
        const options = {
            method: "GET", headers: {"Content-Type": "application/json"},
        }

        const response = await this.fetchJson(
            this.resourceUrl + "/getAllProductsByHavingTransactions", options);
        if (response) {
            return response.map(inventoryProductDTO => Object.assign(new InventoryProductDTO(), inventoryProductDTO));
        }
    }

    async asyncGetProductsByWarehouseId(warehouseId) {
        const options = {
            method: "GET", headers: {"Content-Type": "application/json"},
        }

        const response = await this.fetchJson(
            this.resourceUrl + "/getProductsByWarehouseId/" + warehouseId, options);
        if (response) {
            return response.map(inventoryProductDTO => Object.assign(new InventoryProductDTO(), inventoryProductDTO));
        }
    }
}