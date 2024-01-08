<template>
  <entity-data-table :dialog-config="dialogConfig" :filter-config="filterConfig" :table-config="tableConfig"
                     title="Teams" @delete="handleDelete" @save="handleSave"
                     @update-tableConfig="tableConfig = $event" @update-dialogConfig="dialogConfig = $event"
                     @update-filterConfig="filterConfig = $event" @warehouse-change="selectedWarehouse = $event"/>
</template>
<script>
import {Team} from "@/models/Team";
import EntityDataTable from "@/components/EntityDataTable.vue";

export default {
  name: "TeamsComponent",
  inject: ['teamsService', 'warehousesService', 'usersService'],
  components: {
    EntityDataTable,

  },
  data() {
    return {
      selectedWarehouse: null,
      teams: [],
      warehouses: [],
      users: [],
      tableConfig: {
        entityName: 'Team',
        headers: [
          {title: 'Name', value: 'name', key: 'name'},
          {title: 'Warehouse', value: 'warehouseName', key: 'warehouseName'},
          {title: 'Team Lead', value: 'teamLeadName', key: 'teamLeadName'},
          {title: 'Actions', value: 'actions', sortable: false}
        ],
        items: this.teams,
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
        baseObject: new Team(),
        itemFields: [
          {name: 'name', label: 'Name', type: 'text', required: true},
          {name: 'warehouseId', label: 'Warehouse', type: 'select', required: true, items: []},
          {name: 'leaderId', label: 'Team Lead', type: 'select', required: true, items: []},
          {name: 'membersIds', label: 'Team Members', type: 'selectMultiple', required: false, items: []}
        ],
        detailTabs: [
          {title: 'Projects', component: 'TeamProjectsTable'},
        ]
      },
      filterConfig: {
        canSearch: true,
        canSortByWarehouse: true,
        selectedWarehouse: null
      }
    }
  },

  async created() {
    await this.initialize();
  },

  watch: {
    selectedWarehouse: {
      handler: async function () {
        this.tableConfig.items = await this.fetchTeams();
      },
      deep: true
    },
  },

  methods: {
    async initialize() {
      this.warehouses = await this.fetchWarehouses();
      this.users = await this.fetchUsers();
      this.tableConfig.items = await this.fetchTeams();

      this.dialogConfig.itemFields[1].items = this.warehouses;
      this.dialogConfig.itemFields[2].items = this.users;
      this.dialogConfig.itemFields[3].items = this.users;
    },
    async fetchTeams() {
      let teams = this.selectedWarehouse ?
          await this.teamsService.asyncFindAllByWarehouseId(this.selectedWarehouse) :
          await this.teamsService.asyncFindAll();
      return teams.map(team => {
        return {
          ...team,
          warehouseName: this.mapWarehouseName(team.warehouseId),
          teamLeadName: this.mapTeamLeadName(team.leaderId)
        }
      })
    },
    async fetchWarehouses() {
      let data = await this.warehousesService.asyncFindAll();
      return data.map(warehouse => {
        return {
          id: warehouse.id,
          name: warehouse.name
        }
      })
    },
    async fetchUsers() {
      let data = await this.usersService.asyncFindAll();
      return data.map(user => {
        return {
          id: user.id,
          name: user.name
        }
      });
    },
    mapWarehouseName(warehouseId) {
      const warehouse = this.warehouses.find(warehouse => warehouse.id === warehouseId);
      if (warehouse) {
        return warehouse.name;
      }
    },
    mapTeamLeadName(teamLeadId) {
      const teamLead = this.users.find(user => user.id === teamLeadId);
      if (teamLead) {
        return teamLead.name;
      }
    },
    async handleSave(item) {
      const savedItem = item.id !== undefined ?
          await this.teamsService.asyncUpdate(item.id, item) :
          await this.teamsService.asyncAdd(item);
      if (savedItem) {
        this.dialogConfig.item = {};
        this.dialogConfig.open = false;
        await this.initialize();
      } else {
        // TODO: Show error message
        console.error("Failed to save item")
      }
    },
    async handleDelete(item) {
      const deletedItem = await this.teamsService.asyncDeleteById(item.id)
      if (deletedItem) {
        this.dialogConfig.item = {};
        this.dialogConfig.open = false;
        await this.initialize();
      } else {
        console.error("Failed to delete item")
      }
    }
  }
}
</script>

<style scoped>

</style>