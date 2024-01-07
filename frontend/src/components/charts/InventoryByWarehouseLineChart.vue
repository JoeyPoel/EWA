<template>
  <v-container>
    <v-card>
      <v-card-title>

      </v-card-title>
      <v-card-text>
        <line-chart :charts-data="inventoryByWarehouseLineChartData"/>
      </v-card-text>
    </v-card>
  </v-container>
</template>

<script>
import LineChart from "@/components/charts/base/LineChart.vue";

export default {
  name: "InventoryByWarehouseLineChart",
  inject: ['chartsService'],
  components: {
    LineChart
  },
  data() {
    return {
      inventoryByWarehouseLineChartData: null,
    }
  },
  async mounted() {
    this.inventoryByWarehouseLineChartData = await this.chartsService.asyncInventoryLineByWarehouse(
        1,
        '2023-01-01',
        '2023-12-31'
    );
  }
}
</script>

<style scoped>

</style>