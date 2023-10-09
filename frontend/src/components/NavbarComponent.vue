<template>
  <nav class="navbar navbar-expand-md navbar-light bg-body py-3">
    <div class="container bg-light">
      <router-link class="navbar-brand" to="/">
        <h2>Home</h2>
      </router-link>

      <button
          class="navbar-toggler"
          type="button"
          data-bs-toggle="collapse"
          data-bs-target="#navbarNav"
          aria-controls="navbarNav"
          aria-expanded="false"
          aria-label="Toggle navigation"
      >
        <span class="navbar-toggler-icon"></span>
      </button>

      <div class="collapse navbar-collapse" id="navbarNav">
        <ul class="navbar-nav ms-auto">
          <li class="nav-item">
            <router-link class="nav-link" to="/warehouse">
              <h2>Inventory</h2>
            </router-link>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="#">
              <h2>Projects</h2>
            </a>
          </li>
          <li class="nav-item dropdown">
            <a
                class="nav-link dropdown-toggle"
                href="#"
                id="adminDropdown"
                role="button"
                data-bs-toggle="dropdown"
                aria-expanded="false"
            >
      <ul class="navbar-nav me-auto">
        <li class="nav-item ">
          <router-link class="nav-link m-1 item" to="/"><h2>Home</h2></router-link>
        </li>
        <li class="nav-item ">
          <router-link class="nav-link m-1 item" to="/warehouse/inventory"><h2>Inventory</h2></router-link>
        </li>
        <li class="nav-item ">
          <a class="nav-link m-1 item" href="#">
            <h2>Projects</h2>
          </a>
        </li>
        <li class="nav-item ">
            <router-link class="nav-link m-1 item" to="/warehouse/overview"><h2>Warehouses</h2></router-link>
        </li>
        <li class="nav-item">
          <div class="nav-item dropdown m-1">
            <a class="nav-link item" aria-expanded="false" data-bs-toggle="dropdown" href="#">
              <h2>Admin</h2>
            </a>
            <div class="dropdown-menu" aria-labelledby="adminDropdown">
              <router-link class="dropdown-item" :to="{ name: 'User' }">
                User page
              </router-link>
              <a class="dropdown-item" href="#">Admin Page 2</a>
            </div>
          </li>
          <li class="nav-item ">
            <router-link class="nav-link m-1 item" to="/dashboard"><h2>Dashboard</h2></router-link>
          </li>
          <li class="nav-item">
            <router-link class="nav-link btn btn-success" to="/sign-in">
              Log in
            </router-link>
          </li>
        </ul>
      </div>
    </div>
  </nav>
</template>

<script>
export default {
  name: "NavbarComponent",
};
</script>

<style scoped>
.item:hover, .dropdown-item:hover, .item:focus, .nav-link:hover{
  background-color: lightgreen;
}

.nav-link.router-link-exact-active {
  background-color: #007bff;
  color: #fff;
}
</style>