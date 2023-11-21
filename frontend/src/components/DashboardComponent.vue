<template>
  <div class="container">
    <div class="top">
      <div class="top-left">
        <h2>Top Left Content</h2>
        <p>This is the content for the Top Left section.</p>
      </div>
      <div class="top-right">
        <div class="top-right-top">
          <h2>Top Right Top Content</h2>
          <p>This is the content for the Top Right Top section.</p>
        </div>
        <div class="top-right-bottom">
          <div class="message-board">
            <h2>Message Board</h2>
            <p>This is the message board content.</p>
          </div>

        </div>
      </div>
    </div>
    <div class="bottom">
      <div class="bottom-left">
        <h2>Products sorted on stock</h2>
        <div class="inventory">
          <div class="selectWarehouse">
            <select id="warehouse-select" class="form-control form-control-sm" v-model="selectedWarehouse">
              <option :value="null">All warehouses</option>
              <option v-for="warehouse in warehouses" :value="warehouse.id" :key="warehouse.id">{{ warehouse.name }}</option>
            </select>
          </div>
          <div class="inventoryTable">
            <table class="Table w-100">
              <thead>
              <tr>
                <th scope="col">Warehouse:</th>
                <th scope="col">Name:</th>
                <th scope="col">Stock level:</th>
              </tr>
              </thead>
              <tbody class="bg-light">
              <tr v-for="product in filteredProducts" :key="product.id" class="border-bottom">
                <td class="align-self-center">{{ product.warehouseId }}</td>
                <td class="align-self-center">{{ product.name }}</td>
                <td class="align-self-center">{{ product.quantity}}</td>
              </tr>
              </tbody>
            </table>
          </div>
        </div>
      </div>
      <div class="bottom-right">
        <h2>Projects sorted on date</h2>
        <div class="projects">
          <div class="selectedTeam">
            <select id="team-select" class="form-control form-control-sm" v-model="selectedTeam">
              <option :value="null">All teams</option>
              <option v-for="team in teams" :value="team.id" :key="team.id">{{ team.name }}</option>
            </select>
          </div>
          <div class="projectsTable">
            <table class="Table w-100">
              <thead>
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
    </div>
  </div>
</template>

<script>
export default {
  inject: ['productsService', 'warehousesService', 'teamsService', 'projectsService'],
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
      if (this.selectedWarehouse === null || this.selectedWarehouse === '') {
        this.filteredProducts = this.products; // Display all products when selectedWarehouse is null or empty string
      } else {
        this.filteredProducts = this.products.filter(product => product.warehouseId === this.selectedWarehouse);
      }
    },
    selectedTeam: function () {
      if (this.selectedTeam === null || this.selectedTeam === '') {
        this.filteredProjects = this.projects; // Display all projects when selectedTeam is null or empty string
      } else {
        this.filteredProjects = this.projects.filter(project => project.team === this.team);
      }
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
.container {
  display: flex;
  flex-direction: column;
  height: 100vh;
}

.top {
  display: flex;
  flex: 1;
}

.bottom {
  display: flex;
  flex: 1;
}

.top-left {
  flex: 1;
}

.top-right {
  display: flex;
  flex: 1;
  flex-direction: column;
}

.top-right-top,
.top-right-bottom{
  flex: 1;
}

.bottom-left,
.bottom-right {
  flex: 1;
}

.projectsTable,
.inventoryTable{
  max-height: 40vh;
  overflow-y: auto;
}
</style>