<template>
  <v-container>
    <v-card title="Product Bar Chart">
      <v-card-text>
        <bar-chart :charts-data="inventoryByProductBarChartData"/>
      </v-card-text>
    </v-card>
  </v-container>
</template>

<script>
import BarChart from "@/components/base/BarChart.vue";

export default {
  name: "InventoryByProductBarChart",
  inject: ['chartsService'],
  components: {
    BarChart
  },
  data() {
    return {
      inventoryByProductBarChartData: null,
    }
  },
  async mounted() {
    this.inventoryByProductBarChartData = await this.chartsService.asyncInventoryBarByProduct(1);
  }
}
</script>

<style scoped>

</style>