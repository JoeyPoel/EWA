import {Adaptor} from "@/services/Adaptor";

export default class TransactionsAdaptor extends Adaptor{
    constructor(URL) {
        super(URL);
    }

    async asyncFindAllTransactionsByProductIdPaginated(productId, pageSettings) {
        const options = {
            method: "POST", headers: {"Content-Type": "application/json"}, body: JSON.stringify(pageSettings)
        }

        const response = await this.fetchJson(this.resourceUrl + "/getAllTransactionsByProductIdPaginated/"
            + productId, options);
        if (response) {
            console.log(response);
            return response;
        }
    }
}