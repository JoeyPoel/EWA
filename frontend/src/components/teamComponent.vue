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

    <button class="btn btn-primary mt-3" @click="addTeam()">
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
            <button type="button" class="btn btn-dark mx-1" @click="editTeam(team)">
              Edit
            </button>
            <button type="button" class="btn btn-danger" @click="deleteTeam(team)">Delete</button>
          </td>
        </tr>
        </tbody>
      </table>
    </div>

    <!-- EditTeamModal component -->
    <EditTeamModal v-if="showModal" :team="editingTeam" :warehouses="warehouses" @save="saveEditedTeam" @close="closeEditModal" />
  </div>
</template>

<script>
import EditTeamModal from './EditTeamModal.vue'; // Import the modal component

export default {
  inject: ['teamsService', 'warehousesService'],
  name: 'teamComponent',
  data() {
    return {
      teams: [],
      warehouses: [],
      selectedWarehouse: null,
      teamName: '',
      editingTeam: false,
      team: null,
      showModal: false, // Flag to control modal visibility
    };
  },
  components: {
    EditTeamModal, // Register EditTeamModal component
  },
  async created() {
    this.teams = await this.teamsService.asyncFindAll();
    this.warehouses = await this.warehousesService.asyncFindAll();
  },
  computed: {
  },
  methods: {
    async addTeam() {
      if(this.editingTeam){
        this.team = {
          id: parseInt(this.$route.params.id),
          name: this.teamName,
          warehouseId: this.selectedWarehouse,
        }
      }else {
          this.team = {
            id: Math.floor(Math.random() * 100000),
            name: this.teamName,
            warehouseId: this.selectedWarehouse,
          };
      }
      await this.teamsService.asyncSave(this.team);
      this.resetForm();
    },
    async editTeam(team) {
      this.editingTeam = team;
      this.teamName = team.name;
      this.selectedWarehouse = await this.warehousesService.asyncFindById(team.warehouseId);
      // this.$router.push("/teams/" + team.id)
      this.editingTeam = team;
      this.showModal = true;
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