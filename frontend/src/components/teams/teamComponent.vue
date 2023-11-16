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
        :users="users"
        :editingTeam="editingTeam"
        @closeModal="closeModal"
        @save="addTeam"
        @update="editTeam"
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
      users: [],
      selectedWarehouse: null,
      teamName: '',
      searchTerm: "",
      selectedTeam: null,
      team: null,
      showModal: false, // Flag to control modal visibility
      editingTeam: false,
    };
  },
  components: {
    editTeamModal,
  },
  async created() {
    this.teams = await this.teamsService.asyncFindAll();
    this.warehouses = await this.warehousesService.asyncFindAll();
    this.users = await this.warehousesService.asyncFindAll()
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
    async addTeam(addedTeam) {
      console.log("Entered method AddTeam")
      console.log(addedTeam)
      await this.teamsService.asyncSave(addedTeam);
      this.teams.push(addedTeam); // Add the new team to the local list
    },
    async editTeam(editedTeam) {
      console.log("Entered method EditTeam")
      console.log(editedTeam)
      await this.teamsService.asyncUpdate(editedTeam);
      const index = this.teams.findIndex(team => team.id === editedTeam.id);
      if (index !== -1) {
        this.teams.splice(index, 1, editedTeam); // Remove 1 element at index and replace with editedTeam
      }
    },
    showEditModal(team) {
      this.selectedTeam = team;
      this.teamName = team.name;
      this.selectedWarehouse = this.warehouses.find(warehouse => warehouse.id === team.warehouseId);
      this.$router.push({name: 'EditTeamModal', params: {id: team.id}});
      this.editingTeam = true;
      this.showModal = true;
    },
    async deleteTeam(deletedTeam) {
      await this.teamsService.asyncDeleteById(deletedTeam.id)
      this.teams = this.teams.filter(item => item.id !== deletedTeam.id); // Remove the team from the local list
    },
    showAddModal() {
      this.$router.push({name: 'AddTeamModal'});
      this.editingTeam = false;
      this.showModal = true;
    },
    closeModal() {
      this.$router.push({name: 'Teams'});
      this.resetForm()
      this.showModal = false;
    },
    resetForm(){
      this.selectedTeam = null
      this.selectedWarehouse = null
      this.teamName = ''
    }
  },
};
</script>

<style scoped>
.table-wrapper {
  max-height: 640px;
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

