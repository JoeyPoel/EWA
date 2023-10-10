<template>
  <div class="m-0 border border-2 rounded border-light-subtle">
    <div class="row m-0 mx-2 justify-content-center border border-2 rounded border-light-subtle">
      <new-warehouse-product-component :products="products" :warehouses="warehouses" @add-product="addProduct"/>
    </div>
    <div class="row m-0 justify-content-center">
      <div class="col m-0 align-self-center mx-4">
        <div class="row m-0 justify-content-center">
          <div class="col align-self-center">
            <h3 class="fw-light">Inventory</h3>
          </div>
          <div class="scrollPanel">
            <div v-for="product in inventoryCopy" :key="product.id" class="row m-0 border border-2 rounded
            border-light-subtle
        justify-content-center">
              <div class="col col-6">
                <div class="row">
                  <div class="text-muted col col-5 text-end">Product ID</div>
                  <div class="col col-7 text-start">{{ product.productId }}
                  </div>
                </div>
                <div class="row">
                  <div class="text-muted col col-5 text-end">Product Name</div>
                  <div class="col col-7 text-start">{{
                      WarehouseProduct.getProductName(product.productId, products)
                    }}
                  </div>
                </div>
                <div class="row">
                  <div class="text-muted col col-5 text-end">Current stock</div>
                  <div class="col col-7 text-start">
                    {{
                      ProductTransaction.getCurrentQuantity(product.productId, selectedWarehouse.id, transactions)
                    }}
                  </div>
                </div>
              </div>
              <div class="col col-6 align-self-center">
                <div class="row justify-content-center">
                  <div class="col col-9 align-self-center">
                    <div class="row">
                      <div class="col col-6 align-self-center">
                        <h6 class="fw-medium">Minimum stock level</h6>
                      </div>
                      <div class="col col-6 align-self-center" ref="f">
                        <input class="form-control" type="number" v-model="product.minimumQuantity"/>
                      </div>
                    </div>
                  </div>
                  <div class="col col-3 align-self-center">
                    <div class="row p-1">
                      <button class="btn btn-success" @click="saveProduct(product)">
                        Save
                      </button>
                      <button class="btn btn-danger mt-1" @click="removeProduct(product)">
                        Remove
                      </button>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {Warehouse} from "@/models/warehouse";
import {WarehouseProduct} from "@/models/WarehouseProduct";
import {ProductTransaction} from "@/models/productTransaction";
import NewWarehouseProductComponent from "@/components/NewWarehouseProductComponent.vue";

export default {
  name: "WarehouseDetailInventoryComponent",
  components: {NewWarehouseProductComponent},
  props: {
    selectedWarehouse: {
      type: Object,
      required: true
    },
    warehouses: {
      type: Array,
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
    },
    inventory: {
      type: [Object],
      required: true
    }
  },
  data() {
    return {
      lastId: 1000,
      isActive: true,
      Warehouse: Warehouse,
      WarehouseProduct: WarehouseProduct,
      ProductTransaction: ProductTransaction,
    }
  },
  computed: {
    inventoryCopy() {
      return this.getInventory(true)
    }
  },
  methods: {
    addProduct(product) {
      this.$emit('add-product',product)
    },
    removeProduct(product) {
      this.$emit('remove-product', product)
    },
    saveProduct(product) {
      this.$emit('save-product', product)
    },
    getInventory(copy) {
      let inv = []
      for (let i = 0; i < this.inventory.length; i++) {
        if (this.inventory[i].warehouseId === this.selectedWarehouse.id) {
          inv.push(copy ? WarehouseProduct.copy(this.inventory[i]) : this.inventory[i])
        }
      }
      return inv
    }
  },
}
</script>

<style scoped>
.scrollPanel {
  max-height: 74vh;
  overflow-y: auto;
}
</style>