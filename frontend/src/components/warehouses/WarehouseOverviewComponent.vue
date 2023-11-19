<template>
  <div class="container mt-3">
    <div class="d-flex justify-content-between mb-3">
      <input type="text" class="form-control w-25" placeholder="Search for name...">
      <button @click="this.onNewWarehouse" class="btn btn-success">Add warehouse</button>
    </div>

    <table class="table table-hover">
      <thead>
      <tr>
<!--        <th scope="col" class="fw-light">ID</th>-->
        <th scope="col">Name</th>
        <th scope="col">Location</th>
        <th scope="col">Address</th>
      </tr>
      </thead>
      <tbody>
      <tr class="border border-1 border-light-subtle" v-for="warehouse in warehouses" :key="warehouse.id"
          @click="selectWarehouse(warehouse)">
<!--        <th scope="row" class="fw-light" >{{ warehouse.id }}</th>-->
        <td>{{ warehouse.name }}</td>
        <td>{{ warehouse.location }}</td>
        <td>{{ warehouse.address }}</td>
      </tr>
      </tbody>
    </table>
    <router-view
        v-if="shouldShowModal"
        :warehouses="warehouses"
        :products="products"
        :transactions="transactions"
        :inventories="inventories"
        :selectedWarehouse="findSelectedWarehouseFromRoute()"
        @close-modal="deselectWarehouse()"
        @add-warehouse="onNewWarehouse()"
    ></router-view>
  </div>
</template>

<script>
import {Warehouse} from '@/models/Warehouse.js';
// import {ProductTransaction} from "@/models/productTransaction";
import {WarehouseProduct} from "@/models/WarehouseProduct";
// import {ProductTransaction} from "@/models/productTransaction";
// import {WarehouseProduct} from "@/models/WarehouseProduct";
// import {Vendor} from "@/models/Vendor";

export default {
  name: "WarehouseOverviewComponent",
  inject: ['warehousesService', 'productsService'],
  components: {},
  data() {
    return {
      warehouses: [],
      products: [],
      inventories: [],
      transactions: [],
      lastId: 10000,
      isActive: true,
      Warehouse: Warehouse
    }
  },
  async created() {
    this.warehouses = await this.warehousesService.asyncFindAll()
    this.products = await this.productsService.asyncFindAll()
    await this.createDummyData()
  },
  computed: {
    shouldShowModal() {
      return this.warehouses !== null && this.products !== null &&
          (this.findSelectedWarehouseFromRoute() !== null || this.$route.name === "add")
    },
  },
  methods: {
    onNewWarehouse(warehouse) {
      // this.lastId = this.lastId + Math.floor(Math.random() * 3) + 1
      // this.warehousesService.asyncSave(warehouse)
      // this.warehouses.push(
      //     warehouse
      // )
      console.log(warehouse)
      this.$router.push("/warehouse/overview/add")
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
    deselectWarehouse() {
      this.$router.push("/warehouse/overview")
      this.selectedWarehouse = null
    },
    // onSaveProduct(savedProduct) {
    //   for (let i = 0; i < this.inventories.length; i++) {
    //     if (this.inventories[i].productId === savedProduct.productId) {
    //       this.inventories[i] = savedProduct
    //       return;
    //     }
    //   }
    // },
    // onRemoveProduct(product) {
    //   console.log(product)
    //   for (let i = 0; i < this.inventories.length; i++) {
    //     if (this.inventories[i].productId === product.productId) {
    //       this.inventories.splice(i, 1)
    //       return;
    //     }
    //   }
    // },
    // onAddProduct(product) {
    //   console.log(product)
    //   this.inventories.push(product)
    // },
    selectWarehouse(warehouse) {
      this.$router.push("/warehouse/overview/" + warehouse.id)
    },
    findSelectedWarehouseFromRoute() {
      const id = parseInt(this.$route.params.id)
      return this.warehouses.find(c => c.id === id)
    },
    async createDummyData(){
      if (this.warehouses.length < 0) return
      if (this.products.length < 0) return
      for (let i = 0; i < this.warehouses.length; i++){
        for (let j = 0; j < this.products.length; j++){
          this.inventories.push(
              WarehouseProduct.createDummyWarehouseProduct(this.warehouses[i].id, this.products[j].id)
          )
          // for (let k = 0; k < 5; k++) {
          //   this.transactions.push(
          //       ProductTransaction.createDummyProductTransaction(this.products[j].id, this.warehouses[i].id)
          //   )
          // }
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