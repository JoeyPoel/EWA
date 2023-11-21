<template>
  <div class="bg-light p-3">
    <label for="name">Name</label>
    <input type="text" v-model="teamName" class="form-control">

    <label for="chooseWarehouse">Warehouse</label>

    <select v-model="selectedWarehouse" class="form-control">
      <option v-for="warehouse in warehouses" :key="warehouse.id" :value="warehouse">
        {{ warehouse.name }}
      </option>
    </select>

    <button class="btn btn-primary mt-3" @click="editingTeam ? updateTeam() : addTeam()">
      {{ editingTeam ? 'Update' : 'Save' }}
    </button>
  </div>

  <div class="container mt-3">
    <div class="table-wrapper">
      <table class="table">
        <thead class="thead-dark">
        <tr>
          <th scope="col">Name:</th>
          <th scope="col">Warehouse ID:</th>
          <th scope="col">Actions:</th>
        </tr>
        </thead>
        <tbody class="bg-light team-list">
        <tr v-for="team in teams" :key="team.id" class="border-bottom">
          <td>{{ team.name }}</td>
          <td>{{ team.warehouseId }}</td>
          <td>
            <button type="button" class="btn btn-dark mx-1" @click="editTeam(team)">Edit</button>
<!--            TODO: TEAMS CAN'T BE DELETED WHEN THEY ARE STILL CONNECTED TO A PROJECT-->
            <button type="button" class="btn btn-danger" @click="deleteTeam(team)">Delete</button>
          </td>
        </tr>
        </tbody>
      </table>
    </div>
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
    this.warehouses = await this.warehousesService.asyncGetAllWarehouses();
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
      console.log(savedTeam)
      if (savedTeam) {
        this.teams.add(savedTeam)
        this.resetForm();
      }
    },
    async editTeam(team) {
      this.editingTeam = team;
      this.teamName = team.name;
      this.selectedWarehouse = await this.warehousesService.asyncFindById(team.warehouse.id);
    },
    async updateTeam() {
      if (!this.editingTeam) return;

      const team = {
        id: this.editingTeam.id,
        name: this.teamName,
        warehouse: this.selectedWarehouse,
      };
      const updatedTeam = await this.teamsService.asyncUpdateTeam(this.editingTeam.id, team);
      if(updatedTeam){
        let index = this.teams.findIndex(t => t.id === updatedTeam.id);
        if (index !== -1) {
          this.teams[index] = updatedTeam;
        }
      }
      this.resetForm();
    },
    async deleteTeam(team) {
      await this.teamsService.asyncDeleteById(team.id)
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
.table-wrapper {
  max-height: 450px;
  overflow-y: auto;
}
</style>