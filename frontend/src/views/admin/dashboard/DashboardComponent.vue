<template>
  <v-container>
    <v-card>
      <v-card-title>
        <h1>WORK</h1>
        <h5>inventoryByAllProductsLineChartData</h5>
      </v-card-title>
      <v-card-text>
        <line-chart :charts-data="inventoryByAllProductsLineChartData"/>
      </v-card-text>
    </v-card>
    <v-card>
      <v-card-title>
        <h5>inventoryLineByWarehouse</h5>
      </v-card-title>
      <v-card-text>
        <line-chart :charts-data="inventoryByWarehouseLineChartData"/>
      </v-card-text>
    </v-card>
    <v-card>
      <v-card-title>
        <h5>inventoryLineByProduct</h5>
      </v-card-title>
      <v-card-text>
        <line-chart :charts-data="inventoryByProductLineChartData"/>
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
import LineChart from "@/components/charts/LineChart.vue";

export default {
  inject: ['chartsService'],
  components: {
    LineChart,
    BarChart
  },
  data() {
    return {
      inventoryByProductBarChartData: null,
      inventoryByAllWarehousesBarChartData: null,
      inventoryByWarehouseBarChartData: null,
      inventoryTotalStockBarChartData: null,
      inventoryByWarehouseLineChartData: null,
      inventoryByProductLineChartData: null,
      inventoryByAllProductsLineChartData: null,
    }
  },
  async mounted() {
    await this.findAllBarChartData()
    await this.findAllLineChartData()
  },
  methods: {
    async findAllBarChartData() {
      // TODO: Remove hardcoded params
      this.inventoryByProductBarChartData = await this.chartsService.asyncInventoryBarByProduct(1);
      this.inventoryByAllWarehousesBarChartData = await this.chartsService.asyncInventoryBarByAllWarehouses();
      this.inventoryByWarehouseBarChartData = await this.chartsService.asyncInventoryBarByWarehouse(1);
      this.inventoryTotalStockBarChartData = await this.chartsService.asyncInventoryBarByAllProducts();
      this.inventoryByAllProductsLineChartData = await this.chartsService.asyncInventoryLineByAllProducts();
    },
    async findAllLineChartData() {
      this.inventoryByWarehouseLineChartData = await this.chartsService.asyncInventoryLineByWarehouse(
          1,
          '2023-01-01',
          '2023-12-31'
      );
      this.inventoryByProductLineChartData = await this.chartsService.asyncInventoryLineByProduct(
          1,
          '2023-06-01',
          '2023-12-31'
      );
    }
  }
}
</script>

<style scoped>

</style>