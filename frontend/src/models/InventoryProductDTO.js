export class InventoryProductDTO {
   constructor(productId, warehouseId, name, description, price, quantity) {
       this.productId = productId;
       this.warehouseId = warehouseId;
       this.name = name;
       this.description = description;
       this.price = price;
       this.quantity = quantity;
   }
}