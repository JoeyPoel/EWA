import {Adaptor} from "@/services/Adaptor";
import {InventoryProduct} from "@/models/InventoryProduct";

export class InventoryAdaptor extends Adaptor {
    constructor(URL) {
        super(URL);
    }

    async asyncFindAllProductsHavingTransactions() {
        const options = {
            method: "GET", headers: {"Content-Type": "application/json"},
        }

        const response = await this.fetchJson(
            this.resourceUrl + "/getAllProductsByHavingTransactions", options);
        if (response) {
            return response.map(inventoryProductDTO => Object.assign(new InventoryProduct(), inventoryProductDTO));
        }
    }

    async asyncGetProductsByWarehouseId(warehouseId) {
        const options = {
            method: "GET", headers: {"Content-Type": "application/json"},
        }

        const response = await this.fetchJson(
            this.resourceUrl + "/getProductsByWarehouseId/" + warehouseId, options);
        if (response) {
            return response.map(inventoryProductDTO => Object.assign(new InventoryProduct(), inventoryProductDTO));
        }
    }
}