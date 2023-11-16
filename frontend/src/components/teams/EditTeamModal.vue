
<template>
  <div class="modal fade" ref="addModalRef" tabindex="-1" aria-labelledby="ModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
      <div class="modal-content">
        <div class="modal-header">
          <h1 class="modal-title fs-5 text-center" id="ModalLabel">New Team</h1>
          <button @click="closeModal" type="button" class="btn-close" data-bs-dismiss="modal"
                  aria-label="Close"></button>
        </div>
        <div class="modal-body">
          <label for="name">Name</label>
          <input type="text" v-model="teamName" class="form-control">

          <label for="chooseWarehouse">Warehouse</label>
          <select v-model="selectedWarehouse" class="form-control">
            <option v-for="warehouse in warehouses" :key="warehouse.id" :value="warehouse.id">
              {{ warehouse.name }}
            </option>
          </select>
          <label>Search users</label>
          <input
              type="text"
              v-model="searchQuery"
              placeholder="Search users..."
              class="form-control mb-3"
          />
          <label for="chooseUsers">Choose Users</label>
          <div class="chooseUsers">
            <div v-for="user in filteredUsers" :key="user.id">
              <input
                  type="checkbox"
                  :id="'user_' + user.id"
                  :value="user"
                  v-model="selectedUsers"
              />
              <label :for="'user_' + user.id">{{ user.name }}</label>
            </div>
          </div>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-secondary" data-bs-dismiss="modal" @click="closeModal">Close</button>
          <button type="button" class="btn btn-primary" @click="saveChanges">Save changes</button>
          <button type="button" class="btn btn-danger" @click="deleteTeam">Delete</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>

import {Modal} from "bootstrap";

export default {
  props: {
    team: Object, // Team object to edit
    warehouses: Array, // Array of warehouses
    users: Array, // Array of users
  },
  data() {
    return {
      teamName: '',
      selectedWarehouse: null,
      selectedUsers: [],
      searchQuery: "",
    };
  },
  mounted() {
    // Populate modal fields when component is mounted
    this.modal = new Modal(this.$refs.addModalRef);
    this.$refs.addModalRef.addEventListener('hidden.bs.modal', this.closeModal);
    if(this.team != null){
      this.teamName = this.team.name;
      this.selectedWarehouse = this.team.warehouseId;
    }
    this.modal.show();
  },
  computed: {
    filteredUsers() {
      // Filter users based on search text
      if (!this.searchQuery) {
        return this.users;
      }
      const searchTerm = this.searchQuery .toLowerCase();
      return this.users.filter(user =>
          user.name.toLowerCase().includes(searchTerm)
      );
    },
  },
  methods: {
    saveChanges() {
      // Create a team object
      if(this.team){
        const team = {
          id: parseInt(this.$route.params.id),
          name: this.teamName,
          warehouseId: this.selectedWarehouse,
        }
        // Emit the team object
        this.$emit('update', team);
      } else{
        const team = {
          id: Math.floor(Math.random() * 100000),
          name: this.teamName,
          warehouseId: this.selectedWarehouse,
        };
        // Emit the team object
        this.$emit('save', team);
      }




      // Close the modal
      this.closeModal();
    },
    closeModal() {
      this.$emit('closeModal');
    },
    deleteTeam(){
      const id = {
        id: parseInt(this.$route.params.id),
      };
      this.$emit('delete', id);

      this.closeModal();
    }
  },
};
</script>

<style scoped>
.chooseUsers{
  max-height: 100px;
  min-height: 100px;
  overflow-y: auto;
}
</style>
