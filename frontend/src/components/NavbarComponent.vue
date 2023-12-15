<template>
  <v-navigation-drawer v-if="!$route.meta.hideNavbar" class="bg-white bg-custom"
                       :expand-on-hover="true" :rail="true" :permanent="true">
    <v-list>
      <router-link to="/dashboard">
        <v-list-item prepend-icon="">
          <v-img :src="logo" max-height="100" max-width="100" class="align-center justify-center"></v-img>
        </v-list-item>
      </router-link>
      <router-link v-for="(route, key) in filteredRoutes" :key="key" :to="route.to" class="nav-link text-black">
        <v-list-item :prepend-icon="route.icon" :title="route.title"></v-list-item>
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
import {jwtDecode} from "jwt-decode";

export default {
  name: "NavbarComponent",
  data() {
    return {
      logo: logo,
      session: true,
      routes: [
        { name: 'dashboard', icon: '$dashboard', title: 'Dashboard', to: '/dashboard',  roles: ['ADMIN', 'USER'] },
        { name: 'inventories', icon: '$inventory', title: 'Inventories', to: '/inventories', roles: ['ADMIN'] },
        { name: 'teamProjects', icon: '$project', title: 'Projects', to: '/team-projects', roles: ['USER'] },
        { name: 'projects', icon: '$project', title: 'Projects', to: '/projects', roles: ['ADMIN'] },
        { name: 'warehouses', icon: '$warehouse', title: 'Warehouses', to: '/warehouses', roles: ['ADMIN', 'USER'] },
        { name: 'products', icon: '$product', title: 'Products', to: '/products', roles: ['ADMIN'] },
        { name: 'team', icon: '$team', title: 'Team', to: '/teams', roles: ['ADMIN', 'USER'] },
        { name: 'users', icon: '$user', title: 'Users', to: '/users', roles: ['ADMIN'] },
      ],
    };
  },
  computed: {
    isAdmin() {
      const isAuthenticated = localStorage.getItem('token');
      if (isAuthenticated) {
        const decodedToken = jwtDecode(isAuthenticated);
        return decodedToken.role === 'ADMIN';
      }
      return false;
    },
    isUser() {
      const isAuthenticated = localStorage.getItem('token');
      if (isAuthenticated) {
        const decodedToken = jwtDecode(isAuthenticated);
        return decodedToken.role === 'USER';
      }
      return false;
    },
    filteredRoutes() {
      const userRole = this.isAdmin ? 'ADMIN' : (this.isUser ? 'USER' : null);
      if (!userRole) {
        return [];
      }
      return this.routes.filter(route => route.roles.includes(userRole));
    },
  },
  methods: {
    logout() {
      if (localStorage.getItem("email") != null) {
        localStorage.clear();
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
