<template>
  <v-row>
    <v-col cols="12">
      <life-time-stats>
        <template v-slot:warehouse>
          <v-select v-model="warehouseId" :items="warehouses" item-title="name" item-value="id" label="Warehouse"
                    outlined @change="updateChartData" icon="$warehouse"/>
        </template>
      </life-time-stats>
    </v-col>
    <v-col cols="12" md="6">
      <projects-bar :warehouse-id="this.warehouseId"/>
    </v-col>
    <v-col cols="12" md="6">
      <inventory-line :warehouse-id="this.warehouseId"/>
    </v-col>
  </v-row>
</template>

<script>
import LifeTimeStats from "@/components/charts/LifeTimeStats.vue";
import ProjectsBar from "@/components/charts/ProjectsBar.vue";
import InventoryLine from "@/components/charts/InventoryLine.vue";

export default {
  components: {
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
    this.warehouses = await this.warehousesService.asyncGetAll();
  },
  methods: {
    updateChartData() {
      this.$refs.projectsBar.updateChartData();
      this.$refs.inventoryLine.updateChartData();
    }
  }
}
</script>



