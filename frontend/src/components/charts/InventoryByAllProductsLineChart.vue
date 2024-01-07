<template>
  <v-container>
    <v-card title="All Products Line Chart">
      <v-card-text>
        <line-chart :charts-data="inventoryByAllProductsLineChartData"/>
      </v-card-text>
    </v-card>
  </v-container>
</template>

<script>
import LineChart from "@/components/charts/base/LineChart.vue";

export default {
  name: "InventoryByAllProductsLineChart",
  inject: ['chartsService'],
  components: {
    LineChart
  },
  data() {
    return {
      inventoryByAllProductsLineChartData: null,
    }
  },
  async mounted() {
    // TODO: Remove hard-coded dates
    this.inventoryByAllProductsLineChartData = await this.chartsService.asyncInventoryLineByAllProducts(
        '2023-01-01',
        '2023-12-31'
    );
  }
}
</script>

<style scoped>

</style>