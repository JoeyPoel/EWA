<template>
  <div class="container mt-3">
    <div class="d-flex justify-content-between mb-3">
      <input v-model="searchTerm" type="text" class="form-control w-25" placeholder="Search for name...">
      <button @click="showAddModal" class="btn btn-success">Add Team</button>
    </div>
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
        <tr v-for="team in filteredTeams" :key="team.id" class="border-bottom">
          <td>{{ team.name }}</td>
          <td>{{ team.warehouseId }}</td>
          <td>
            <button type="button" class="btn btn-dark mx-1" @click="showEditModal(team)">
              Edit
            </button>
          </td>
        </tr>
        </tbody>
      </table>
    </div>

    <editTeamModal
        v-if="shouldShowModal"
        :team="selectedTeam"
        :warehouses="warehouses"
        @closeModal="closeModal"
        @saveChanges="addTeam"
        @delete="deleteTeam"
    ></editTeamModal>
  </div>
</template>

<script>
import editTeamModal from "@/components/teams/EditTeamModal";
export default {
  inject: ['teamsService', 'warehousesService'],
  name: 'teamComponent',
  data() {
    return {
      teams: [],
      warehouses: [],
      selectedWarehouse: null,
      teamName: '',
      searchTerm: "",
      selectedTeam: null,
      team: null,
      showModal: false, // Flag to control modal visibility
    };
  },
  components: {
    editTeamModal,
  },
  async created() {
    this.teams = await this.teamsService.asyncFindAll();
    this.warehouses = await this.warehousesService.asyncFindAll();
  },
  computed: {
    filteredTeams() {
      if (!this.searchTerm) return this.teams;

      return this.teams.filter(team => {
        return team.name.toLowerCase().includes(this.searchTerm.toLowerCase());
      });
    },
    shouldShowModal() {
      return this.$route.name === 'AddTeamModal' || this.showModal === true;
    }
  },
  methods: {
    async addTeam() {
      console.log("Entered method AddTeam")
      await this.teamsService.asyncSave(this.team);
    },
    showEditModal(team) {
      this.selectedTeam = team;
      this.teamName = team.name;
      this.selectedWarehouse = this.warehouses.find(warehouse => warehouse.id === team.warehouseId);
      this.$router.push({name: 'EditTeamModal', params: {id: team.id}});
      this.showModal = true;
    },
    async deleteTeam(team) {
      await this.teamsService.asyncDeleteById(team.id)
    },
    showAddModal() {
      this.$router.push({name: 'AddTeamModal'});
      this.showModal = true;
    },
    closeModal() {
      this.$router.push({name: 'Teams'});
      this.showModal = false;
    },
  },
};
</script>

<style scoped>
.table-wrapper {
  max-height: 450px;
  overflow-y: auto;
}
.modal-mask {
  position: fixed;
  z-index: 9998;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: table;
  transition: opacity 0.3s ease;
}
</style>

