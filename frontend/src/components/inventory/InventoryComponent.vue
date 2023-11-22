<template>
  <div class="container border border-1 rounded-2 border-light-subtle">
    <div class="container-fluid text-light mb-4 pt-4 text-dark">
      <div class="row justify-content-center">
        <div class="col-md-6 col-lg-4 mb-4">
          <label class="form-label" for="selectWarehouse">Choose a warehouse:</label>
          <select id="warehouse-select" v-model="selectedWarehouseId" class="form-control form-control-sm">
            <option v-for="warehouse in warehouses" :key="warehouse.id" :value="warehouse.id">{{
                warehouse.name
              }}
            </option>
          </select>
        </div>
        <div class="col-md-6 col-lg-4 mb-4">
          <label class="form-label" for="searchBar">Search for an item:</label>
          <input id="searchBar" v-model="searchQuery" class="form-control form-control-sm" type="text"
                 @input="handleSearch">
        </div>
        <div class="col-md-6 col-lg-4 mb-4">
          <label class="form-label" for="selectSorting">Choose a sorting system:</label>
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
    <RouterView/>
  </div>

</template>

<script>
export default {
  inject: ['warehousesService', 'inventoryService'],
  name: "WarehouseComponent",
  data() {
    return {
      products: [],
      warehouses: [],
      filteredProducts: [],
      selectedWarehouseId: null,
      selectedSorting: "id",
      searchQuery: "",
    }
  },
  async created() {
    this.warehouses = await this.warehousesService.asyncGetAllWarehouses();
  },
  watch: {
    async $route() {
      this.selectedWarehouseId = this.$route.params.id;
    },
    selectedWarehouseId: function () {
      this.$router.push({name: 'WarehouseInventory', params: {id: this.selectedWarehouseId}});
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
        const products = this.products.filter(product => product.warehouseId === this.selectedWarehouseId); // Only from this warehouse
        this.filteredProducts = products.filter(product => product.name.toLowerCase().includes(searchQuery)); // Filter it on includes searchQuery
      } else {
        this.filteredProducts = this.products.filter(product => product.warehouseId === this.selectedWarehouseId); // Else return the normal product list of this warehouse
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
