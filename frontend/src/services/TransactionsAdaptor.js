import {Adaptor} from "@/services/Adaptor";
import {Transaction} from "@/models/Transaction";

export default class TransactionsAdaptor extends Adaptor {
    constructor(URL) {
        super(URL);
    }

    /**
     * Retrieve all transactions by product ID.
     *
     * @param {string} productId - The ID of the product.
     * @return {Promise<Transaction[] | null>} - A promise that resolves to an array of Transaction objects
     *                                           or null.
     */
    async asyncGetAllByProductId(productId) {
        const options = {
            method: "GET", headers: {"Content-Type": "application/json"},
        }

        const response = await this.fetchJson(this.resourceUrl + "/product/" + productId, options);

        return response ? response.map(transaction => Transaction.fromJson(transaction)) : null;
    }

    /**
     * Retrieves all transactions by warehouse ID asynchronously.
     *
     * @param {string} warehouseId - The ID of the warehouse.
     * @returns {Promise<Array<Transaction> | null>} - A promise that resolves to an array of Transaction objects or
     *                                                 null.
     */
    async asyncGetAllByWarehouseId(warehouseId) {
        const options = {
            method: "GET", headers: {"Content-Type": "application/json"},
        }

        const response = await this.fetchJson(this.resourceUrl + "/warehouse/" + warehouseId, options);

        return response ? response.map(transaction => Transaction.fromJson(transaction)) : null;
    }
}