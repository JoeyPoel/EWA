<script>
export default {
  name: "NavbarComponent"
}
</script>

<template>
  <nav class="navbar navbar-expand-md bg-body py-3">
    <div class="container bg-light">
      <ul class="navbar-nav me-auto">
        <li class="nav-item ">
          <router-link class="nav-link m-1 item" to="/"><h2>Home</h2></router-link>
        </li>
        <li class="nav-item ">
          <router-link class="nav-link m-1 item" to="/warehouse"><h2>Inventory</h2></router-link>

        </li>
        <li class="nav-item">
          <a class="nav-link m-1 item" href="#">
            <h2>Projects</h2>
          </a>
        </li>
        <li class="nav-item ">
          <router-link class="nav-link m-1 item" to="/dashboard"><h2>Dashboard</h2></router-link>

        </li>
      </ul>
      <router-link class="nav-link btn btn-lg btn-success m-1" to="/sign-in">Log in</router-link>
    </div>
  </nav>
</template>

<style scoped>
.item:hover, .dropdown-item:hover, .item:focus{
  background-color: lightgreen;
}

.nav-link.router-link-active {
  background-color: #007bff;
  color: #fff;
}
</style>