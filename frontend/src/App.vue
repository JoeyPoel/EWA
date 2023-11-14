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
      productsService: new ProductAdaptor(CONFIG.BACKEND_URL + "/products/"),
      usersService: new UserAdaptor(CONFIG.BACKEND_URL + "/user"),
    }
  }
  }
</script>