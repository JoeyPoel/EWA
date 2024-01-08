<template>
  <entity-data-table :dialog-config="dialogConfig" :filter-config="filterConfig" :table-config="tableConfig"
                     title="Inventories" @delete="handleDelete" @save="handleSave"
                     @update-tableConfig="tableConfig = $event" @update-dialogConfig="dialogConfig = $event"
                     @update-filterConfig="filterConfig = $event" @warehouse-change="selectedWarehouse = $event"/>
</template>

<script>
import EntityDataTable from "@/components/base/EntityDataTable.vue";

export default {
  name: "OrderEntityTable",
  components: {EntityDataTable},
  inject: ['inventoryService', 'productsService', 'warehousesService'],
  data() {
    return {
      selectedWarehouse: null,
      tableConfig: {
        entityName: 'Order',
        headers: [
          {title: 'ID', key: 'id'},
          {title: 'Warehouse', key: 'warehouseId'},
          {title: 'Order Date', key: 'orderDate'},
          {title: 'Delivery Date', key: 'deliveryDate'},
          {title: 'Project', key: 'projectId'},
          {title: 'Actions', key: 'actions', align: 'center', sortable: false}
        ],
        items: [],
        itemsPerPage: 10,
        searchTerm: '',
        actions: [
          {action: 'Details', icon: '$info', color: 'primary'},
          {action: 'Edit', icon: '$edit', color: 'primary'},
        ],
        canAdd: false,
      },
      dialogConfig: {
        open: false,
        title: '',
        item: {},
        baseObject: {},
        itemFields: [
          {name: 'warehouseId', label: 'Warehouse', type: 'select', required: true, items: []},
          {name: 'orderDate', label: 'Order Date', type: 'date', required: true},
          {name: 'deliveryDate', label: 'Delivery Date', type: 'date', required: false},
          {name: 'projectId', label: 'Project ID', type: 'number', required: false},
        ],
        detailTabs: [
          {title: 'Order Items', component: 'OrderItemsTable'},
        ],
        maxWidth: '1000px',
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
  watch: {
    selectedWarehouse() {
      this.fetchInventoryItems();
    }
  },
  methods: {
    async initialize() {
      this.warehouses = await this.warehousesService.asyncFindAll();
      this.products = await this.productsService.asyncFindAll();
      await this.fetchOrders();
    },
    async fetchOrders() {
     alert(`Not implemented`);
     this.tableConfig.items = [];
    },
    async handleDelete(item) {
      alert(`Not implemented${JSON.stringify(item)}`);
    },
    async handleSave(item) {
      alert(`Not implemented${JSON.stringify(item)}`);
    },
  }
}
</script>
