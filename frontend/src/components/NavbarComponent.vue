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
.nav-link:hover {
  background-color: lightgreen;
}

.nav-link.router-link-exact-active {
  background-color: #007bff;
  color: #fff;
}
</style>
