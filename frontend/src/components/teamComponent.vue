<template>
  <div class="bg-light p-3">
    <label for="name">Name</label>
    <input v-if="!editingTeam" type="text" v-model="teamName" class="form-control">

    <label for="chooseWarehouse">Warehouse</label>
    <select v-if="!editingTeam" v-model="selectedWarehouse" class="form-control">
      <option v-for="warehouse in warehouses" :key="warehouse.id" :value="warehouse.id">
        {{ warehouse.name }}
      </option>
    </select>

    <button class="btn btn-primary mt-3" @click="editingTeam ? updateTeam() : addTeam">
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
          <button type="button" class="btn btn-dark" @click="editTeam(team)">Edit</button>
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
  methods: {
    async addTeam() {
      const team = {
        name: this.teamName,
        warehouseId: this.selectedWarehouse,
      };
      await this.teamsService.asyncSave(team);
      this.resetForm();
    },
    editTeam(team) {
      this.editingTeam = team;
      this.teamName = team.name;
      this.selectedWarehouse = this.warehousesService.asyncFindById(team.warehouseId);
    },
    async updateTeam() {
      if (!this.editingTeam) return;

      const updatedTeam = {
        id: this.editingTeam.id, // Assuming teams have an id property
        name: this.teamName,
        warehouseId: this.selectedWarehouse,
      };

      await this.teamsService.asyncSave(updatedTeam);
      this.resetForm();
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
