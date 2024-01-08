<template>
  <v-container>
    <v-data-table
        :headers="warehouseProductHeaders"
        :items="warehouseProducts"
        :items-per-page-options="[5, 10, 15]"
        :sort-by="['name']"
        class="elevation-1"
    >
    </v-data-table>
  </v-container>
</template>

<script>
export default {
  name: "WarehouseStockLevelsTable",
  inject: ['inventoryService'],
  props: {
    item: {
      type: Object,
      required: true
    },
  },
  data(){
    return {
      warehouseProductHeaders: [
        {title: "Name", value: "name"},
        {title: "Stock", value: "quantity"},
      ],
      warehouseProducts: [],
    }
  },
  async mounted() {
    await this.fetchWarehouseProducts();
  },
  methods: {
    async fetchWarehouseProducts() {
      this.warehouseProducts = await this.inventoryService.asyncFindAllByWarehouseId(this.item.id)
      console.log(this.warehouseProducts)
    },
  }
}
</script>
