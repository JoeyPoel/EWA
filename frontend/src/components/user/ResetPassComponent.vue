<template>
  <main class="d-flex align-items-center min-vh-100 py-3 py-md-0 bg-custom w-100">
    <div class="container p-5 justify-content-center">
      <div class="card login-card col-5 mx-auto">
        <div class="card-body">
          <div class="brand-wrapper">
            <img src="../../assets/logo.png" alt="logo" class="logo">
          </div>
          <p class="login-card-description">Reset password</p>
          <p v-if="notEqual" class="error text-error mt-2">Passwords do not match</p>
          <p v-if="succes" class="success text-success mt-2">Succesfully changed password</p>

          <form @submit.prevent="resetPassword">
            <div class="form-group">
              <input type="password" name="password" placeholder="New password" id="password" class="form-control"
                     v-model.trim="password">
            </div>
            <div class="form-group">
              <input type="password" name="repeatPassword" placeholder="Repeat password" id="repeatPassword" class="form-control"
                     v-model.trim="repeatPassword">
            </div>
            <input name="login" id="login" class="btn btn-block login-btn mb-4 w-100" type="submit"
                   value="Reset Password">
          </form>
        </div>
      </div>
    </div>
  </main>
</template>

<script>
import {jwtDecode} from "jwt-decode";

export default {
  name: "ResetPasswordComponent",
  inject: ["usersService", "emailService"],
  data() {
    return {
      password: "",
      repeatPassword: "",
      token: null,
      notEqual: false,
      succes: false
    }
  },
  methods: {
    resetPassword() {
      this.token = this.$route.params.token.substring(1);
      const decodedToken = jwtDecode(this.token);
      console.log(decodedToken);

      if (this.password === this.repeatPassword && this.password !== "" && this.repeatPassword !== "" ) {

        this.usersService.asyncResetPassword(this.password, decodedToken.id);
        this.succes = true;

        this.$router.push("/log-in");

      } else {
        this.notEqual = true;
      }
    }
  }
}
</script>

<style scoped>
@import "@/assets/styles/userFormStyles.css";
</style>
