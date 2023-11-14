<template>
  <div class="bg-light p-3">
    <label for="name">Name</label>
    <input type="text" v-model="teamName" class="form-control">

    <label for="chooseWarehouse">Warehouse</label>

    <select v-model="selectedWarehouse" class="form-control">
      <option v-for="warehouse in warehouses" :key="warehouse.id" :value="warehouse.id">
        {{ warehouse.name }}
      </option>
      <option v-for="warehouse in warehouses" :key="warehouse.id" :value="warehouse.id">
        {{ warehouse.name }}
      </option>
    </select>

    <select v-model="this.selectedWarehouse">
      <option v-for="w in warehouses" :key="w">
        {{w.name}}
      </option>
    </select>

    <button class="btn btn-primary mt-3" @click="editingTeam ? updateTeam() : addTeam()">
      {{ editingTeam ? 'Update' : 'Save' }}
    </button>
  </div>

  <div class="container mt-3">
    <table class="table">
      <thead class="thead-dark">
      <h2>Teams:</h2>
      <tr>
        <th scope="col">Name:</th>
        <th scope="col">Warehouse ID:</th>
        <th scope="col">Actions:</th>
      </tr>
      </thead>
      <tbody class="bg-light">
      <tr v-for="team in teams" :key="team" class="border-bottom">
        <td>{{ team.name }}</td>
        <td>{{ team.warehouseId }}</td>
        <td>
          <button type="button" class="btn btn-dark mx-1" @click="editTeam(team)">Edit</button>
          <button type="button" class="btn btn-danger" @click="deleteTeam(team)">Delete</button>
        </td>
      </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
export default {
  inject: ['teamsService', 'warehousesService'],
  name: 'teamComponent',
  data() {
    return {
      teams: [],
      warehouses: [],
      selectedWarehouse: null,
      teamName: '',
      editingTeam: null,
    };
  },
  async created() {
    this.teams = await this.teamsService.asyncFindAll();
    this.warehouses = await this.warehousesService.asyncFindAll();
  },
  computed: {
  },
  methods: {
    async addTeam() {
      const team = {
        id: Math.floor(Math.random() * 100000),
        name: this.teamName,
        warehouseId: this.selectedWarehouse,
      };
      const savedTeam = await this.teamsService.asyncSave(team);
      if (savedTeam) {
        this.teams.add(savedTeam)
        this.resetForm();
      }
    },
    async editTeam(team) {
      this.editingTeam = team;
      this.teamName = team.name;
      this.selectedWarehouse = await this.warehousesService.asyncFindById(team.warehouseId);
    },
    async updateTeam() {
      if (!this.editingTeam) return;

      const team = {
        id: this.editingTeam.id,
        name: this.teamName,
        warehouseId: this.selectedWarehouse,
      };
      const updatedTeam = await this.teamsService.asyncSave(team);
      if(updatedTeam){
        let index = this.teams.findIndex(t => t.id === updatedTeam.id);
        if (index !== -1) {
          this.teams[index] = updatedTeam;
        }
      }
      this.resetForm();
    },
    async deleteTeam(team) {
      this.teamsService.asyncDeleteById(team.id)
    },
      resetForm() {
      this.editingTeam = null;
      this.teamName = '';
      this.selectedWarehouse = null;
    },
  },
};
</script>

<style scoped>
</style>
