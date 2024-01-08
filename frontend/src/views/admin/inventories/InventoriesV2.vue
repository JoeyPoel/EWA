<template>
  <entity-data-table :dialog-config="dialogConfig" :filter-config="filterConfig" :table-config="tableConfig"
                     title="Inventories" @delete="handleDelete" @save="handleSave"
                     @update-tableConfig="tableConfig = $event" @update-dialogConfig="dialogConfig = $event"
                     @update-filterConfig="filterConfig = $event" @warehouse-change="handleSelectedWarehouse"/>
</template>

<script>
import EntityDataTable from "@/components/base/EntityDataTable.vue";

export default {
  name: "InventoriesComponentV2",
  inject: ['inventoryService', 'productsService', 'warehousesService'],
  components: {EntityDataTable},
  data() {
    return {
      selectedWarehouse: null,
      inventories: [],
      products: [],
      warehouses: [],
      tableConfig: {
        entityName: 'Inventory',
        headers: [
          {title: 'Product', key: 'name'},
          {title: 'Quantity', key: 'quantity'},
          {title: 'Price', key: 'price'},
          {title: 'Actions', key: 'actions', align: 'center', sortable: false}
        ],
        items: this.inventories,
        itemsPerPage: 10,
        searchTerm: '',
        actions: [
          {action: 'Details', icon: '$info', color: 'primary'},
          {action: 'Edit', icon: '$edit', color: 'primary'},
        ],
        canAdd: true,
      },
      dialogConfig: {
        open: false,
        title: '',
        item: {},
        baseObject: {},
        itemFields: [
          {name: 'id', label: 'ID', type: 'text', required: true},
          {name: 'quantity', label: 'Quantity', type: 'number', required: true},
          {name: 'transactionType', label: 'Transaction Type', type: 'text', required: true},
          {name: 'transactionDate', label: 'Transaction Date', type: 'date', required: true},
          {name: 'productId', label: 'Product ID', type: 'text', required: true},
          {name: 'warehouseId', label: 'Warehouse ID', type: 'text', required: true},
          {name: 'transferFromWarehouseId', label: 'Transfer From Warehouse ID', type: 'text', required: false},
        ],
        detailTabs: [
          {title: 'Stock', component: 'InventoryTransactionsTable'},
        ],
        hasGeneratedDetails: false,
      },
      filterConfig: {
        canSearch: true,
        canSortByWarehouse: true,
        selectedWarehouse: null
      }
    }
  },
  async mounted() {
    await this.initialize();
  },
  methods: {
    async initialize() {
      await this.fetchInventoryItems();
      await this.fetchProducts();
      await this.fetchWarehouses();
    },
    async fetchInventoryItems() {
      this.inventories = this.selectedWarehouse ?
          await this.inventoryService.asyncFindAllByWarehouseId(this.selectedWarehouse.id) :
          await this.inventoryService.asyncFindAll();
      console.log(this.inventories)
      this.tableConfig.items = this.inventories;
    },
    async fetchProducts() {
      let data = await this.productsService.asyncFindAll();
      this.products = data.map(product => ({id: product.id, name: product.name}));
      this.dialogConfig.itemFields.find(field => field.name === 'productId').items = this.products;
    },
    async fetchWarehouses() {
      let data = await this.warehousesService.asyncFindAll();
      this.warehouses = data.map(warehouse => ({id: warehouse.id, name: warehouse.name}));
      this.dialogConfig.itemFields.find(field => field.name === 'warehouseId').items = this.warehouses;
    },
    async handleSave(item) {
      const savedItem = item.id ?
          await this.inventoryService.asyncUpdate(item.id, item) :
          await this.inventoryService.asyncAdd(item);
      if (savedItem) {
        this.dialogConfig.open = false;
      } else {
        console.error("Failed to save item");
      }
      await this.initialize();
    },
    async handleDelete(item) {
      const deletedItem = await this.inventoryService.asyncDeleteById(item.id);
      if (deletedItem) {
        this.dialogConfig.open = false;
      } else {
        console.error("Failed to delete item");
      }
      await this.initialize();
    },
    async handleSelectedWarehouse(warehouse) {
      this.filterConfig.selectedWarehouse = warehouse;
      await this.fetchInventoryItems();
    },
  }
}
</script>

<style scoped>

</style>