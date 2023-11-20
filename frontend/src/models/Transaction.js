export class Transaction {
    constructor(id, quantity, product, transactionDate, warehouse, project, transferFrom, order) {
        this.id = id;
        this.quantity = quantity;
        this.product = product;
        this.transactionDate = transactionDate;
        this.warehouse = warehouse;
        this.project = project;
        this.transferFrom = transferFrom;
        this.order = order;
    }
}