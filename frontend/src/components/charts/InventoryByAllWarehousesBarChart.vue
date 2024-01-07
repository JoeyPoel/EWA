<template>
  <v-container>
    <v-card title="All Warehouses Bar Chart">
      <v-card-text>
        <bar-chart :charts-data="inventoryByAllWarehousesBarChartData"/>
      </v-card-text>
    </v-card>
  </v-container>
</template>

<script>
import BarChart from "@/components/charts/base/BarChart.vue";

export default {
  name: "InventoryByAllWarehousesBarChart",
  inject: ['chartsService'],
  components: {
    BarChart
  },
  data() {
    return {
      inventoryByAllWarehousesBarChartData: null,
    }
  },
  async mounted() {
    this.inventoryByAllWarehousesBarChartData = await this.chartsService.asyncInventoryBarByAllWarehouses();
  }
}
</script>

<style scoped>

</style>