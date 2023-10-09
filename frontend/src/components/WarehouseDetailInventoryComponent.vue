<template>
  <div v-if="selectedWarehouse">
    <div class="row mt-2 border border-2 rounded border-light-subtle">
      <div class="row justify-content-center">
        <div class="col align-self-center">
          <h3 class="fw-light">Inventory</h3>
        </div>
        <div class="col-auto align-self-center">
          <button class="btn btn-lg btn-success" @click="addProduct()">
            addProduct
          </button>
        </div>
      </div>
      <div class="row">
        <div class="col overflow-auto">
          <div v-for="product in inventory" :key="product.id" class="row border border-2 rounded border-light-subtle">
            <div class="col">
              <h6 class="fw-medium">ProductID {{ product.productId }}</h6>
            </div>
            <div class="col">
              <h6 class="fw-medium">Product Name {{ WarehouseProduct.getProductName(product.productId, products) }}</h6>
            </div>
            <div class="col">
              <h6 class="fw-medium">Current quantity {{
                  ProductTransaction.getCurrentQuantity(product.productId, selectedWarehouse.id, transactions)
                }}</h6>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
  <div v-else>
    <div class="row justify-content-center">
      <div class="col align-self-center">
        <h3 class="fw-light">Select a Warehouse</h3>
      </div>
    </div>
  </div>
</template>

<script>
import {Warehouse} from "@/models/warehouse";
import {WarehouseProduct} from "@/models/WarehouseProduct";
import {ProductTransaction} from "@/models/productTransaction";
// import {Product} from "@/models/product_MERGE_ME";

export default {
  name: "WarehouseDetailInventoryComponent",
  props: {
    selectedWarehouse: {
      type: Object,
      required: true
    },
    warehouses: {
      type: Object,
      required: true
    },
    vendors: {
      type: Array,
      required: true
    },
    products: {
      type: Array,
      required: true
    },
    transactions: {
      type: Array,
      required: true
    }
  },
  data() {
    return {
      inventory: [],
      lastId: 1000,
      isActive: true,
      Warehouse: Warehouse,
      WarehouseProduct: WarehouseProduct,
      ProductTransaction: ProductTransaction
    }
  },
  created() {
    this.inventory = this.createDummyWarehouseInventory()
  },
  methods: {
    createDummyWarehouseInventory() {
      let inventory = []
      for (let i = 0; i < 8; i++) {
        this.lastId = this.lastId + Math.floor(Math.random() * 3) + 1
        inventory.push(WarehouseProduct.createDummyWarehouseProduct(this.selectedWarehouse.id, this.products[i].id))
      }
      return inventory
    },
    addProduct() {
      this.lastId = this.lastId + Math.floor(Math.random() * 3) + 1
      this.inventory.push(
          WarehouseProduct.createDummyWarehouseProduct(this.lastId,
              this.selectedWarehouse.id,
              this.vendors[Math.floor(Math.random() * this.vendors.length)].id)
      )
    },
    removeProduct(product) {
      this.inventory = this.inventory.filter(item => item !== product)
    }
  }
}
</script>


<style scoped>

</style>