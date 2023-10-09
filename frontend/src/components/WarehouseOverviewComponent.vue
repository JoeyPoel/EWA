<template>
  <div class="col container-fluid">
    <div class="row">
      <div class="col px-5 text-center border border-2 border-light-subtle rounded-4 mx-2">
        <div class="row pt-1">
          <div class="col col-auto align-self-center">
            <button class="btn btn-lg btn-success" @click="onNewWarehouse()">
              New Warehouse
            </button>
          </div>
          <div class="col col-xl-10">
            <div class="row flex-nowrap overflow-auto p-2 border border-2 border-light-subtle rounded-4"
                 ref="scrollPanel">
              <div class="warehouse col col-2 border border-secondary-subtle rounded-2 justify-content-center mx-1 "
                   type="button"
                   v-for="warehouse in warehouses" :key="warehouse.id" @click="selectWarehouse(warehouse)"
                   :class="{active:this.findSelectedWarehouseFromRoute() === warehouse}">
                <div class="row justify-content-center">
                  <img class="card-img rounded" :src="Warehouse.getImage()" alt="Card image cap">
                </div>
                <div class="mt-1">
                  <h6 class="fw-medium">Warehouse {{ warehouse.id }}</h6>
                  <p class="fw-light">
                    <strong>Name:</strong> {{ warehouse.name }}
                    <br><strong>Location:</strong> {{ warehouse.location }}
                  </p>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="row" v-if="findSelectedWarehouseFromRoute()">
          <router-view :selected-warehouse="findSelectedWarehouseFromRoute()" :warehouses="warehouses"
                       :vendors="vendors" :products="products" :transactions="transactions"
                       @delete="onDelete" @save="onSave"></router-view>
        </div>
        <div class="row" v-else>
          <div class="col align-self-center">
            <h3 class="fw-light">Select a Warehouse</h3>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {Warehouse} from '@/models/warehouse.js';
import {Vendor} from "@/models/Vendor";
import {Product} from "@/models/product_MERGE_ME";
import {ProductTransaction} from "@/models/productTransaction";

export default {
  name: "WarehouseOverviewComponent",
  components: {},
  data() {
    return {
      warehouses: [],
      vendors: [],
      products: [],
      transactions: [],
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
      this.warehouses = this.warehouses.filter(c => c !== warehouse)
    },
    onSave(warehouse) {
      this.warehouses = this.warehouses.map(c => c === warehouse ? warehouse : c)
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
      this.transactions = this.createDummyTransactions()
    },
    createDummyTransactions() {
      let transactions = []
      for (let i = 0; i < this.warehouses.length; i++){
        for (let j = 0; j < this.products.length; j++){
          for (let k = 0; k < 2; k++){
            transactions.push(
                ProductTransaction.createDummyProductTransaction(this.products[j].id, this.warehouses[i].id)
            )
          }
        }
      }
      return transactions
    },
  }
}
</script>

<style scoped>
.card-img {
  width: 50%;
  height: 50%;
}

.warehouse {
  min-width: 100px;
  min-height: fit-content;
  max-width: 200px;
  max-height: 250px;
}

.active {
  background: lightgrey;
}
</style>