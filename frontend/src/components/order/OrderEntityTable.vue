<template>
  <entity-data-table :dialog-config="dialogConfig" :filter-config="filterConfig" :table-config="tableConfig"
                     title="Orders" @delete="handleDelete" @save="handleSave"
                     @update-tableConfig="tableConfig = $event" @update-dialogConfig="dialogConfig = $event"
                     @update-filterConfig="filterConfig = $event" @warehouse-change="selectedWarehouseId = $event"/>
</template>

<script>
import EntityDataTable from "@/components/base/EntityDataTable.vue";

export default {
  name: "OrderEntityTable",
  components: {EntityDataTable},
  inject: ['orderService', 'warehousesService'],
  data() {
    return {
      selectedWarehouseId: null,
      productLinesCopy: [],
      tableConfig: {
        entityName: 'Order',
        headers: [
          {title: 'ID', key: 'id'},
          {title: 'Warehouse', key: 'warehouseName'},
          {title: 'Order Date', key: 'orderDate'},
          {title: 'Delivery Date', key: 'deliveryDate'},
          {title: 'Actions', key: 'actions', align: 'center', sortable: false}
        ],
        items: [],
        itemsPerPage: 10,
        searchTerm: '',
        actions: [
          {action: 'Details', icon: '$info', color: 'primary'},
          {action: 'Edit', icon: '$edit', color: 'primary'},
        ],
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
          {name: 'description', label: 'Description', type: 'text', required: false},
          {name: 'status', label: 'Status', type: 'orderStatus', required: false},
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
    selectedWarehouseId() {
      this.fetchInventoryItems();
    }
  },
  methods: {
    async initialize() {
      this.warehouses = await this.warehousesService.asyncFindAll();
      this.dialogConfig.itemFields[0].items = this.warehouses;
      await this.fetchOrders();
    },
    async fetchOrders() {
      this.tableConfig.items = this.selectedWarehouseId ?
          await this.orderService.asyncFindAllByWarehouseId(this.selectedWarehouseId) :
          await this.orderService.asyncFindAll();
      this.tableConfig.items = this.tableConfig.items.map(item => {
        item.warehouseName = item.warehouseId ?
            this.warehouses.find(warehouse => warehouse.id === item.warehouseId).name :
            '';
        item.projectName = item.projectId ?
            this.projects.find(project => project.id === item.projectId).name :
            '';
        return item;
      });
      console.log(this.tableConfig.items);
    },
    async handleDelete(item) {
      const deletedItem = await this.orderService.asyncDeleteById(item.id);
      if (deletedItem) {
        this.dialogConfig.item = Object.assign({}, this.dialogConfig.baseObject);
        this.dialogConfig.open = false;
        await this.fetchOrders();
      } else {
        console.error("Failed to delete item");
      }
    },
    async handleSave(item) {
      alert(`Not implemented${JSON.stringify(item)}`);
    },
  }
}
</script>
