<template>
  <v-container>
    <v-card title="Total Stock Bar Chart">
      <v-card-text>
        <bar-chart :charts-data="inventoryTotalStockBarChartData"/>
      </v-card-text>
    </v-card>
  </v-container>
</template>

<script>
import BarChart from "@/components/base/BaseBarChart.vue";

export default {
  name: "InventoryTotalStockBarChart",
  inject: ['chartsService'],
  components: {
    BarChart
  },
  data() {
    return {
      inventoryTotalStockBarChartData: null,
    }
  },
  async mounted() {
    this.inventoryTotalStockBarChartData = await this.chartsService.asyncInventoryBarByAllProducts();
  }
}
</script>

<style scoped>

</style>