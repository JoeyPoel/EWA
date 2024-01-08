<template>
  <entity-data-table :dialog-config="dialogConfig" :filter-config="filterConfig" :table-config="tableConfig"
                     title="Warehouses" @delete="handleDelete" @save="handleSave"
                     @update-tableConfig="tableConfig = $event" @update-dialogConfig="dialogConfig = $event"
                     @update-filterConfig="filterConfig = $event"/>
</template>

<script>
import EntityDataTable from "@/components/base/EntityDataTable.vue";
import {Warehouse} from "@/models/Warehouse";

export default {
  name: "WarehousesV2",
  inject: ['warehousesService'],
  components: {EntityDataTable},
  data() {
    return {
      warehouses: [],
      tableConfig: {
        entityName: 'Warehouse',
        headers: [
          {title: 'Name', value: 'name', key: 'name'},
          {title: 'Location', value: 'location', key: 'location'},
          {title: 'Contact Name', value: 'contactName', key: 'contactName'},
          {title: 'Actions', value: 'actions', sortable: false}
        ],
        items: this.warehouses,
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
        baseObject: new Warehouse(),
        itemFields: [
          {name: 'name', label: 'Name', type: 'text', required: true},
          {name: 'location', label: 'Location', type: 'text', required: true},
          {name: 'contactName', label: 'Contact Name', type: 'text', required: true},
          {name: 'contactEmail', label: 'Contact Email', type: 'text', required: true},
          {name: 'contactPhone', label: 'Contact Phone', type: 'text', required: true},
        ],
        detailTabs: [
          {title: 'Stock', component: 'WarehouseStockLevelsTable'},
          {title: 'Teams', component: 'WarehouseTeamsTable'},
          {title: 'Projects', component: 'WarehouseProjectsTable'},
          {title: 'Transactions', component: 'WarehouseTransactionsTable'},
        ]
      },
      filterConfig: {
        canSearch: true,
        canSortByWarehouse: false,
        canSortByTeam: false,
        selectedWarehouse: null
      }
    }
  },
  async mounted() {
    await this.initialize();
  },
  methods: {
    async initialize() {
      this.warehouses = await this.warehousesService.asyncFindAll();
      this.tableConfig.items = this.warehouses;
      this.dialogConfig.itemFields[0].items = this.warehouses;
    },
    async handleSave(item) {
      if (item.id) {
        await this.warehousesService.update(item);
      } else {
        await this.warehousesService.create(item);
      }
      await this.initialize();
    },
    async handleDelete(item) {
      await this.warehousesService.delete(item.id);
      await this.initialize();
    }
  }
}
</script>