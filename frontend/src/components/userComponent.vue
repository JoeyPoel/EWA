<template>
  <div class="container mt-5">
    <h1 class="text-center card text-light bg-dark mb-2 p-3">User List</h1>

    <!-- "Create User" button -->
    <button class="btn btn-dark mb-2" data-bs-toggle="modal"
            data-bs-target="#createUserModal" @click="openCreateUserModal">Create User</button>

    <div class="row">
      <div class="col-md-6" v-for="(user, index) in users" :key="index">
        <div class="card mb-4">
          <!-- Your card component -->
          <div class="card-header d-flex justify-content-between align-items-center">
            <h5 class="card-title">{{ user.name }}</h5>
            <button class="btn btn-dark"  @click="openUpdateUserModal(user)">Update</button>
          </div>
          <div class="card-body">
            <p class="card-text"><strong>Email:</strong> {{ user.email }}</p>
            <p class="card-text"><strong>Team:</strong> {{ user.team }}</p>
            <p class="card-text"><strong>Role:</strong> {{ user.role }}</p>
          </div>
        </div>
      </div>
    </div>

    <!-- Modal for creating a new user -->
    <div class="modal fade" id="createUserModal" ref="createUserModal" tabindex="-1" aria-labelledby="projectModalLabel" aria-hidden="true">
      <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">

        <div class="modal-content">

          <div class="modal-header bg-dark text-white">

            <h5 class="modal-title">Create User</h5>

            <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal" aria-label="Close" @click="closeCreateUserModal"></button>

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
                <input type="text" class="form-control" id="newUserRole" v-model="newUser.role" required>
              </div>

              <div class="mb-3">
                <label for="newUserTeam" class="form-label">Team</label>
                <input type="text" class="form-control" id="newUserTeam" v-model="newUser.team" required>
              </div>

              <button type="submit" class="btn btn-dark" data-bs-toggle="modal"
                      data-bs-target="#createUserModal">Create</button>

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
  inject: ['userDetailService'],
  data() {
    return {
      users: [], // Initialize an empty array to store user data
      newUser: {
        name: "",
        email: "",
        team: "",
        role: "",
      },
    };
  },
  methods: {
    openCreateUserModal() {
      // Clear the form when opening the modal
      this.newUser = {
        name: "",
        email: "",
        team: "",
        role: "",
      };

      // Show the modal using the $refs object
      this.$refs.createUserModal.classList.add("show");
      this.$refs.createUserModal.style.display = "block";
      document.body.classList.add("modal-open");
    },
    closeCreateUserModal() {
      // Hide the modal using the $refs object
      this.$refs.createUserModal.classList.remove("show");
      this.$refs.createUserModal.style.display = "none";
      document.body.classList.remove("modal-open")
    },
    createUser() {
      // Validate the form fields (add additional validation as needed)
      if (!this.newUser.name || !this.newUser.email || !this.newUser.role) {
        alert("Please fill in all fields.");
        return;
      }

      // Add the new user to the users array
      this.users.push({ ...this.newUser });
      this.$emit('add-User', {...this.newUser});

      // Close the modal
      this.closeCreateUserModal();
    },
    openUpdateUserModal(user) {
      // Logic for updating a user (you can implement this based on your requirements)
      console.log("Update user:", user);
    },
    saveProduct() {
      this.$emit('add-User', {...this.newUser});
      this.closeCreateUserModal();
    }
  },
  async created() {

    this.users = await this.userDetailService.asyncFindAll();
  },
};
</script>

<style scoped>
.modal {
  pointer-events: none; /* Prevent interaction with hidden elements */
}
</style>