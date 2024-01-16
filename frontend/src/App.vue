<template>
  <FormDialog title="Error" :open="error.isOpen" :item="error" @close="error.isOpen = false" max-width="450px" />
  <v-layout class="rounded rounded-md">
    <NavbarComponent/>
    <v-main v-bind:class="getClass()">
      <router-view/>
    </v-main>
  </v-layout>
</template>

<script>

import NavbarComponent from "@/components/NavbarComponent";
import WarehousesAdaptor from "@/services/WarehousesAdaptor";
import {CONFIG} from "../app-config";
import TeamsAdaptor from "@/services/TeamsAdaptor";
import ProductAdaptor from "@/services/ProductAdaptor";
import UserAdaptor from "@/services/UserAdaptor";
import ProjectAdaptor from "@/services/ProjectAdaptor";
import {InventoryAdaptor} from "@/services/InventoryAdaptor";
import TransactionsAdaptor from "@/services/TransactionsAdaptor";
import AuthenticationAdaptor from "@/services/AuthenticationAdaptor";
import {ChartsAdaptor} from "@/services/ChartsAdaptor";
import EmailAdaptor from "@/services/EmailAdaptor";
import {FetchInterceptor} from "./services/FetchInterceptor";

import logo from "@/assets/console.png";
import FormDialog from "@/components/base/FormDialog.vue";
import {OrderAdaptor} from "@/services/OrderAdaptor";

export default {
  name: 'App',
  components: {
    FormDialog,
    NavbarComponent
  },
  created() {
    this.$router.app = this; // Add this line
  },
  data() {
    return {
      theFetchInterceptor: null,
      error: {
        title: "Something went wrong!",
        message: "",
        isOpen: false
      }
    };
  },
  mounted() {
    document.title = 'Solar Console';
    this.setFavicon(logo);
    this.theFetchInterceptor = new FetchInterceptor(this.$router);
  },
  beforeUnmount() {
    if (this.theFetchInterceptor) {
      this.theFetchInterceptor.unregister();
    }
  },
  methods: {
    setFavicon(href) {
      let link = document.querySelector("link[rel*='icon']") || document.createElement('link');
      link.type = 'image/x-icon';
      link.rel = 'shortcut icon';
      link.href = href;
      document.getElementsByTagName('head')[0].appendChild(link);
    },
    getClass() {
      if (!this.$route.meta.hideNavbar && !this.$route.meta.noPadding) {
        return {
          'px-16': 1
        }
      }
    }
  },
  provide() {
    return {
      // stateless data services adaptor singletons
      warehousesService: new WarehousesAdaptor(CONFIG.BACKEND_URL + "/warehouses"),
      teamsService: new TeamsAdaptor(CONFIG.BACKEND_URL + "/teams"),
      usersService: new UserAdaptor(CONFIG.BACKEND_URL + "/users"),
      productsService: new ProductAdaptor(CONFIG.BACKEND_URL + "/products"),
      projectsService: new ProjectAdaptor(CONFIG.BACKEND_URL + "/projects"),
      inventoryService: new InventoryAdaptor(CONFIG.BACKEND_URL + "/inventories"),
      transactionsService: new TransactionsAdaptor(CONFIG.BACKEND_URL + "/transactions"),
      authenthicationService: new AuthenticationAdaptor(CONFIG.BACKEND_URL + "/auth", this.$router),
      chartsService: new ChartsAdaptor(CONFIG.BACKEND_URL + "/charts"),
      emailService: new EmailAdaptor(CONFIG.BACKEND_URL + "/mail"),
      orderService: new OrderAdaptor(CONFIG.BACKEND_URL + "/orders"),
    }
  },
}
</script>

<style>
* {
  font-weight: 500;
}

:root {
  --gradient-color-start: rgba(232, 241, 82, 0.31);
  --gradient-color-middle: rgba(91, 46, 24, 0.07);
  --gradient-color-almost-end: rgba(223, 226, 177, 0.21);
  --gradient-color-end: rgba(255, 255, 255, 1);

  --button-color: rgba(232, 241, 82, .5);
}

body {
  background: linear-gradient(90deg,
  var(--gradient-color-start) 0%,
  var(--gradient-color-middle) 25%,
  var(--gradient-color-almost-end) 75%,
  var(--gradient-color-end) 100%);
}
</style>