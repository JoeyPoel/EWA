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

export default {
  name: 'App',
  components: {
    NavbarComponent
  },
  provide() {
    return {
      // stateless data services adaptor singletons
      warehousesService: new WarehousesAdaptor(CONFIG.BACKEND_URL + "/warehouses/"),
      teamsService: new TeamsAdaptor(CONFIG.BACKEND_URL + "/teams/"),

    }
  }
  }
</script>