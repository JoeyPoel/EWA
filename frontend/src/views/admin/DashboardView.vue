<template>
  <v-container :fluid="true">
    <v-row>
      <v-col cols="12">
        <v-card class="pt-4 font-weight-bold" variant="elevated">
          <data-filter :can-sort-by-warehouse="true" @warehouse-change="this.warehouseId = $event"/>
        </v-card>
      </v-col>
      <v-col cols="12" md="6">
        <projects-bar ref="projectsBar" :warehouse-id="this.warehouseId"/>
      </v-col>
      <v-col cols="12" md="6">
        <inventory-line ref="inventoryLine" :warehouse-id="this.warehouseId"/>
      </v-col>
      <v-col cols="12" md="6">
        <life-time-stats ref="lifeTimeStats" :warehouse-id="this.warehouseId"/>
      </v-col>
      <v-col cols="12" md="6">
        <v-container>
          <v-card class="mt-1 font-weight-bold">
            <v-card-title class="bg-secondary text-center"><h5 class="fs-3">Teams</h5></v-card-title>
            <WarehouseTeamsTable :density="'compact'" :items-per-page="5" :warehouseId="this.warehouseId"/>
          </v-card>
        </v-container>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import LifeTimeStats from "@/components/charts/LifeTimeStats.vue";
import ProjectsBar from "@/components/charts/ProjectsBar.vue";
import InventoryLine from "@/components/charts/InventoryLine.vue";
import DataFilter from "@/components/base/DataFilterComponent.vue";
import WarehouseTeamsTable from "@/components/warehouses/WarehouseTeamsTable.vue";

export default {
  name: "DashboardViewAdmin",
  components: {
    WarehouseTeamsTable,
    DataFilter,
    ProjectsBar,
    InventoryLine,
    LifeTimeStats
  },
  inject: ['warehousesService'],
  data() {
    return {
      warehouseId: null,
      warehouses: [],
    }
  },
  async mounted() {
    this.warehouses = await this.warehousesService.asyncFindAll();
    this.warehouses.unshift({id: null, name: 'All warehouses'});
  },
}
</script>



