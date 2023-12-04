import {Adaptor} from "@/services/Adaptor";
import {InventoryProduct} from "@/models/InventoryProduct";

/**
 * A class representing an InventoryAdaptor.
 * @extends Adaptor
 *
 * @author Junior Javier Brito Perez
 */
export class InventoryAdaptor extends Adaptor {
    constructor(URL) {
        super(URL);
    }

    /**
     * Retrieves all inventories products asynchronously.
     *
     * @async
     * @return {Promise<Array<InventoryProduct>>} A promise that resolves with an array of InventoryProduct objects.
     */
    async asyncGetAll() {
        const options = {
            method: "GET", headers: {"Content-Type": "application/json"},
        }
        const response = await this.fetchJson(this.resourceUrl, options);

        if (response) {
            return response.map(inventoryProduct => InventoryProduct.fromJson(inventoryProduct));
        }
    }

    /**
     * Retrieves all inventories products by warehouse id asynchronously.
     *
     * @param {string} warehouseId - The id of the warehouse.
     * @return {Promise<Array<InventoryProduct>>} - A Promise that resolves to an array of
     *     InventoryProduct instances.
     */
    async asyncGetAllByWarehouseId(warehouseId) {
        const options = {
            method: "GET", headers: {"Content-Type": "application/json"},
        }
        const response = await this.fetchJson(
            this.resourceUrl + "/warehouse/" + warehouseId, options);
        if (response) {
            return response.map(inventoryProduct => InventoryProduct.fromJson(inventoryProduct));
        }
    }

    async asyncGetStockByProductId(productId) {
        const options = {
            method: "GET", headers: {"Content-Type": "application/json"},
        }
        const response = await this.fetchJson(
            this.resourceUrl + `/product/${productId}/warehouses`, options);
        if (response) {
            return response;
        }
    }
}