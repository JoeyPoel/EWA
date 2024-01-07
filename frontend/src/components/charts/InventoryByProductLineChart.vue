<template>
  <v-container>
    <v-card title="Product Line Chart">
      <v-card-text>
        <line-chart :charts-data="inventoryByProductLineChartData"/>
      </v-card-text>
    </v-card>
  </v-container>
</template>

<script>
import LineChart from "@/components/charts/base/LineChart.vue";

export default {
  name: "InventoryByProductLineChart",
  inject: ['chartsService'],
  components: {
    LineChart
  },
  data() {
    return {
      inventoryByProductLineChartData: null,
    }
  },
  async mounted() {
    this.inventoryByProductLineChartData = await this.chartsService.asyncInventoryLineByProduct(
        1,
        '2023-06-01',
        '2023-12-31'
    );
  }
}
</script>

<style scoped>

</style>