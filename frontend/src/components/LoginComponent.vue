<template>
    <form @submit.prevent="login" class="row position-absolute top-25 end-0 start-0">
      <div class="col-md-3 col-lg-3 col-12 mx-auto card p-5">
        <h3 class="mb-3 text-center">Login</h3>
        <p v-if="!formIsValid" class="error text-danger mt-2">Email and/or password are wrong</p>

        <div class="form-group mb-2">
          <label class="me-3">Email</label>
          <input class="form-control" type="email" v-model.trim="email" />
        </div>
        <div class="form-group mb-2">
          <label class="me-3">Password</label>
          <input class="form-control" type="password" v-model.trim="password" />
        </div>

        <div class="text-center">
          <a class="link">Forgot password?</a>
          <br />
          <button type="submit" class="btn btn-primary px-4 mt-4">Log in</button>
        </div>

      </div>
    </form>
</template>

<script>

import {User} from "@/models/User";

export default {
  name: "LoginComponent",
  inject: ["usersService"],
  data() {
    return {
      email: "admin@admin.com",
      password: "admin",
      user: null,
      formIsValid: true
    }
  },
  methods: {
    async login() {
      //Check if email and password are not empty
      if (!this.email || !this.password) {
        this.formIsValid = false;
        return;
      }

      let givenUser = new User(this.email, this.password);
      this.user = await this.usersService.asyncFindUser(givenUser);

      if (this.user.email) {
        sessionStorage.setItem("email", this.user.email);
        this.$router.push("/dashboard");
      } else {
        this.formIsValid = false;
      }
    },
  }
}
</script>

<style scoped>
  .top-25 {
    top: 25%;
  }
</style>