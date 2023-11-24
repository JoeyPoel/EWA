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
import ProjectAdaptor from "@/services/ProjectAdaptor";
import {InventoryAdaptor} from "@/services/InventoryAdaptor";

export default {
  name: 'App',
  components: {
    NavbarComponent
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

.btn-primary, .btn-success {
  background-color: #fff !important;
  color: #000 !important;
  border: 1px solid rgba(108, 117, 125, 0.45) !important;
  border-radius: 0.375rem !important;
  box-shadow: 0 0 4px rgba(108, 117, 125, 0.35) !important;
}

.btn-primary:hover, .btn-success:hover {
  background-color: var(--button-color) !important;
  box-shadow: 0 0 2px #6c757d !important;
}

.btn-primary:active, .btn-success:active {
  box-shadow: 0 0 4px #6c757d inset !important;
}

.btn-primary:focus, .btn-success:focus {
  box-shadow: 0 0 0 0.25rem rgba(232, 241, 82, 0.5) !important;
}
</style>