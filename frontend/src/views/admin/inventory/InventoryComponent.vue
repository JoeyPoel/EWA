<template>
  <v-container fluid>
    <base-card icon="mdiSolarPanel" title="Inventory">
      <base-table
          class="elevation-1"
          table-title="Inventory"
          modelName="Product"
          table-actions="['new', 'edit', 'delete', 'detail']"
          :table-items="tableData"
          :tableHeaders="tableHeaders"
          :model-details="modelDetails"
          :model-edit-fields="modelEditFields"
      >
      </base-table>
    </base-card>
  </v-container>
</template>

<script>
import BaseTable from "@/components/base/BaseTable.vue";
import BaseCard from "@/components/base/BaseCard.vue";

export default {
  name: "InventoryComponent",
  inject: ['warehousesService', 'inventoryService'],
  data() {
    return {
      tableData: [],
      tableHeaders: [
        {title: 'ID', key: 'productId'},
        {title: 'Product', key: 'name'},
        {title: 'Quantity', key: 'quantity'},
        {title: 'Price', key: 'price'}
      ],
      modelDetails: [
        {field: 'id', label: 'ID', type: 'number'},
        {field: 'name', label: 'Name', type: 'text'},
        {field: 'description', label: 'Description', type: 'text'},
        {field: 'price', label: 'Price', type: 'number'},
        {field: 'quantity', label: 'Quantity', type: 'number'},
      ],
      modelEditFields: [
        {field: 'name', label: 'Name', type: 'text'},
        {field: 'description', label: 'Description', type: 'text'},
        {field: 'price', label: 'Price', type: 'number'}
      ],
    }
  },
  async mounted() {
    this.tableData = await this.inventoryService.asyncFindAllProductsHavingTransactions();
    console.log(this.tableData)
    // this.tableData.forEach(async (item) => {
    //   item.product = await this.inventoryService.asyncFindProductById(item.productId);
    //   item.warehouse = await this.warehousesService.asyncFindWarehouseById(item.warehouseId);
    // });
  },
  components: {
    BaseTable,
    BaseCard
  },
  computed: {
  },
  watch: {
  },
  methods: {
  },
}
</script>
