<template>
  <div class="bg-light p-3">
    <label for="name">Name</label>
    <input type="text" v-model="productName" class="form-control">
    <label for="productDescription">Description</label>
    <input type="text" v-model="productDescription" class="form-control">
    <label for="chooseWarehouse">Warehouse</label>
    <div v-for="warehouse in warehouses" :key="warehouse.id" class="dropdown-item">
      <label>
        <input type="checkbox" :value="warehouse.id" v-model="selectedWarehouses">
        {{ warehouse.name }}
      </label>
      <br />
      <label for="quantity" v-if="selectedWarehouses.includes(warehouse.id)">Quantity</label>
      <input
          type="number"
          class="form-control"
          v-model="quantityPerWarehouse[warehouses.indexOf(warehouse)]"
          v-if="selectedWarehouses.includes(warehouse.id)"
      />
    </div>
    <button class="btn btn-primary mt-3" @click="addProduct">
      Save
    </button>
  </div>
  <div class="container mt-3">
    <table class="table">
      <thead class="thead-dark">
      <h2>Products:</h2>
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
  data() {
    return {
      products: [],
      warehouses: [],
      selectedWarehouses: [],
      productName: "",
      productDescription: "",
      quantityPerWarehouse: [],
    };
  },
  created() {
    fetch("http://localhost:8090/products/getAllTypes")
        .then((response) => response.json())
        .then((data) => {
          this.products = data;
        })
        .catch((error) => {
          console.error("Error fetching data", error);
        });

    fetch("http://localhost:8090/warehouses/getAll")
        .then((response) => response.json())
        .then((data) => {
          this.warehouses = data;
        })
        .catch((error) => {
          console.error("Error fetching data", error);
        });
  },
  methods: {
    addProduct() {
      if(this.selectedWarehouses.length === 0){
        console.log("Please select at least 1 warehouse.")
      }

      const selectedWarehouseData = [];
      this.selectedWarehouses.forEach((warehouseId, index) => {
        selectedWarehouseData.push({
          warehouseId: warehouseId,
          id: 0,
          name: this.productName,
          description: this.productDescription,
          quantity: this.quantityPerWarehouse[index] || 0, // TODO FIX THIS
        });
      });

      const productData = {
        productRequests: selectedWarehouseData,
      };
      console.log(productData)
      const requestOptions = {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(productData),
      };

      fetch("http://localhost:8090/products/addProduct", requestOptions)
          .then((response) => response.json())
          .then((data) => {
            // handle the response data here
            console.log(data);
          })
          .catch((error) => {
            console.error("Error adding product", error);
          });
    },
  },
};
</script>

<style scoped>
/* Add any necessary custom styling here */
</style>