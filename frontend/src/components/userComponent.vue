<template>
  <div class="container mt-5">
    <h1 class="text-center card text-light bg-dark mb-2 p-3">User List</h1>

    <!-- "Create User" button -->
    <button class="btn btn-dark mb-2" data-bs-toggle="modal"
            data-bs-target="#createUserModal" @click="openCreateUserModal">Create User
    </button>

    <div class="row">
      <div class="col-md-6" v-for="(user, index) in users" :key="index">
        <div class="card mb-4">
          <!-- Your card component -->
          <div class="card-header d-flex justify-content-between align-items-center">
            <h5 class="card-title">{{ user.name }}</h5>
            <button class="btn btn-dark" @click="openCreateUserModal(user)">Update</button>
          </div>
          <div class="card-body">
            <p class="card-text"><strong>Email:</strong> {{ user.email }}</p>
            <p class="card-text"><strong>Team:</strong> {{ user.team.name }}</p>
            <p class="card-text"><strong>Role:</strong> {{ user.role }}</p>
          </div>
        </div>
      </div>
    </div>

    <!-- Modal for creating a new user -->
    <div class="modal fade" id="createUserModal" ref="createUserModal" tabindex="-1" aria-labelledby="projectModalLabel"
         aria-hidden="true">
      <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">

        <div class="modal-content">

          <div class="modal-header bg-dark text-white">

            <h5 class="modal-title">Create User</h5>

            <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal" aria-label="Close"
                    @click="closeCreateUserModal"></button>

          </div>

          <div class="modal-body">
            <!-- Form to fill in name, email, role, and team for creating a new user -->

            <form @submit.prevent="createUser">

              <div class="mb-3">
                <label for="newUserName" class="form-label">Name</label>
                <input type="text" class="form-control" id="newUserName" v-model="newUser.name" required>
              </div>

              <div class="mb-3">
                <label for="newUserEmail" class="form-label">Email</label>
                <input type="email" class="form-control" id="newUserEmail" v-model="newUser.email" required>
              </div>

              <div class="mb-3">
                <label for="newUserRole" class="form-label">Role</label>
                <select class="form-control" id="newUserRole" v-model="newUser.role" required>
                  <option>USER</option>
                  <option>ADMIN</option>
                </select>
              </div>

              <div class="mb-3">
                <label for="newUserTeam" class="form-label">Team</label>
                <input type="text" class="form-control" id="newUserTeam" v-model="newUser.team" required>
              </div>

              <button type="submit" class="btn btn-dark" data-bs-toggle="modal"
                      data-bs-target="#createUserModal">Create
              </button>

            </form>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>

export default {
  name: "UserComponent",
  inject: ['usersService'],
  data() {
    return {
      users: [],
      newUser: {
        email: "",
        name: "",
        password: "a",
        role: "",
        team: 0,
      },
      isCreateUserModalOpen: false,
    };
  },
  async created() {
    console.log("test")
    this.users = await this.usersService.asyncFindAll();
    console.log(this.users)


  },
  methods: {
    async openCreateUserModal() {
      this.newUser = {
        email: "",
        name: "",
        password: "",
        role: "",
        team: 0,
      };
      this.isCreateUserModalOpen = true;
    },
    closeCreateUserModal() {
      this.isCreateUserModalOpen = false;
    },
    async createUser() {
      // Validate the form fields (add additional validation as needed)
      if (!this.newUser.name || !this.newUser.email || !this.newUser.role) {
        alert("Please fill in all fields.");
        return;
      }

      // Create a new object with all properties of newUser except team
      const userToSave = { ...this.newUser };
      userToSave.password = "a";

      console.log(userToSave)
      const savedUser = await this.usersService.asyncSave(userToSave);
      if (savedUser) {
        this.users.push(savedUser);
        this.closeCreateUserModal();
      } else {
        alert("Failed to create user. Please try again.");
      }
    },
  }
};
</script>

<style scoped>
.modal {
  pointer-events: none; /* Prevent interaction with hidden elements */
}
</style>