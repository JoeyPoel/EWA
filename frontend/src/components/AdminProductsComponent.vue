<template>
<!--  TODO ADD STYLING -->
  <div>
    <label for="id">Id</label>
    <input type="text" id="id" class="form-control">
    <label for="name">Name</label>
    <input type="text" id="name" class="form-control">
    <label for="quantity">Quantity</label>
    <input type="number" id="quantity" class="form-control">
    <label for="chooseWarehouse">Warehouse</label>
    <select name="chooseWarehouse" id="chooseWarehouse" class="form-select">
      <option v-for="warehouse in warehouses" :value="warehouse.id" :key="warehouse.id">{{ warehouse.name }}</option>
    </select>
    <button  class="btn btn-primary">
      Save
    </button>
  </div>
  <div class="container">
    <table class="table">
      <thead class="thead-dark">
      <tr>
        <th scope="col">Name:</th>
      </tr>
      </thead>
      <tbody class="bg-light">
      <tr v-for="product in products" :key="product" class="border-bottom">
        <td>{{ product }}</td>
        <button type="button" class="btn btn-dark">Edit</button>
      </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
export default {
name: "AdminProducts",
  data(){
    return{
      products: {
        type: Array,
      },
      warehouses: {
        type: Array,
      },
    }
  },
  created() {
    fetch('http://localhost:8090/products/getAllTypes')
        .then(response => response.json())
        .then(data => {
          console.log(data)
          this.products = data;
        })
        .catch(error => {
          console.error('Error fetching data', error);
        });

    fetch('http://localhost:8090/warehouses/getAll')
        .then(response => response.json())
        .then(data => {
          this.warehouses = data;
        })
        .catch(error => {
          console.error('Error fetching data', error);
        });
  },
  methods(){

  }
}
</script>

<style scoped>

</style>