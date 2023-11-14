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

      <li class="nav-item">
        <router-link class="nav-link text-black" to="/warehouse/overview">
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
          <li><router-link class="dropdown-item" :to="{ name: 'User' }">
            User page
          </router-link></li>
          <li><router-link class="dropdown-item" :to="{ name: 'AdminProducts' }">
            Products page
          </router-link></li>
          <li><a class="dropdown-item" href="#">
            Administration
          </a></li>
        </ul>
      </li>

      <li class="nav-item">
        <router-link class="nav-link text-black" to="/sign-in">
          Log in
        </router-link>
      </li>
    </ul>
  </div>

</template>



<style>
:root {
  --gradient-color-start: rgba(232, 241, 82, 0.36);
  --gradient-color-middle: rgba(91, 46, 24, 0.13);
  --gradient-color-almost-end: rgba(223, 226, 177, 0.26);
  --gradient-color-end: rgba(255, 255, 255, 1);

  --button-color: rgba(232, 241, 82, .3);
}

.nav-link:hover {
  background-color: var(--button-color);
}

.nav-link.router-link-exact-active {
  background-color: var(--button-color); /* Adjust to use gradient start color */
  color: #fff;
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
