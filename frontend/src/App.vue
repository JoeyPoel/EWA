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
import UserDetailAdaptor from "@/services/UserDetailAdaptor";

export default {
  name: 'App',
  components: {
    NavbarComponent
  },
  provide() {
    return {
      // stateless data services adaptor singletons
      warehousesService: new WarehousesAdaptor(CONFIG.BACKEND_URL + "/warehouses/"),
      userDetailService: new UserDetailAdaptor(CONFIG.BACKEND_URL + "/users/")
    }
  }
  }
</script>