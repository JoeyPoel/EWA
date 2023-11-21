<script>

import logo from "@/assets/logo.png";

export default {
  name: "NavbarComponent",
  data() {
    return {
      logo: logo,
      session: !!sessionStorage.getItem('email')
    }
  },
  methods: {
    logout() {
      sessionStorage.clear();
      this.$router.push("/log-in");
    },
  }
};
</script>

<template>
<!--<div v-if="session" class="d-flex flex-column flex-shrink-0 p-3 text-black bg-custom vh-100 w-auto sticky-top">-->
  <div class="d-flex flex-column flex-shrink-0 p-3 text-black bg-custom vh-100 w-auto sticky-top">
    <!-- Home Link with Icon -->
    <router-link class="d-flex align-items-center mb-3 mb-md-0 me-md-auto text-black text-decoration-none" to="/dashboard">
      <img :src="logo" alt="Your Company Logo" class="me-2 nav-height">
    </router-link>

    <hr>

    <ul class="nav nav-pills flex-column mb-auto">

      <li class="nav-item">
        <router-link class="nav-link text-black" to="/dashboard">
          Dashboard
        </router-link>
      </li>

      <li class="nav-item">
        <router-link class="nav-link text-black" to="/warehouse/inventory">
          Inventory
        </router-link>
      </li>

      <li class="nav-item ">
        <router-link class="nav-link text-black " to="/warehouse/overview">
          Warehouses
        </router-link>
      </li>

      <li class="nav-item">
        <router-link class="nav-link text-black" to="/product">
          Products
        </router-link>
      </li>

      <li class="nav-item">
        <router-link class="nav-link text-black" to="/project">
          Projects
        </router-link>
      </li>

      <li class="nav-item">
        <router-link class="nav-link text-black" to="/teams">
          Teams
        </router-link>
      </li>

      <li class="nav-item dropdown">
        <a class="nav-link text-black dropdown-toggle" href="#" id="adminDropdown" data-bs-toggle="dropdown" aria-expanded="false">
          Admin
        </a>
        <ul class="dropdown-menu dropdown-menu-dark text-small shadow" aria-labelledby="adminDropdown">
          <li><router-link class="dropdown-item" to="/user">
            User page
          </router-link></li>
          <li><router-link class="dropdown-item" to="/admin/products">
            Products page
          </router-link></li>
          <li><router-link class="dropdown-item" to="/admin/project">
            Projects
          </router-link></li>
          <li><a class="dropdown-item" href="#">
            Administration
          </a></li>
        </ul>
      </li>

      <li class="nav-item" @click="logout">
      <a class="nav-link text-black">
        Log out
      </a>
      </li>
    </ul>
  </div>

</template>

<style scoped>

.nav-link {
  margin: .2em;
}

.nav-link:hover {
  background-color: var(--button-color);
}

.nav-link.router-link-exact-active {
  position: relative;
  color: #000;
  background-color: #fff;
  border: 1px solid rgba(108, 117, 125, 0.45);
  border-radius: 0.2rem;
  box-shadow: 0 0 4px rgba(108, 117, 125, 0.35);
}

.nav-link.router-link-exact-active::after {
  background-color: var(--button-color);
  border: 1px solid #6c757d;
  border-radius: 0.2rem;
  box-shadow: 0 0 2px #6c757d;
  outline: black 1em;
}

.nav-height {
  height: 4em;
}

.bg-custom {
  background: linear-gradient(
      25deg,
      var(--gradient-color-start) 0%,
      var(--gradient-color-middle) 30%,
      var(--gradient-color-almost-end) 75%,
      var(--gradient-color-end) 100%
  );
}
</style>
