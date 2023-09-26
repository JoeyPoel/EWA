<template>
  <table>
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

export default  {
  name: "WarehouseComponent",
  data() {
    return {
      products: [],
      lastId: 0,
    }
  },
  created() {
    for (let i = 0; i < 8; i++) { // Loop until all products have been added
      this.products.push(
          this.lastId++,
          product.createDummyProduct(this.lastId)
          // Actual product being pushed
      )
    }
  },
  methods: {

    }
}
</script>

<!--<style scoped>-->
<!--table {-->
<!--  width: 100%;-->
<!--}-->

<!--thead {-->
<!--  background-color: orange;-->
<!--  color: white;-->
<!--}-->

<!--tbody {-->
<!--  background-color: white;-->
<!--}-->

<!--tr {-->
<!--  border-bottom: 1px solid #ccc;-->
<!--}-->

<!--th,-->
<!--td {-->
<!--  border-right: 1px solid #ccc;-->
<!--  padding: 8px;-->
<!--}-->

<!--tr td:last-child,-->
<!--tr th:last-child {-->
<!--  border-right: none;-->
<!--}-->

<!--button{-->
<!--  background: yellow;-->
<!--  color: red;-->
<!--  float: right;-->
<!--}-->
<!--</style>-->
