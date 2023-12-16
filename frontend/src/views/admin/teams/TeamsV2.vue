<template>
  <v-container :fluid="true">
    <base-card
        class="mt-1"
        color="secondary"
        title="Teams">
      <data-filter :can-search="true" :can-sort-by-warehouse="true" :search="table.searchTerm"
                   @input="table.searchTerm = $event" @warehouse="selectedWarehouse = $event"/>
      <DataTable :dialog-config="dialog" :table-config="table" @close="handleClose"
                 @action="handleDialogAction(true, $event.action, $event.item)" />
    </base-card>
  </v-container>
</template>

<script>
import BaseCard from "@/components/base/BaseCard.vue";
import {Team} from "@/models/Team";
import dataFilter from "@/components/DataFilterComponent.vue";
import DataTable from "@/components/DataTable.vue";

export default {
  name: "TeamsComponent",
  inject: ['teamsService', 'warehousesService', 'usersService'],
  components: {
    DataTable,
    dataFilter,
    BaseCard,
  },
  data() {
    return {
      teams: [],
      warehouses: [],
      users: [],
      selectedWarehouse: null,
      table:{
        headers: [
          {title: 'Name', value: 'name'},
          {title: 'Warehouse', value: 'warehouseName'},
          {title: 'Team Lead', value: 'teamLeadName'},
          {title: 'Actions', value:'actions', sortable: false}
        ],
        items: this.teams,
        itemsPerPage: 10,
        searchTerm: '',
        allowedActions: [
          {action: 'Details', icon: '$info', color: 'primary'},
          {action: 'Edit', icon: '$edit', color: 'primary'},
          {action: 'Delete', icon: '$delete', color: 'error'},
        ]
      },
      dialog: {
        open: false,
        title: '',
        item: new Team(),
        itemFields: [
          {name: 'name', label: 'Name', type: 'text', required: true},
          {name: 'warehouseId', label: 'Warehouse', type: 'select', required: true, items: []},
          {name: 'leaderId', label: 'Team Lead', type: 'select', required: true, items: []},
          {name: 'membersIds', label: 'Team Members', type: 'selectMultiple', required: false, items: []}
        ],
        detailTabs: [
          // {title: 'Team Members', component: 'TeamMembersComponent'}
        ]
      },
    }
  },


  async created() {
    await this.initialize();
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
    handleDialogAction(open,action, item) {
      console.log(open, action, item)
      this.dialog.title = action.action;
      this.dialog.item = item;
      this.dialog.open = open;
      console.log(this.dialog.open)
    },
    async handleClose() {
      this.handleDialogAction(false, null, new Team());
      this.table.items = await this.getTeams();
    },
  }
}
</script>

<style scoped>

</style>