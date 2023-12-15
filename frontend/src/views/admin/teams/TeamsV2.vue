<template>
  <v-container :fluid>
    <base-card
        class="mt-1"
        color="secondary"
        title="Teams">
      <data-filter :can-search="true" :can-sort-by-warehouse="true" :search="search"
                   @input="search = $event" @warehouse="selectedWarehouse = $event"/>
      <DataTable :allowed-actions="['View', 'Edit', 'Delete']"
                 :dialog-open="dialog.open"
                 :dialog-title="dialogTitle"
                 :item-fields="dialog.fields"
                 :table-headers="headers"
                 :table-items="teams"
      />
      <BaseFormDialog :dialog-open="dialog.open" :dialog-title="dialogTitle" :item="dialog.item"
                      :itemFields="dialog.fields" @dialog-closed="dialog.open = false" @save-item="saveItem"/>
    </base-card>
  </v-container>
</template>

<script>
import BaseCard from "@/components/base/BaseCard.vue";
import {Team} from "@/models/Team";
import BaseFormDialog from '@/components/BaseFormDialog';
import dataFilter from "@/components/DataFilterComponent.vue";
import DataTable from "@/components/DataTable.vue";

export default {
  name: "TeamsComponent",
  inject: ['teamsService', 'warehousesService', 'usersService'],
  components: {
    DataTable,
    dataFilter,
    BaseCard,
    BaseFormDialog
  },
  data() {
    return {
      teams: [],
      warehouses: [],
      users: [],
      selectedWarehouse: null,
      search: '',
      itemsPerPage: 10,
      dialog: {
        open: false,
        item: new Team(),
        fields: [
          {
            name: 'name',
            label: 'Name',
            type: 'text',
            rules: [
              v => !!v || 'Name is required'
            ]
          },
          {
            name: 'warehouseId',
            label: 'Warehouse',
            type: 'select',
            items: [],
            rules: [
              v => !!v || 'Warehouse is required'
            ]
          },
          {
            name: 'leaderId',
            label: 'Team Lead',
            type: 'select',
            items: [],
            rules: [
              v => !!v || 'Team Lead is required'
            ]
          }
        ]
      },
      dialogTitle: '',
      dialogTitleOptions: {
        'New': 'New Team',
        'Edit': 'Edit Team',
        'Delete': 'Delete Team'
      },
      headers: [
        {title: 'Name', value: 'name'},
        {title: 'Warehouse', value: 'warehouseName'},
        {title: 'Team Lead', value: 'teamLeadName'},
        {title: 'Actions', sortable: false}
      ]
    }
  },

  watch: {
    'this.$route.path': function (val) {
      if (val) {
        switch (val) {
          case '/view':
            this.dialogTitle = 'Team Details';
            this.view(this.selectedTeam);
            break;
          case '/edit':
            this.dialogTitle = 'Edit Team';
            this.edit(this.selectedTeam);
            break;
          case '/delete':
            this.dialogTitle = 'Delete Team';
            this.delete(this.selectedTeam);
            break;
        }
      }
    },
    search(val) {
      this.searchTerm = val;
    },
    async selectedWarehouse(val) {
      if (val) {
        this.teams = await this.teamsService.asyncFindAllByWarehouseId(val);
      } else {
        this.teams = await this.teamsService.asyncFindAll();
      }
    },
    'dialog.open': function (val) {
      val || this.close();
    }
  },

  async created() {
    await this.initialize();
  },

  methods: {
    async initialize() {
      this.warehouses = await this.warehousesService.asyncFindAll();
      this.users = await this.usersService.asyncFindAll();
      this.teams = await this.getTeams();

      this.dialog.fields[1].items = this.warehouses;
      this.dialog.fields[2].items = this.users;
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
    async actionClicked(action) {
      switch (action.action) {
        case 'View':
          this.view(action.item);
          break;
        case 'Edit':
          this.edit(action.item);
          break;
        case 'Delete':
          this.delete(action.item);
          break;
      }
    },
    reset() {
      this.search = '';
      this.selectedWarehouse = null;
    },
    async close() {
      this.dialog.open = false;
      await this.$router.push('/teams');
    },
    saveItem(item) {
      switch (this.dialog.action) {
        case 'New':
          this.saveNew(item);
          break;
        case 'Edit':
          this.saveEdited(item);
          break;
        case 'Delete':
          this.deleteConfirm(item);
          break;
      }
    },
    view(item) {
      this.dialog.action = 'View';
      this.dialog.open = true;
      this.dialog.item = item;
    },
    edit(item) {
      this.dialog.action = 'Edit';
      this.dialog.open = true;
      this.dialog.item = item;
    },
    delete(item) {
      this.dialog.action = 'Delete';
      this.dialog.open = true;
      this.dialog.item = item;
    },
    async saveNew(item) {
      await this.teamsService.asyncAdd(item);
      await this.close();
    },
    async saveEdited(item) {
      await this.teamsService.asyncUpdate(item.id, item);
      await this.close();
    },
    async deleteConfirm(item) {
      await this.teamsService.asyncDeleteById(item.id);
      await this.close();
    },
  }
}
</script>

<style scoped>

</style>