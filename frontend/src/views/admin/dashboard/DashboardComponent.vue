<template>
  <v-row class="fill-height">
    <v-col>
      <life-time-stats/>
    </v-col>
    <v-col cols="12" md="6">
      <projects-this-mont-pie-chart/>
    </v-col>
    <v-col cols="12" md="6">
      <inventory-by-all-products-line-chart/>
    </v-col>
    <v-col cols="12" md="6">
      <inventory-by-all-warehouses-bar-chart/>
    </v-col>
    <v-col cols="12" md="6">
      <inventory-by-product-bar-chart/>
    </v-col>
    <v-col cols="12" md="6">
      <!--      <inventory-by-warehouse-line-chart/>-->
      <inventory-by-all-warehouses-bar-chart/>
    </v-col>
    <v-col cols="12" md="6">
      <inventory-by-product-line-chart/>
    </v-col>
    <v-col cols="12" md="6">
      <inventory-total-stock-bar-chart/>
    </v-col>
  </v-row>
</template>

<script>
import {
  InventoryByAllProductsLineChart,
  InventoryByAllWarehousesBarChart,
  InventoryByProductBarChart,
  // InventoryByWarehouseLineChart,
  InventoryByProductLineChart,
  InventoryTotalStockBarChart,
  ProjectsThisMontPieChart
} from "@/components/charts";
import LifeTimeStats from "@/components/charts/LifeTimeStats.vue";
// import ProjectsByWarehouseThisMonthPie from "@/components/charts/ProjectsByWarehouseThisMonthPie.vue";

export default {
  components: {
    // ProjectsByWarehouseThisMonthPie,
    InventoryByAllProductsLineChart,
    InventoryByAllWarehousesBarChart,
    InventoryByProductBarChart,
    // InventoryByWarehouseLineChart,
    InventoryByProductLineChart,
    InventoryTotalStockBarChart,
    ProjectsThisMontPieChart,
    LifeTimeStats
  },
  data() {
    return {}
  },
  async mounted() {
  },
  methods: {}
}
</script>

<style scoped lang="sass">
.match-height
  display: flex
  flex-wrap: wrap
  margin: 0 -12px

  > [class*="col"]
    padding: 0 12px
    margin-bottom: 24px
</style>