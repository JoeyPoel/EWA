<template>
  <div class="bg-light p-3">
    <label for="name">Name</label>
    <input type="text" v-model="teamName" class="form-control">

    <label for="chooseWarehouse">Warehouse</label>
    <select v-model="selectedWarehouse" class="form-control">
      <option v-for="warehouse in warehouses" :key="warehouse.id" :value="warehouse.id">
        {{ warehouse.name }}
      </option>
    </select>

    <button class="btn btn-primary mt-3" @click="addTeam">
      Save
    </button>
  </div>

  <div class="container mt-3">
    <table class="table">
      <thead class="thead-dark">
      <h2>Teams:</h2>
      <tr>
        <th scope="col">Name:</th>
        <th scope="col">Warehouse ID:</th>
      </tr>
      </thead>
      <tbody class="bg-light">
      <tr v-for="team in teams" :key="team" class="border-bottom">
        <td>{{ team.name }}</td>
        <td>{{ team.warehouseId }}</td>
        <button type="button" class="btn btn-dark">Edit</button>
      </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
export default {
  inject: ['teamsService', 'warehousesService'],
  name: "teamComponent",
  data() {
    return {
      teams: [],
      warehouses: [],
      selectedWarehouse: null,
      teamName: "",
    };
  },
  async created() {
    this.teams = await this.teamsService.asyncFindAll();
    this.warehouses = await this.warehousesService.asyncFindAll();
  },
  methods: {
    async addTeam() {
      const team = {
        name: this.teamName,
        warehouseId: this.selectedWarehouse,
      };
      await this.teamsService.asyncSave(team);
    },
  },
};
</script>

<style scoped>
</style>
