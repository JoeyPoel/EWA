<template>
  <div class="container-fluid bg-dark text-light mb-4 pt-4">
    <div class="row justify-content-center">
      <div class="col-md-6 col-lg-4 mb-4">
        <label for="selectWarehouse"><h4>Choose a warehouse:</h4></label>
        <select id="selectWarehouse" class="form-control form-control-sm">
          <option v-for="warehouse in warehouses" :key="warehouse.id">{{ warehouse.name }}</option>
        </select>
      </div>
      <div class="col-md-6 col-lg-4 mb-4">
        <label for="searchBar"><h4>Search for an item:</h4></label>
        <input type="text" id="searchBar" class="form-control form-control-sm">
      </div>
      <div class="col-md-6 col-lg-4 mb-4">
        <label for="selectSorting"><h4>Choose a sorting system:</h4></label>
        <select id="selectSorting" v-model="selected" class="form-control form-control-sm">
          <option disabled value="">Please select one</option>
          <option value="id" >Sort on ID</option>
          <option value="name">Sort on name</option>
          <option value="stock">Sort on Stock level</option>
        </select>
      </div>
    </div>
  </div>
  <div class="container">
    <table class="table">
      <thead class="thead-dark">
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
  </div>
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
      selected: null,
    }
  },
  created() {
    for (let i = 0; i < 5; i++) {
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

    },
  watch: {
    selected: function (){
      if(this.selected === "id"){
      this.products.sort((a, b) => a.id - b.id);
      }

      if(this.selected === "name"){
        this.products.sort((a, b) => {
          const nameA = a.name.toUpperCase(); // ignore upper and lowercase
          const nameB = b.name.toUpperCase(); // ignore upper and lowercase
          if (nameA < nameB) {
            return -1;
          }
          if (nameA > nameB) {
            return 1;
          }
          // names must be equal
          return 0;
        });
      }

      if(this.selected === "stock"){
      this.products.sort((a, b) => a.quantity - b.quantity);
      }
    }
  }
}
</script>
