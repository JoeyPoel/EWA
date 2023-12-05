<template>
  <main class="d-flex align-items-center min-vh-100 py-3 py-md-0 bg-custom w-100">
    <div class="container p-5">
      <div class="card login-card">
        <div class="row no-gutters">
          <div class="col-md-5 position-relative">
            <img src="../../assets/Solar-panel-login.jpeg" alt="login" class="login-card-img">
          </div>
          <div class="col-md-7">
            <div class="card-body">
              <div class="brand-wrapper">
                <img src="../../assets/logo.png" alt="logo" class="logo">
              </div>
              <p class="login-card-description">Log into your account</p>
              <p v-if="!formIsValid" class="error text-danger mt-2">Email and/or password are wrong</p>
              <form @submit.prevent="login">
                <div class="form-group">
                  <input type="email" name="email" id="email" class="form-control" v-model.trim="email" placeholder="Email">
                </div>
                <div class="form-group mb-4">
                  <input type="password" name="password" id="password" class="form-control" v-model.trim="password" placeholder="Password">
                </div>
                <input name="login" id="login" class="btn btn-block login-btn mb-4 w-100" type="submit" value="Login">
              </form>
              <router-link to="/pass-forgot" class="forgot-password-link">Forgot password?</router-link>
            </div>
          </div>
        </div>
      </div>
    </div>
  </main>

</template>

<script>
// import User from "@/models/User";

export default {
  name: "LoginComponent",
  inject: ["usersService", "authenthicationService"],
  data() {
    return {
      email: null,
      password: null,
      user: null,
      formIsValid: true
    }
  },
  methods: {
    async login() {
      if (!this.email || !this.password) {
        this.formIsValid = false;
        return;
      }

      try {
        const response = await this.authenthicationService.asyncLogin({
          email: this.email,
          password: this.password
        });

        if (response) {
          localStorage.setItem("email", response.email);
          console.log('Token from server:', response.jwtToken);
          localStorage.setItem("token", response.jwtToken);
          console.log(response)
          this.$router.push("/dashboard");
          //TODO After login navbar isnt loaded in properly firsly, after refresh it is
          window.location.reload()
        } else {
          this.formIsValid = false;
        }
      } catch (error) {
        console.error("Login failed:", error);
        this.formIsValid = false;
      }

    }
  },
}
</script>

<style scoped>
  .bg-custom {
    background: linear-gradient(
        25deg,
        var(--gradient-color-start) 0%,
        var(--gradient-color-middle) 30%,
        var(--gradient-color-almost-end) 75%,
        var(--gradient-color-end) 100%
    );
  }

  body {
    font-family: "Karla", sans-serif;
    min-height: 100vh;
  }

  .brand-wrapper {
    margin-bottom: 19px;
  }

  .logo {
    height: 80px;
  }

  .login-card {
    border: 0;
    border-radius: 27px;
    box-shadow: 0 10px 30px 0 rgba(172, 168, 168, 0.43);
    overflow: hidden;
  }

  .login-card-img {
    border-radius: 0;
    position: absolute;
    width: 100%;
    height: 100%;
    object-fit: cover;
  }

  .card-body {
    padding: 60px 60px 60px;
    min-height: 580px;
  }

  .login-card-description {
    font-size: 25px;
    color: #000;
    font-weight: normal;
    margin-bottom: 23px;
  }

  form {
    max-width: 326px;
  }

  .form-control {
    border: 1px solid #d5dae2;
    padding: 15px 25px;
    margin-bottom: 20px;
    min-height: 45px;
    font-size: 13px;
    line-height: 15;
    font-weight: normal;
  }

  .form-control::placeholder {
    color: #919aa3;
  }

  .login-btn {
    padding: 13px 20px 12px;
    background-color: #000;
    border-radius: 4px;
    font-size: 17px;
    font-weight: bold;
    line-height: 20px;
    color: #fff;
    margin-bottom: 24px;
  }

  .login-btn:hover {
    border: 1px solid #000;
    background-color: transparent;
    color: #000;
  }

  .forgot-password-link {
    font-size: 14px;
    color: #919aa3;
    margin-bottom: 12px;
  }

  .login-card-footer-text {
    font-size: 16px;
    color: #0d2366;
    margin-bottom: 60px;
  }

  .login-card-footer-nav a {
    font-size: 14px;
    color: #919aa3;
  }

  .login-card-footer-link {
    position: absolute;
    bottom: 1rem;
    text-align: center;
    width: 100%;
  }

  .form-control {
    line-height: 1.5!important;
  }

</style>