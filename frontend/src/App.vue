<template>
  <div class="d-flex">
    <NavbarComponent/>
    <div class="flex-grow-1">
      <router-view/>
    </div>
  </div>
</template>

<script>

import NavbarComponent from "@/components/NavbarComponent";
import WarehousesAdaptor from "@/services/WarehousesAdaptor";
import {CONFIG} from "../app-config";
import TeamsAdaptor from "@/services/TeamsAdaptor";
import ProductAdaptor from "@/services/ProductAdaptor";
import UserAdaptor from "@/services/UserAdaptor";

export default {
  name: 'App',
  components: {
    NavbarComponent
  },
  provide() {
    return {
      // stateless data services adaptor singletons
      warehousesService: new WarehousesAdaptor(CONFIG.BACKEND_URL + "/warehouses/"),
      teamsService: new TeamsAdaptor(CONFIG.BACKEND_URL + "/teams"),
      usersService: new UserAdaptor(CONFIG.BACKEND_URL + "/user"),
      productsService: new ProductAdaptor(CONFIG.BACKEND_URL + "/products"),

    }
  }
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
</style>