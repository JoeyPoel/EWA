<template>
  <v-row>
    <v-row>
      <v-col cols="12">
        <life-time-stats ref="lifeTimeStats" :warehouse-id="this.warehouseId">
          <template v-slot:warehouse>
            <v-col cols="12">
              <data-filter :can-sort-by-warehouse="true" @warehouse="this.warehouseId = $event"/>
            </v-col>
          </template>
        </life-time-stats>
      </v-col>
      <v-col cols="12" md="6">
        <projects-bar ref="projectsBar" :warehouse-id="this.warehouseId"/>
      </v-col>
      <v-col cols="12" md="6">
        <inventory-line ref="inventoryLine" :warehouse-id="this.warehouseId"/>
      </v-col>
    </v-row>
  </v-row>
</template>

<script>
import LifeTimeStats from "@/components/charts/LifeTimeStats.vue";
import ProjectsBar from "@/components/charts/ProjectsBar.vue";
import InventoryLine from "@/components/charts/InventoryLine.vue";
import DataFilter from "@/components/base/DataFilterComponent.vue";

export default {
  components: {
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
  methods: {
    updateChartData() {
      this.$refs.projectsBar.updateChartData();
      this.$refs.inventoryLine.updateChartData();
    }
  }
}
</script>



