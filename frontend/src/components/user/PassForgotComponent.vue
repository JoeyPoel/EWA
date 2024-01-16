<template>
  <main class="d-flex align-items-center min-vh-100 py-3 py-md-0 bg-custom w-100">
    <div class="container p-5 justify-content-center">
      <div class="card login-card col-5 mx-auto">
        <div class="card-body">
          <div class="brand-wrapper">
            <img src="../../assets/logo.png" alt="logo" class="logo">
          </div>
          <p class="login-card-description">Reset password</p>
          <p v-if="emailSent" class="succes text-success mt-2">Email has been sent</p>
          <p v-if="wrongEmail" class="error text-error mt-2">Account with this email does not exist</p>
          <form @submit.prevent="forgotPassword">
            <div class="form-group">
              <input type="email" name="email" placeholder="example@email.com" id="email" class="form-control"
                     v-model.trim="email">
            </div>
            <input name="login" id="login" class="btn btn-block login-btn mb-4 w-100" type="submit"
                   value="Send Email">
          </form>
          <router-link to="/log-in" class="forgot-password-link">Back to login</router-link>
        </div>
      </div>
    </div>
  </main>
</template>

<script>
export default {
  name: "PassForgotComponent",
  inject: ["usersService", "emailService"],
  data() {
    return {
      email: "",
      user: null,
      emailSent: false,
      wrongEmail: false
    }
  },
  methods: {
    async forgotPassword() {
      try {
        this.user = await this.emailService.sendPassResetEmail(this.email);
        this.emailSent = true;
      } catch(e) {
        //TODO change this error
      }

    }
  },
}
</script>

<style scoped>
@import "@/assets/styles/userFormStyles.css";
</style>
