<template>
  <v-card>
    <v-data-table
        :headers="headers"
        :items="productStockLevels"
        class="elevation-1"/>
  </v-card>
</template>

<script>
export default {
  name: "ProductStockLevelsComponent",
  inject: ['inventoryService'],
  props: {
    item: {
      type: Object,
      required: true
    }
  },
  data() {
    return {
      productStockLevels: [],
      headers: [
        {title: "Warehouse Name", value: "warehouseName", key: "warehouseName"},
        {title: "Stock", value: "stockLevel", key: "stockLevel"}
      ]
    }
  },
  async mounted() {
    await this.fetchProductStockLevels(this.item.id);
  },
  methods: {
    async fetchProductStockLevels(productId) {
      const stockData = await this.inventoryService.asyncFindStockByProductId(productId);
      this.productStockLevels = Object.entries(stockData).map(([warehouseName, stockLevel]) => ({
        warehouseName,
        stockLevel
      }));
    },
  },
}
</script>

<style scoped>

</style>