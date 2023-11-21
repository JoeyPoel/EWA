<script>

export default {
  name: "UserComponent",
  inject: ["usersService"],
  data() {
    return {
      users: [],
    };
  },
  async created() {
    this.users = await this.usersService.asyncFindAll();
  },
};
</script>

<template>
  <div class="container mt-5">
    <h1 class="text-center text-bg-dark p-3">User List</h1>
    <div class="row">
      <div class="col-md-6" v-for="(user, index) in users" :key="index">
        <div class="card mb-4">
          <div class="card-header d-flex justify-content-between align-items-center">
            <h5 class="card-title">{{ user.name }}</h5>
            <button class="btn btn-dark">Update</button>
          </div>
          <div class="card-body">
            <p class="card-text"><strong>Email:</strong> {{ user.email }}</p>
            <p class="card-text"><strong>Team:</strong> {{ user.team.name }}</p>
            <p class="card-text"><strong>Role:</strong> {{ user.role }}</p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>


<style scoped>

</style>