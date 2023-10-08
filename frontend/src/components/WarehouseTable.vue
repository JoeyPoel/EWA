<template>
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
        <td class="align-self-center">{{ product.quantity}}</td>
      </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
export default {
  name: "WarehouseTable",
  props: {
    products: {
      type: Array,
    },
  }
}
</script>

<style scoped>

</style>