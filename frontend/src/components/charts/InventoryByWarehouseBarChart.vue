<template>
  <v-container>
    <v-card title="Warehouses Current Stock">
      <v-card-text>
        <bar-chart :charts-data="inventoryByWarehouseBarChartData"/>
      </v-card-text>
    </v-card>
  </v-container>
</template>

<script>
import BarChart from "@/components/base/BarChart.vue";
export default {
  name: "WarehouseInventoryBarChart",
  inject: ['chartsService'],
  components: {
    BarChart
  },
  data() {
    return {
      inventoryByWarehouseBarChartData: null,
    }
  },
  async mounted() {
    // TODO: get warehouse id from user
    this.inventoryByWarehouseBarChartData =  await this.chartsService.asyncInventoryBarByWarehouse(1);
  }
}
</script>

<style scoped>

</style>