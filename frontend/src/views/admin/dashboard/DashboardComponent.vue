<template>
  <v-container>
    <v-card>
      <v-card-title>
        <h5>Line Chart</h5>
      </v-card-title>
      <v-card-text>
        <!--            <line-chart></line-chart>-->
      </v-card-text>
    </v-card>
    <v-card>
      <v-card-title>
        <h5>Inventory Bar By product ADD SELECT PRODUCT</h5>
      </v-card-title>
      <v-card-text>
        <bar-chart :charts-data="inventoryByProductBarChartData"/>
      </v-card-text>
    </v-card>
    <v-card>
      <v-card-title>
        <h5>inventoryBarByAllWarehouses</h5>
      </v-card-title>
      <v-card-text>
        <bar-chart :charts-data="inventoryByAllWarehousesBarChartData"/>
      </v-card-text>
    </v-card>
    <v-card>
      <v-card-title>
        <h5>inventoryBarByWarehouse</h5>
      </v-card-title>
      <v-card-text>
        <bar-chart :charts-data="inventoryByWarehouseBarChartData"/>
      </v-card-text>
    </v-card>
    <v-card>
      <v-card-title>
        <h5>inventoryBarTotalStock</h5>
      </v-card-title>
      <v-card-text>
        <bar-chart :charts-data="inventoryTotalStockBarChartData"/>
      </v-card-text>
    </v-card>
    <v-card>
      <v-card-title>
        <h5>Pie Chart</h5>
      </v-card-title>
      <v-card-text>
        <!--            <pie-chart></pie-chart>-->
      </v-card-text>
    </v-card>
    <v-card>
      <v-card-title>
        <h5>Radar Chart</h5>
      </v-card-title>
      <v-card-text>
        <!--            <radar-chart></radar-chart>-->
      </v-card-text>
    </v-card>
  </v-container>
</template>

<script>
import BarChart from "@/components/charts/BarChart.vue";

export default {
  inject: ['chartsService'],
  components: {
    BarChart,
    // LineChart,
    // RadarChart,
    // PieChart
  },
  data() {
    return {
      inventoryByProductBarChartData: null,
      inventoryByAllWarehousesBarChartData: null,
      inventoryByWarehouseBarChartData: null,
      inventoryTotalStockBarChartData: null,
    }
  },
  async mounted() {
    await this.findAllBarChartData()
  },
  methods: {
    async findAllBarChartData() {
      // TODO: Remove hardcoded product id
      this.inventoryByProductBarChartData = await this.chartsService.asyncInventoryBarByProduct(1);
      this.inventoryByAllWarehousesBarChartData = await this.chartsService.asyncInventoryBarByAllWarehouses();
      this.inventoryByWarehouseBarChartData = await this.chartsService.asyncInventoryBarByWarehouse(1);
      this.inventoryTotalStockBarChartData = await this.chartsService.asyncInventoryBarByAllProducts();
    },

  }
}
</script>

<style scoped>

</style>