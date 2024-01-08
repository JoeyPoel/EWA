import {Adaptor} from "@/services/Adaptor";
import {Order} from "@/models/Order";

export class OrderAdaptor extends Adaptor {
    constructor(URL) {
        super(URL);
    }

    /**
     * Retrieves all orders asynchronously.
     *
     * @async
     * @return {Promise<Array<Order>>} A promise that resolves with an array of Order objects.
     */
    async asyncFindAll() {
        const options = {
            method: "GET", headers: {"Content-Type": "application/json"},
        }
        const response = await this.fetchJson(this.resourceUrl, options);

        if (response) {
            return response.map(order => Order.fromJson(order));
        }
    }
    /**
     * Retrieves all orders by warehouse id asynchronously.
     *
     * @param {string} warehouseId - The id of the warehouse.
     * @return {Promise<Array<Order>>} - A Promise that resolves to an array of
     *     Order instances.
     */
    async asyncFindAllByWarehouseId(warehouseId) {
        const options = {
            method: "GET", headers: {"Content-Type": "application/json"},
        }
        const response = await this.fetchJson(
            `${this.resourceUrl}/warehouse/${warehouseId}`, options);
        if (response) {
            return response.map(order => Order.fromJson(order));
        }
    }
}