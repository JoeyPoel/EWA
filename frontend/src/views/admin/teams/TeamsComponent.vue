<template>
  <v-container fluid>
    <base-card class="mt-1" color="secondary" title="Teams">
      <v-data-table
          v-model:items-per-page="itemsPerPage"
          :headers="headers"
          :items="teams"
          :search="search"
          class="elevation-1"
          item-value="id"
      >
        <template v-slot:[`item.warehouseName`]="{ item }">
          {{ getWarehouseName(item) }}
        </template>
        <template v-slot:[`item.teamLeadName`]="{ item }">
          {{ getTeamLeadName(item) }}
        </template>
        <template v-slot:top>
          <v-toolbar flat>
            <v-dialog v-model="dialog.open" max-width="800px">
              <template v-slot:activator="{ props }">
                <v-text-field v-model="search" label="Search Team" prepend-inner-icon="$search" variant="outlined"/>
                <v-select
                    v-model="selectedWarehouse"
                    :items="warehouses"
                    item-title="name"
                    item-value="id"
                    label="Warehouse"
                />
                <v-btn color="secondary" dark class="mb-2" v-bind="props" @click="showNew">
                  New Team</v-btn>
              </template>
              <v-card>
                <v-card-title><h5>{{ dialogTitle[dialog.type] }}</h5></v-card-title>
                <v-card-text>
                  <v-form>
                    <v-container>
                      <template v-if="dialog.type === 'new' || dialog.type === 'edit'">
                        <v-col>
                          <v-row>
                            <v-text-field v-model="editedTeam.name" label="Name" type="text"/>
                          </v-row>
                          <v-row>
                            <v-select
                                v-model="editedTeam.warehouseId"
                                :items="warehouses"
                                item-title="name"
                                item-value="id"
                                label="Warehouse"
                            />
                          </v-row>
                          <v-row>
                            <v-select
                                v-model="editedTeam.leaderId"
                                :items="users"
                                item-title="name"
                                item-value="id"
                                label="Team Lead"
                            />
                          </v-row>
                          <v-row>
                            <v-select
                                v-model="editedTeam.membersIds"
                                :items="possibleTeamMembers"
                                item-title="name"
                                item-value="id"
                                label="Team Members"
                                multiple
                                chips
                            />
                          </v-row>
                        </v-col>
                      </template>
                      <template v-if="dialog.type === 'delete'">
                        <h3>Are you sure you want to delete this team?</h3>
                      </template>
                      <template v-else-if="dialog.type === 'details'">
                        <h1>In development</h1>
                      </template>
                    </v-container>
                  </v-form>
                </v-card-text>
                <v-card-actions>
                  <v-spacer></v-spacer>
                  <v-btn variant="text" @click="close">Cancel</v-btn>
                  <v-btn v-if="dialog.type === 'new'" variant="text" @click="saveNew">Save</v-btn>
                  <v-btn v-if="dialog.type === 'edit'" variant="text" @click="saveEdited">Save</v-btn>
                  <v-btn v-if="dialog.type === 'delete'" variant="text" @click="deleteConfirm">Delete</v-btn>
                </v-card-actions>
              </v-card>
            </v-dialog>
          </v-toolbar>
        </template>
        <template v-slot:[`item.actions`]="{ item }">
          <v-icon small class="mr-2" @click="showDetails(item)">$info</v-icon>
          <v-icon small class="mr-2" @click="showEdit(item)">$edit</v-icon>
          <v-icon small @click="showDelete(item)">$delete</v-icon>
        </template>
        <template v-slot:no-data>
          <v-btn color="primary" @click="initialize">Reset</v-btn>
        </template>
      </v-data-table>
    </base-card>
  </v-container>
</template>

<script>
import BaseCard from "@/components/base/BaseCard.vue";
import {Team} from "@/models/Team";

export default {
  name: "TeamsComponent",
  inject: ['teamsService', 'warehousesService', 'usersService'],
  components: {
    BaseCard,
  },
  data() {
    return {
      teams: [],
      warehouses: [],
      users: [],
      teamMembers: [],
      possibleTeamMembers: [],
      selectedWarehouse: null,
      teamName: '',
      searchTerm: "",
      selectedTeam: null,
      team: null,
      dialog: {
        type: null,
        open: false
      },
      editedTeam: {
        id: null,
        name: null,
        warehouseId: null,
        teamLeadId: null,
        teamMembers: [],
      },
      headers: [
        {title: 'Name', value: 'name'},
        {title: 'Warehouse', value: 'warehouseName'},
        {title: 'Team Lead', value: 'teamLeadName'},
        {title: 'Actions', value: 'actions', sortable: false},
      ],
      search: '',
      itemsPerPage: 10,
    }
  },

  computed: {
    dialogTitle() {
      return {
        new: 'New Team',
        edit: 'Edit Team',
        delete: 'Delete Team',
        details: 'Team Details',
      }
    },
  },

  watch: {
    search(val) {
      this.searchTerm = val;
    },
    async selectedWarehouse(val) {
      if (val) {
        this.teams = await this.teamsService.asyncGetAllByWarehouseId(val.id);
      } else {
        this.teams = await this.teamsService.asyncGetAll();
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
      this.teams = await this.teamsService.asyncGetAll();
      this.warehouses = await this.warehousesService.asyncGetAll();
      this.users = await this.usersService.asyncGetAll();
      this.possibleTeamMembers = await this.usersService.asyncGetAllByNoTeam();
      this.assignSelectedTeam(new Team());

      console.log(this.users);
      console.log(this.teams);
    },

    async saveNew() {
      console.log(this.editedTeam);
      const saved = await this.teamsService.asyncAdd(this.editedTeam);
      console.log('Saved: ', saved);
      await this.close();
    },

    async deleteConfirm() {
      await this.teamsService.asyncDeleteById(this.selectedTeam.id);
      await this.close();
    },

    async saveEdited() {
      console.log(this.editedTeam);
      const saved = await this.teamsService.asyncUpdate(this.editedTeam.id, this.editedTeam);
      console.log('Saved edited: ', saved);
      await this.close();
    },

    openDialog(type, team) {
      this.assignSelectedTeam(team);
      this.dialog.type = type;
      this.dialog.open = true;
    },

    close() {
      this.dialog.open = false;
      // this.initialize();
    },

    getWarehouseName(team) {
      if (team.warehouseId && this.warehouses.length > 0) {
        return this.warehouses.find(warehouse => warehouse.id === team.warehouseId).name;
      }
      return '';
    },

    getTeamLeadName(team) {
      if (team.teamLeadId && this.users.length > 0) {
        return this.users.find(user => user.id === team.teamLeadId).name;
      }
      return '';
    },

    showDetails(team) {
      this.openDialog('details', team);
    },

    showEdit(team) {
      this.openDialog('edit', team);
    },

    showDelete(team) {
      this.openDialog('delete', team);
    },

    async showNew() {
      this.openDialog('new', new Team());
    },

    async deleteTeam() {
      if (this.selectedTeam) {
        await this.teamsService.asyncDeleteById(this.selectedTeam.id);
        await this.initialize();
      }
    },

    assignSelectedTeam(team) {
      this.selectedTeam = Object.assign(new Team(), team);
      this.editedTeam = Object.assign(new Team(), team);
    },
  }
}
</script>

<style scoped>

</style>