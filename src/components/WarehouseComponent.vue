<template>
  <div class="bg-primary">
    <label for="selectWarehouse" class="px-5"><h4>Choose a warehouse:</h4></label>
    <select id="selectWarehouse">
      <option v-for="warehouse in warehouses" :key="warehouse.id">{{warehouse.name}}</option>
    </select>
    <label for="searchBar" class="px-5"><h4>Search for an item:</h4></label>
    <input type="text" id="searchBar">
    <label for="selectSorting" class="px-5"><h4>Choose a sorting system:</h4></label>
    <select id="selectSorting">
      <option value="id">Sort on ID</option>
      <option value="name">Sort on name</option>
      <option value="stock">Sort on Stock level</option>
    </select>
  </div>
  <table class="w-100">
    <thead class="bg-primary">
    <tr>
      <th scope="col">Id:</th>
      <th scope="col">Name:</th>
      <th scope="col">Quantity:</th>
    </tr>
    </thead>
    <tbody class="bg-light">
    <tr v-for="product in products" :key="product.id" class="border-bottom">
      <td>{{ product.id }}</td>
      <td>{{ product.name }}</td>
      <td class="align-self-center">{{ product.quantity }}</td>
    </tr>
    </tbody>
  </table>
</template>

<script>
import {product} from '@/models/product.js';
import {warehouse} from '@/models/warehouse.js';

export default  {
  name: "WarehouseComponent",
  data() {
    return {
      products: [],
      warehouses: [],
      lastId: 1,
    }
  },
  created() {
    for (let i = 0; i < 8; i++) {
      this.products.push(
          product.createDummyProduct(this.lastId)
      );

      this.warehouses.push(
          warehouse.createDummyWarehouse(this.lastId)
      );

      this.lastId++; // Increment lastId only once for each iteration
    }
  },
  methods: {

    }
}
</script>
