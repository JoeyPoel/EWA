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

    static get getCategories() {
        let categories = [];
        Object.keys(this.CATEGORY).forEach(key => {
            categories.push({title: this.CATEGORY[key], value: key});
        });
        return categories;
    }
}