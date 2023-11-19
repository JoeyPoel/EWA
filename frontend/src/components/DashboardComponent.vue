<template>
  <div class="Top">
    <div class="TopLeft">
      <div>

      </div>
    </div>
    <div class="TopRight">
      <div>

      </div>
      <div class="MessageBoard">

      </div>
    </div>
  </div>
  <div class="Bottom">
    <div class="Inventory">
      <div class="SelectWarehouse">
        <select id="warehouse-select" class="form-control form-control-sm" v-model="selectedWarehouse">
          <option v-for="warehouse in warehouses" :value="warehouse.id" :key="warehouse.id">{{ warehouse.name }}</option>
        </select>
      </div>
      <div class="InventoryTable">
        <table class="Table">
          <thead class="thead-dark">
          <tr>
            <th scope="col">Name:</th>
            <th scope="col">Stock level:</th>
          </tr>
          </thead>
          <tbody class="bg-light">
          <tr v-for="product in filteredProducts" :key="product.id" class="border-bottom">
            <td>{{ product.name }}</td>
            <td class="align-self-center">{{ product.quantity}}</td>
          </tr>
          </tbody>
        </table>
      </div>
    </div>
    <div class="Projects">
      <div class="SelectedTeam">
        <select id="team-select" class="form-control form-control-sm" v-model="selectedTeam">
          <option v-for="team in teams" :value="team.id" :key="team.id">{{ team.name }}</option>
        </select>
      </div>
      <div class="InventoryTable">
        <table class="Table">
          <thead class="thead-dark">
          <tr>
            <th scope="col">Team:</th>
            <th scope="col">Status:</th>
            <th scope="col">Date:</th>
          </tr>
          </thead>
          <tbody class="bg-light">
          <tr v-for="project in filteredProjects" :key="project.id" class="border-bottom">
            <td class="align-self-center">{{ project.team }}</td>
            <td class="align-self-center">{{ project.status}}</td>
            <td class="align-self-center">{{ project.date}}</td>
          </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  inject: ['productsService', 'warehousesService, teamsService, projectsService'],
  name: "DashboardComponent",
  data() {
    return {
      selectedWarehouse: null,
      warehouses: [],
      selectedTeam: null,
      teams: [],
      products: [],
      filteredProducts: [],
      projects: [],
      filteredProjects: []
    }
  },
  async created() {
    this.products = await this.productsService.asyncFindAll();
    this.filteredProducts = this.products;
    console.log(this.products)
    this.sortInventoryByStockLevel()
    this.teams = await this.teamsService.asyncFindAll();
    this.warehouses = await this.warehousesService.asyncFindAll();
    // this.projects = await this.projectsService.asyncFindAll();
    this.sortProjectsByDate()
  },
  watch: {
    selectedWarehouse: function () {
      this.filteredProducts = this.products.filter(product => product.warehouseId === this.selectedWarehouse);
      },
  },
  methods: {
    sortInventoryByStockLevel(){
      this.filteredProducts.sort((a, b) => a.quantity - b.quantity);
    },
    sortProjectsByDate() {
      this.projects.sort((a, b) => {
        return new Date(a.date) - new Date(b.date);
      })
    },
  }
}
</script>

<style scoped>

</style>