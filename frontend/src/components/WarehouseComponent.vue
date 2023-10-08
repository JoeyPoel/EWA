<template>
  <div class="container-fluid bg-dark text-light mb-4 pt-4">
    <div class="row justify-content-center">
      <div class="col-md-6 col-lg-4 mb-4">
        <label for="selectWarehouse"><h4>Choose a warehouse:</h4></label>
        <select id="warehouse-select" class="form-control form-control-sm" v-model="selectedWarehouse">
          <option v-for="warehouse in warehouses" :value="warehouse.id" :key="warehouse.id">{{ warehouse.name }}</option>
        </select>
      </div>
      <div class="col-md-6 col-lg-4 mb-4">
        <label for="searchBar"><h4>Search for an item:</h4></label>
        <input type="text" id="searchBar" class="form-control form-control-sm" v-model="searchQuery" @input="handleSearch">
      </div>
      <div class="col-md-6 col-lg-4 mb-4">
        <label for="selectSorting"><h4>Choose a sorting system:</h4></label>
        <select id="selectSorting" v-model="selectedSorting" class="form-control form-control-sm">
          <option value="id">Sort on ID</option>
          <option value="reverse id">Reverse sort on ID</option>
          <option value="name">Sort on name</option>
          <option value="reverse name">Reverse sort on name</option>
          <option value="stock">Sort on Stock level</option>
          <option value="reverse stock">Reverse sort on Stock level</option>
        </select>
      </div>
    </div>
  </div>
  <div class="container" v-if="selectedWarehouse === null">
    <p class="text-danger">Please select a warehouse.</p>
  </div>
  <warehouse-table v-else :products="filteredProducts"></warehouse-table>
</template>

<script>
import {product} from '@/models/product.js';
import {warehouse} from '@/models/warehouse.js';
import WarehouseTable from "@/components/WarehouseTable";

export default {
  name: "WarehouseComponent",
  components: {WarehouseTable},
  data() {
    return {
      products: [], // Normal product list
      warehouses: [],
      filteredProducts: [], // List of products filtered on name by searchQuery
      selectedWarehouse: null,
      selectedSorting: "id",
      searchQuery: ""
    }
  },
  created() {
    for (let i = 0; i < 5; i++) {
      this.warehouses.push(
          warehouse.createDummyWarehouse(i + 1)
      );

      for (let j = 0; j < 5; j++) {
        this.products.push(product.createDummyProduct(j + 1, i + 1))
      }
    }
  },
  watch: {
    selectedWarehouse: function () {
      // Whenever selectedWarehouse changes, set filteredProducts to this.products
      this.filteredProducts = this.products.filter(product => product.warehouseId === this.selectedWarehouse);
      this.searchQuery = "";
      this.selectedSorting = "id"
    },
    selectedSorting: function () {
      if (this.selectedSorting === "id") {
        this.idSorting();
      }
      if (this.selectedSorting === "reverse id") {
        this.idSorting();
        this.filteredProducts.reverse();
      }
      if (this.selectedSorting === "name") {
        this.nameSorting();
      }
      if (this.selectedSorting === "reverse name") {
        this.nameSorting();
        this.filteredProducts.reverse();
      }
      if (this.selectedSorting === "stock") {
        this.stockLevelSorting();
      }
      if (this.selectedSorting === "reverse stock") {
        this.stockLevelSorting();
        this.filteredProducts.reverse();
      }
    },
  },
  methods: {
    handleSearch() {
      const searchQuery = this.searchQuery.toLowerCase().trim();
      if (searchQuery !== "") { // If searchQuery is not null
        const products = this.products.filter(product => product.warehouseId === this.selectedWarehouse); // Only from this warehouse
        this.filteredProducts = products.filter(product => product.name.toLowerCase().includes(searchQuery)); // Filter it on includes searchQuery
      } else {
        this.filteredProducts = this.products.filter(product => product.warehouseId === this.selectedWarehouse); // Else return the normal product list of this warehouse
      }
    },
    idSorting() {
      this.filteredProducts.sort((a, b) => a.id - b.id);
    },
    nameSorting() {
      this.filteredProducts.sort((a, b) => {
        const nameA = a.name.toUpperCase();
        const nameB = b.name.toUpperCase();
        if (nameA < nameB) {
          return -1;
        }
        if (nameA > nameB) {
          return 1;
        }
        return 0;
      });
    },
    stockLevelSorting() {
      this.filteredProducts.sort((a, b) => a.quantity - b.quantity);
    },
  },
}
</script>
