<template>
  <v-container :fluid="true">
    <v-card class="mt-1 font-weight-bold">
      <v-card-title class="bg-secondary text-center">
        <h3 class="fs-1">Teams</h3>
      </v-card-title>
      <DataTable :dialog-config="dialog" :table-config="table" @action="handleDialogAction(true, $event.action, $event.item)"
                 @delete="handleDelete" @save="handleSave">
        <template v-slot:filter>
          <data-filter :can-search="true" :can-sort-by-warehouse="true" :warehouse="table.searchTerm"
                       @filterChange="table.searchTerm = $event" @warehouseChange="selectedWarehouse = $event"/>
        </template>
      </DataTable>
    </v-card>
  </v-container>
</template>

<script>
import dataFilter from "@/components/DataFilterComponent.vue";
import DataTable from "@/components/base/DataTable.vue";
import {Team} from "@/models/Team";

export default {
  name: "TeamsComponent",
  inject: ['teamsService', 'warehousesService', 'usersService'],
  components: {
    DataTable,
    dataFilter,
  },
  data() {
    return {
      teams: [],
      warehouses: [],
      users: [],
      selectedWarehouse: null,
      table: {
        entityName: 'Team',
        headers: [
          {title: 'Name', value: 'name', key: 'name'},
          {title: 'Warehouse', value: 'warehouseName', key: 'warehouseName'},
          {title: 'Team Lead', value: 'teamLeadName', key:  'teamLeadName'},
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
      dialog: {
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
    }
  },

  async created() {
    await this.initialize();
  },

  watch: {
    selectedWarehouse: {
      handler: async function () {
        this.table.items = await this.getTeams();
      },
      deep: true
    },
  },

  methods: {
    async initialize() {
      this.warehouses = await this.getWarehouses();
      this.users = await this.getUsers();
      this.table.items = await this.getTeams();

      this.dialog.itemFields[1].items = this.warehouses;
      this.dialog.itemFields[2].items = this.users;
      this.dialog.itemFields[3].items = this.users;
    },
    async getTeams() {
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
    async getWarehouses() {
      let data = await this.warehousesService.asyncFindAll();
      return data.map(warehouse => {
        return {
          id: warehouse.id,
          name: warehouse.name
        }
      })
    },
    async getUsers() {
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
    handleDialogAction(open, action, item) {
      this.dialog.title = action.action;
      this.dialog.item = item;
      this.dialog.open = open;
    },
    async handleSave(item) {
      if (item.id !== undefined) {
        await this.teamsService.asyncUpdate(item.id, item);
      } else {
        await this.teamsService.asyncAdd(item);
      }
      await this.initialize();
    },
    async handleDelete(item) {
      const deletedItem = await this.teamsService.asyncDeleteById(item.id)
      if (deletedItem) {
        this.dialog.item = {};
        this.dialog.open = false;
        await this.initialize();
      } else {
        console.error("Failed to delete item")
      }
    },

  }
}
</script>

<style scoped>

</style>