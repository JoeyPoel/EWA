<template>
  <v-navigation-drawer v-if="!$route.meta.hideNavbar" class="bg-white bg-custom"
                       expand-on-hover rail permanent>
    <v-list>
      <router-link to="/dashboard">
        <v-list-item prepend-icon="">
          <v-img :src="logo" max-height="100" max-width="100" class="align-center justify-center"></v-img>
        </v-list-item>
      </router-link>
      <router-link class="nav-link text-black" to="/dashboard">
        <v-list-item prepend-icon="$dashboard" title="Dashboard"></v-list-item>
      </router-link>
      <router-link class="nav-link text-black" to="/inventories">
        <v-list-item prepend-icon="$inventory" title="Inventories"></v-list-item>
      </router-link>
      <router-link class="nav-link text-black" to="/warehouses">
        <v-list-item prepend-icon="$warehouse" title="Warehouses"></v-list-item>
      </router-link>
      <router-link class="nav-link text-black" to="/products">
        <v-list-item prepend-icon="$product" title="Products"></v-list-item>
      </router-link>
      <router-link class="nav-link text-black" to="/projects">
        <v-list-item prepend-icon="$project" title="Projects"></v-list-item>
      </router-link>
      <router-link class="nav-link text-black" to="/teams">
        <v-list-item prepend-icon="$team" title="Teams"></v-list-item>
      </router-link>
      <router-link class="nav-link text-black" to="/users">
        <v-list-item prepend-icon="$user" title="Users"></v-list-item>
      </router-link>
    </v-list>
    <template v-slot:append>
      <v-divider></v-divider>
      <v-list>
        <v-list-item class="nav-link text-black" prepend-icon="$account" title="Account">
        </v-list-item>
        <v-list-item prepend-icon="$logout" title="Logout" @click="logout">
        </v-list-item>
      </v-list>
    </template>
  </v-navigation-drawer>
</template>

<script>
import logo from "@/assets/logo.png";

export default {
  name: "NavbarComponent",
  data() {
    return {
      logo: logo,
      session: true,
    }
  },
  methods: {
    logout() {
      if (sessionStorage.getItem("email") != null) {
        sessionStorage.clear();
        this.$router.push("/log-in");
      }
    }
  },
};
</script>

<style scoped>
.nav-link:hover {
  background-color: var(--button-color);
}

.nav-link.router-link-exact-active {
  color: #000;
  background-color: #fff;
  border: 1px solid rgba(108, 117, 125, 0.45);
  border-radius: 0.375rem;
  box-shadow: 0 0 4px rgba(108, 117, 125, 0.35);
}

.nav-link.router-link-exact-active::after {
  background-color: var(--button-color);
  border: 1px solid #6c757d;
  border-radius: 0.375rem;
  box-shadow: 0 0 2px #6c757d;
  outline: black 1em;
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
