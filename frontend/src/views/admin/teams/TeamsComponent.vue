<template>
  <v-container :fluid="true">
    <base-card class="mt-1" color="primary" title="Teams">
      <data-filter :search="search" :can-search="true" @input="search = $event"
                   :can-sort-by-warehouse="true" @warehouse="selectedWarehouse = $event"/>
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
                <v-btn class="mb-2" color="secondary" dark v-bind="props" @click="showNew">
                  New Team
                </v-btn>
              </template>
              <v-card :title="dialogTitle[dialog.type]">
                <v-card-text>
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
                              :items="users"
                              chips
                              item-title="name"
                              item-value="id"
                              label="Team Members"
                              multiple
                          />
                        </v-row>
                      </v-col>
                    </template>
                    <template v-if="dialog.type === 'delete'">
                      <h3>Are you sure you want to delete this team?</h3>
                    </template>
                    <template v-else-if="dialog.type === 'details'">
                      <v-tabs v-model="detailTab" bg-color="transparent">
                        <v-tab value="details">Details</v-tab>
                        <v-tab value="projects">Projects</v-tab>
                      </v-tabs>
                      <v-window v-model="detailTab">
                        <v-window-item value="details">
                          <v-list>
                            <v-list-item :subtitle="selectedTeam.name" title="Name"/>
                            <v-list-item :subtitle="getWarehouseName(selectedTeam)" title="Warehouse"/>
                            <v-list-item :subtitle="getTeamLeadName(selectedTeam)" title="Team Lead"/>
                            <v-list-item title="Team Members">
                              <v-list-item-subtitle v-for="member in selectedTeamUsers" :key="member.id">
                                {{ member.name }}
                              </v-list-item-subtitle>
                            </v-list-item>
                          </v-list>
                        </v-window-item>
                        <v-window-item value="projects">
                          <v-container>
                            <v-card>
                              <v-card-text>
                                <v-data-table
                                    :headers="projectHeaders"
                                    :items="selectedTeamProjects"
                                    class="elevation-1"
                                    item-value="name">
                                  <template v-slot:[`item.warehouseName`]="{ item }">
                                    {{ getWarehouseName(item) }}
                                  </template>
                                </v-data-table>
                              </v-card-text>
                            </v-card>
                          </v-container>
                        </v-window-item>
                      </v-window>
                    </template>
                  </v-container>
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
          <v-icon @click="showDetails(item)">$info</v-icon>
          <v-icon @click="showEdit(item)">$edit</v-icon>
          <v-icon @click="showDelete(item)">$delete</v-icon>
        </template>
        <template v-slot:no-data>
          <v-btn color="secondary" @click="initialize">Reset</v-btn>
        </template>
      </v-data-table>
    </base-card>
  </v-container>
</template>

<script>
import BaseCard from "@/components/base/BaseCard.vue";
import {Team} from "@/models/Team";
import dataFilter from "@/components/DataFilterComponent.vue";

export default {
  name: "TeamsComponent",
  inject: ['teamsService', 'warehousesService', 'usersService', 'projectsService'],
  components: {
    dataFilter,
    BaseCard,
  },
  data() {
    return {
      teams: [],
      warehouses: [],
      users: [],
      teamMembers: [],
      selectedWarehouse: null,
      teamName: '',
      searchTerm: "",
      detailTab: 'details',
      selectedTeam: null,
      selectedTeamProjects: [],
      team: null,
      dialog: {
        type: null,
        open: false
      },
      editedTeam: {
        id: null,
        name: '',
        warehouseId: null,
        leaderId: null,
        teamMembers: [],
      },
      headers: [
        {title: 'Name', value: 'name'},
        {title: 'Warehouse', value: 'warehouseName'},
        {title: 'Team Lead', value: 'teamLeadName'},
        {title: 'Actions', value: 'actions', sortable: false},
      ],
      projectHeaders: [
        {title: 'ID', value: 'id'},
        {title: 'Location', value: 'location'},
        {title: 'Start Date', value: 'startDate'},
        {title: 'End Date', value: 'endDate'},
        {title: 'Status', value: 'status'},
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
    selectedTeamUsers() {
      if (this.selectedTeam && this.users.length > 0 && this.selectedTeam.membersIds !== undefined) {
        return this.users.filter(user => this.selectedTeam.membersIds.includes(user.id));
      }
      return [];
    }
  },

  watch: {
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
    // 'dialog.open': function (val) {
    //   if (!val) {
    //     this.$router.push('/admin/teams');
    //   }else {
    //     this.$router.push('/admin/teams/' + this.dialog.type + '/' + this.selectedTeam.id);
    //   }
    // },
    selectedTeam(val) {
      if (val) {
        return val;
      }
      return null;
    }
  },

  async created() {
    await this.initialize();
  },

  methods: {
    async initialize() {
      this.teams = this.selectedWarehouse ?
          await this.teamsService.asyncFindAllByWarehouseId(this.selectedWarehouse) :
          await this.teamsService.asyncFindAll();
      this.warehouses = await this.warehousesService.asyncFindAll();
      this.users = await this.usersService.asyncFindAll();
      this.assignSelectedTeam(new Team());
    },

    async saveNew() {
      await this.teamsService.asyncAdd(this.editedTeam);
      await this.close();
    },

    async deleteConfirm() {
      console.log(this.editedTeam.id)
      console.log(await this.teamsService.asyncDeleteById(this.editedTeam['id']))
      await this.close();
    },

    async saveEdited() {
      await this.teamsService.asyncUpdate(this.editedTeam.id, this.editedTeam);
      await this.close();
    },

    openDialog(type, team) {
      this.assignSelectedTeam(team);
      this.dialog.type = type;
      this.dialog.open = true;
    },

    close() {
      this.dialog.open = false;
      this.initialize();
    },

    getWarehouseName(team) {
      if (team.warehouseId && this.warehouses.length > 0) {
        return this.warehouses.find(warehouse => warehouse.id === team.warehouseId).name;
      }
      return '';
    },

    getTeamLeadName(team) {
      if (team.leaderId && this.users.length > 0) {
        return this.users.find(user => user.id === team.leaderId).name;
      }
      return '';
    },

    async showDetails(team) {
      this.openDialog('details', team);
      this.selectedTeamProjects = await this.projectsService.asyncFindAllByTeamId(team.id);
    },

    showEdit(team) {
      this.openDialog('edit', team);
    },

    showDelete(team) {
      console.log(JSON.stringify(team))
      console.log(team['id'])
      for (let i = 0; i < this.teams.length; i++) {
        console.log(this.teams[i])
      }
      this.openDialog('delete', team);
    },

    async showNew() {
      this.openDialog('new', new Team());
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