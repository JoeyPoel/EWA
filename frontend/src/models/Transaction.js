export class Transaction {
    constructor(id, quantity, transactionType, transactionDate, productId, warehouseId,
                transferFromWarehouseId, projectId) {
        this.id = id;
        this.quantity = quantity;
        this.transactionType = transactionType;
        this.transactionDate = transactionDate;
        this.productId = productId;
        this.warehouseId = warehouseId;
        this.transferFromWarehouseId = transferFromWarehouseId;
        this.projectId = projectId;
    }

    static get CATEGORY() {
        return {
            ORDER: 'Order',
            PROJECT_MATERIAL: 'Project Material',
            TRANSFER: 'Transfer',
            ADJUSTMENT: 'Adjustment',
            RETURN: 'Return',
            DAMAGED: 'Damaged',
            LOST: 'Lost',
            EXTRA_MATERIAL_FOR_PROJECT: 'Extra Project Material',
            OTHER: 'Other'
        }
    }

    static get CATEGORIES() {
        let categories = [];
        for (let category in Transaction.CATEGORY) {
            categories.push({ value: category, text: Transaction.CATEGORY[category]});
        }
        return categories;
    }
}