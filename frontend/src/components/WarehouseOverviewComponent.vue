<template>
      <div class="row px-5 text-center border border-2 border-light-subtle rounded-4 mx-2">
        <div class="col col-2 mt-1">
          <div class="row">
            <button class="btn btn-lg btn-success" @click="onNewWarehouse()">
              New Warehouse
            </button>
          </div>
          <div class="row">
            <div class="col mt-2 overflow-auto border border-2 border-light-subtle rounded-4 scrollPanel align-self-center"
                 ref="scrollPanel">
              <div class="warehouse col col-auto border border-secondary-subtle rounded-2 justify-content-center
              align-self-center"
                   type="button"
                   v-for="warehouse in warehouses" :key="warehouse.id" @click="selectWarehouse(warehouse)"
                   :class="{active:this.findSelectedWarehouseFromRoute() === warehouse}">
                <div class="row justify-content-center">
                  <img class="img rounded col col-4 align-self-center" :src="Warehouse.getImage()" alt="Card image cap">
                  <h6 class="fw-medium col align-self-center">Warehouse {{ warehouse.id }}</h6>
                </div>
                <div class="mt-1">
                  <p class="fw-light">
                    <strong>Name:</strong> {{ warehouse.name }}
                    <br><strong>Location:</strong> {{ warehouse.location }}
                  </p>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="col col-10" v-if="findSelectedWarehouseFromRoute()">
          <router-view :selected-warehouse="findSelectedWarehouseFromRoute()" :warehouses="warehouses"
                       :vendors="vendors" :products="products" :transactions="transactions" :inventories="inventories"
                       @delete="onDelete" @save="onSave" @save-product="onSaveProduct"
                       @remove-product="onRemoveProduct" @add-product="onAddProduct">
          </router-view>
        </div>
        <div class="col col-10 align-self-center" v-else>
          <div class="row justify-content-center">
            <h3 class="fw-light">Select a Warehouse</h3>
          </div>
        </div>
      </div>
</template>

<script>
import {Warehouse} from '@/models/warehouse.js';
import {Vendor} from "@/models/Vendor";
import {Product} from "@/models/product_MERGE_ME";
import {ProductTransaction} from "@/models/productTransaction";
import {WarehouseProduct} from "@/models/WarehouseProduct";

export default {
  name: "WarehouseOverviewComponent",
  components: {},
  data() {
    return {
      warehouses: [],
      vendors: [],
      products: [],
      transactions: [],
      inventories: [],
      lastId: 10000,
      isActive: true,
      Warehouse: Warehouse
    }
  },

  created() {
    this.createDummyData()
  },

  methods: {
    onNewWarehouse() {
      this.lastId = this.lastId + Math.floor(Math.random() * 3) + 1
      this.warehouses.push(
          Warehouse.createDummyWarehouse(this.lastId)
      )
      this.$router.push("/warehouse/overview/" + this.lastId)
      this.$nextTick(() => {
        this.$refs.scrollPanel.scrollLeft = this.$refs.scrollPanel.scrollWidth
      })
    },
    onDelete(warehouse) {
      for (let i = 0; i < this.warehouses.length; i++) {
        if (this.warehouses[i].id === warehouse.id) {
          this.warehouses.splice(i, 1)
          return;
        }
      }
      this.$router.push("/warehouse/overview")
    },
    onSave(warehouse) {
      for (let i = 0; i < this.warehouses.length; i++) {
        if (this.warehouses[i].id === warehouse.id) {
          this.warehouses[i] = warehouse
          return;
        }
      }
    },
    onSaveProduct(savedProduct) {
      for (let i = 0; i < this.inventories.length; i++) {
        if (this.inventories[i].productId === savedProduct.productId) {
          this.inventories[i] = savedProduct
          return;
        }
      }
    },
    onRemoveProduct(product) {
      console.log(product)
      for (let i = 0; i < this.inventories.length; i++) {
        if (this.inventories[i].productId === product.productId) {
          this.inventories.splice(i, 1)
          return;
        }
      }
    },
    onAddProduct(product) {
      console.log(product)
      this.inventories.push(product)
    },
    selectWarehouse(warehouse) {
      this.$router.push("/warehouse/overview/" + warehouse.id)
    },
    findSelectedWarehouseFromRoute() {
      const id = parseInt(this.$route.params.id)
      return this.warehouses.find(c => c.id === id)
    },
    createDummyData(){
      for (let i = 0; i < 8; i++) {
        this.lastId = this.lastId + Math.floor(Math.random() * 3) + 1
        this.warehouses.push(Warehouse.createDummyWarehouse(this.lastId))
        this.vendors.push(Vendor.createDummyVendor(this.lastId))
        this.products.push(Product.createDummyProduct(this.lastId, this.vendors, i))
      }
      for (let i = 0; i < this.warehouses.length; i++){
        for (let j = 0; j < this.products.length; j++){
          this.inventories.push(
              WarehouseProduct.createDummyWarehouseProduct(this.warehouses[i].id, this.products[j].id)
          )
          for (let k = 0; k < 5; k++) {
            this.transactions.push(
                ProductTransaction.createDummyProductTransaction(this.products[j].id, this.warehouses[i].id)
            )
          }
        }
      }
    },
  }
}
</script>

<style scoped>

.active {
  background: lightgrey;
}

.scrollPanel {
  max-height: 90vh;
  overflow-y: auto;
}
</style>