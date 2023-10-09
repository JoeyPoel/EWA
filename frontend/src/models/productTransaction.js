export class ProductTransaction {
    static counter = 0;

    constructor(productId, warehouseId, sender, quantity, type, date, note) {
        this.id = ProductTransaction.counter++;
        this.productId = productId
        this.sender = sender;
        this.warehouseId = warehouseId;
        this.quantity = quantity;
        this.type = type;
        this.date = date;
        this.note = note;
    }

    static getTransactionTypeList() {
        return ["Inbound", "Outbound", "Transfer", "Adjustment", "Initial", "Return", "Scrap", "Lost"]
    }

    static createDummyProductTransaction(productId, warehouseId) {
        return new ProductTransaction(productId, warehouseId,
            "Dummy Sender", Math.floor(Math.random() * 10) + 1, "Inbound",
            new Date().toISOString().slice(0, 10), "Dummy Note")
    }

    static getCurrentQuantity(productId, warehouseId, transactions) {
        let quantity = 0;
        for (let i = 0; i < transactions.length; i++) {
            // console.log(transactions[i])
            // console.log(transactions[i].productId, productId, transactions[i].warehouseId, warehouseId)
            if (transactions[i].productId === productId && transactions[i].warehouseId === warehouseId) {
                quantity += transactions[i].quantity;
            }
        }
        return quantity;
    }
}