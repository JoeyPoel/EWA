<template>
  <v-row>
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
      <inventory-by-warehouse-line-chart/>
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
  InventoryByWarehouseLineChart,
  InventoryByProductLineChart,
  InventoryTotalStockBarChart
} from "@/components/charts";

export default {
  components: {
    InventoryByAllProductsLineChart,
    InventoryByAllWarehousesBarChart,
    InventoryByProductBarChart,
    InventoryByWarehouseLineChart,
    InventoryByProductLineChart,
    InventoryTotalStockBarChart
  },
  data() {
    return {
    }
  },
  async mounted() {
  },
  methods: {
  }
}
</script>

<style scoped>
</style>