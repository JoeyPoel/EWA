export class WarehouseProduct {
    constructor(warehouseId, productId, minimumQuantity) {
        this.warehouseId = warehouseId;
        this.productId = productId;
        this.minimumQuantity = minimumQuantity;
    }

    static createDummyWarehouseProduct(warehouseId, productId){
        return new WarehouseProduct(warehouseId, productId, Math.floor(Math.random() * 100) + 1,)
    }

    static getProductName(productId, products){
        for(let i = 0; i < products.length; i++){
            if(products[i].id === productId){
                return products[i].name;
            }
        }
        return "Product not found";
    }

    static copy(warehouseProduct){
        return new WarehouseProduct(warehouseProduct.warehouseId, warehouseProduct.productId,
            warehouseProduct.minimumQuantity);
    }
}