<script>

import logo from "@/assets/logo.png";

export default {
  name: "NavbarComponent",
  data() {
    return {
      logo: logo
    };
  }
};
</script>

<template>
  <div class="d-flex flex-column flex-shrink-0 p-3 text-white bg-dark vh-100 w-auto sticky-top">
    <!-- Home Link with Icon -->
    <router-link class="d-flex align-items-center mb-3 mb-md-0 me-md-auto text-white text-decoration-none" to="/dashboard">
      <img :src="logo" alt="Your Company Logo" class="me-2 nav-height">
    </router-link>

    <hr>

    <ul class="nav nav-pills flex-column mb-auto">

      <li class="nav-item">
        <router-link class="nav-link text-white" to="/dashboard">
          Dashboard
        </router-link>
      </li>

      <li class="nav-item">
        <router-link class="nav-link text-white" to="/warehouse/inventory">
          Inventory
        </router-link>
      </li>

      <li class="nav-item">
        <router-link class="nav-link text-white" to="/warehouse/overview">
          Warehouses
        </router-link>
      </li>

      <li class="nav-item">
        <router-link class="nav-link text-white" to="/product">
          Products
        </router-link>
      </li>

      <li class="nav-item">
        <a class="nav-link text-white" href="#">
          Projects
        </a>
      </li>

      <li class="nav-item dropdown">
        <a class="nav-link text-white dropdown-toggle" href="#" id="adminDropdown" data-bs-toggle="dropdown" aria-expanded="false">
          Admin
        </a>
        <ul class="dropdown-menu dropdown-menu-dark text-small shadow" aria-labelledby="adminDropdown">
          <li><router-link class="dropdown-item" :to="{ name: 'User' }">
            User page
          </router-link></li>
          <li><a class="dropdown-item" href="#">
            Administration
          </a></li>
        </ul>
      </li>

      <li class="nav-item">
        <router-link class="nav-link text-white" to="/sign-in">
          Log in
        </router-link>
      </li>
    </ul>

    <hr>

    <div class="dropdown">
      <a href="#" class="d-flex align-items-center text-white text-decoration-none dropdown-toggle" id="dropdownUser1" data-bs-toggle="dropdown" aria-expanded="false">
        <img src="https://github.com/mdo.png" alt="mdo" width="32" height="32" class="rounded-circle me-2">
        <strong>mdo</strong>
      </a>
      <ul class="dropdown-menu dropdown-menu-dark text-small shadow" aria-labelledby="dropdownUser1">
        <li><a class="dropdown-item" href="#">New project...</a></li>
        <li><a class="dropdown-item" href="#">Settings</a></li>
        <li><a class="dropdown-item" href="#">Account</a></li>
        <li><hr class="dropdown-divider"></li>
        <li><a class="dropdown-item" href="#">Sign out</a></li>
      </ul>
    </div>
  </div>

</template>



<style scoped>
.nav-link:hover {
  background-color: lightgreen;
}

.nav-link.router-link-exact-active {
  background-color: #007bff;
  color: #fff;
}

.nav-height {
  height: 4em;
}
</style>
