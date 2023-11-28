import {Adaptor} from "@/services/Adaptor";

export default class TransactionsAdaptor extends Adaptor{
    constructor(URL) {
        super(URL);
    }

    async asyncFindAllTransactionsByProductId(productId) {
        const options = {
            method: "GET", headers: {"Content-Type": "application/json"},
        }

        const response = await this.fetchJson(
            this.resourceUrl + "/getAllTransactionsByProductId/" + productId, options);
        if (response) {
            return response;
        }
    }

    async asyncGetAllByWarehouseId(warehouseId) {
        const options = {
            method: "GET", headers: {"Content-Type": "application/json"},
        }

        const response = await this.fetchJson(
            this.resourceUrl + "/getAllTransactionsByWarehouseId/" + warehouseId, options);
        if (response) {
            return response;
        }
    }
}