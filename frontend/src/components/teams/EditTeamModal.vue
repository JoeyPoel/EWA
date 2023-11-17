
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
          <input type="text" v-model="teamName" class="form-control" :class="{ 'is-invalid': !isTeamNameValid }" id="teamName">
          <div v-if="!isTeamNameValid" class="invalid-feedback">
            Please enter a team name.
          </div>

          <label for="chooseWarehouse">Warehouse</label>
          <select v-model="selectedWarehouse" class="form-control" :class="{ 'is-invalid': !isSelectedWarehouseValid }">
            <option v-for="warehouse in warehouses" :key="warehouse.id" :value="warehouse">
              {{ warehouse.name }}
            </option>
          </select>
          <div v-if="!isSelectedWarehouseValid" class="invalid-feedback">
            Please select a warehouse.
          </div>
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
        <div>
          <div v-if="showConfirmation" class="confirmation-popper">
            <div class="popper-container">
              <div class="popper-header">
                <h3>Confirmation</h3>
                <button @click="cancelAction" class="close-btn">&times;</button>
              </div>
              <div class="popper-content">
                <p>Are you sure you want to delete this team?</p>
                <div class="button-group">
                  <button @click="confirmAction" class="btn btn-confirm">Yes</button>
                  <button @click="cancelAction" class="btn btn-cancel">No</button>
                </div>
              </div>
            </div>
          </div>
        </div>

        <div class="modal-footer">
          <button type="button" class="btn btn-secondary" data-bs-dismiss="modal" @click="closeModal">Close</button>
          <button v-if="!editingTeam" type="button" class="btn btn-primary" @click="saveChanges">Save</button>
          <button v-else type="button" class="btn btn-primary" @click="saveChanges">Save changes</button>
          <button v-if="editingTeam" type="button" class="btn btn-danger" @click="showConfirmation = true">Delete</button>
          <button v-else type="button" class="btn btn-danger" disabled>Delete</button>
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
    users: Array, // Array of all users
    teamMembers: Array, // Array of team members (selected users)
    editingTeam: Boolean // Indication on if team is being added or edited
  },
  data() {
    return {
      teamName: '',
      selectedWarehouse: null,
      selectedUsers: [],
      searchQuery: "",
      isTeamNameValid: true,
      isSelectedWarehouseValid: true,
      showConfirmation: false,
    };
  },
  mounted() {
    // Populate modal fields when component is mounted
    this.modal = new Modal(this.$refs.addModalRef);
    this.$refs.addModalRef.addEventListener('hidden.bs.modal', this.closeModal);
    if(this.team != null){
      this.teamName = this.team.name;
      this.selectedWarehouse = this.team.warehouse;
    }
    if (this.teamMembers) {
      // Pre-select team members when editing
      this.selectedUsers = this.teamMembers;
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
      if(this.validateForm()) {
        if (this.team) {
          const team = {
            id: parseInt(this.$route.params.id),
            name: this.teamName,
            warehouse: this.selectedWarehouse,
            users: this.selectedUsers
          }
          // Emit the team object
          this.$emit('update', team);
        } else {
          const team = {
            id: Math.floor(Math.random() * 100000),
            name: this.teamName,
            warehouse: this.selectedWarehouse,
            users: this.selectedUsers
          };
          // Emit the team object
          this.$emit('save', team);
        }
        // Close the modal
        this.closeModal();
      }
    },
    closeModal() {
      this.modal.hide(); // Close the Bootstrap modal
      this.$emit('closeModal');
    },
    deleteTeam() {
      const id = {
        id: parseInt(this.$route.params.id),
      };
      this.$emit('delete', id);

      this.closeModal();
    },
    validateForm() {
      // Check if the teamName and selectedWarehouse are not empty
      if (this.teamName.trim() !== '' && this.selectedWarehouse !== null) {
        // Reset validation flags when the fields are correct
        this.isTeamNameValid = true;
        this.isSelectedWarehouseValid = true;
        return true;
      } else {
        // Set validation flags and return false when fields are empty
        this.isTeamNameValid = this.teamName.trim() !== '';
        this.isSelectedWarehouseValid = this.selectedWarehouse !== null;
        return false;
      }
    },
    confirmAction() {
      this.deleteTeam()
      this.showConfirmation = false;
    },
    cancelAction() {
      this.showConfirmation = false;
    },
  },
};
</script>

<style scoped>
.chooseUsers{
  max-height: 100px;
  min-height: 100px;
  overflow-y: auto;
}
.is-invalid {
  border: 1px solid red;
}
.confirmation-popper {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  background-color: #ffffff;
  border: 1px solid #ccc;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  border-radius: 8px;
  padding: 20px;
  max-width: 300px;
}

.popper-container {
  text-align: center;
}

.popper-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 15px;
}

.popper-header h3 {
  margin: 0;
  font-size: 18px;
}

.close-btn {
  background: none;
  border: none;
  font-size: 20px;
  cursor: pointer;
  color: #999;
}

.close-btn:focus,
.close-btn:hover {
  color: #333;
}

.popper-content p {
  margin-bottom: 20px;
}

.button-group {
  display: flex;
  justify-content: center;
}

.btn {
  padding: 8px 20px;
  border-radius: 5px;
  cursor: pointer;
  font-size: 14px;
}

.btn-confirm {
  background-color: #28a745;
  color: #fff;
  border: none;
  margin-right: 10px;
}

.btn-cancel {
  background-color: #dc3545;
  color: #fff;
  border: none;
}

.btn-confirm:focus,
.btn-cancel:focus,
.btn-confirm:hover,
.btn-cancel:hover {
  opacity: 0.9;
}
</style>
