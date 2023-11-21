<template>
  <div class="container mt-3">
    <div class="d-flex justify-content-between mb-3">
      <input class="form-control w-25" placeholder="Search for name..." type="text">
      <button class="btn btn-success" @click="this.onNewWarehouse">Add warehouse</button>
    </div>
    <table class="table table-hover">
      <thead>
      <tr>
        <th class="fw-light" scope="col">ID</th>
        <th scope="col">Name</th>
        <th scope="col">Location</th>
        <th scope="col">Address</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="warehouse in warehouses" :key="warehouse.id" class="border border-1 border-light-subtle"
          @click="selectWarehouse(warehouse)">
        <th class="fw-light" scope="row">{{ warehouse.id }}</th>
        <td>{{ warehouse.name }}</td>
        <td>{{ warehouse.location }}</td>
        <td>{{ warehouse.address }}</td>
      </tr>
      </tbody>
    </table>
    <router-view
        v-if="shouldShowModal"
        :selectedWarehouse="findSelectedWarehouseFromRoute()"
        @close-modal="deselectWarehouse()"
        @add-warehouse="onNewWarehouse()"
        @updated-warehouse="onSave"
        @warehouse-added="handleAddedWarehouse"
    ></router-view>
  </div>
</template>
<script>
import {Warehouse} from '@/models/Warehouse.js';

export default {
  name: "WarehouseOverviewComponent",
  inject: ['warehousesService'],
  components: {},
  data() {
    return {
      warehouses: [],
      lastId: 10000,
      isActive: true,
      Warehouse: Warehouse
    }
  },
  async mounted() {
    this.warehouses = await this.warehousesService.asyncGetAllWarehouses()
  },
  computed: {
    shouldShowModal() {
      return this.warehouses !== null && this.products !== null &&
          (this.findSelectedWarehouseFromRoute() !== null || this.$route.name === "add")
    },
  },
  methods: {
    onNewWarehouse() {
      this.$router.push("/warehouse/overview/add")
    },
    async deselectWarehouse() {
      this.$router.push("/warehouse/overview");
      this.warehouses = await this.warehousesService.asyncGetAllWarehouses();
      this.selectedWarehouse = null;
    },
    selectWarehouse(warehouse) {
      this.$router.push("/warehouse/overview/" + warehouse.id)
    },
    findSelectedWarehouseFromRoute() {
      const id = parseInt(this.$route.params.id);
      return this.warehouses.find(c => c.id === id);
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