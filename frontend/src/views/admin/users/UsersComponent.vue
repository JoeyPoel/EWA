<template>
  <v-container fluid>
    <base-card class="mt-1" color="secondary" title="Users">
      <v-text-field v-model="search" label="Search User" prepend-inner-icon="$search" variant="outlined"/>
      <v-select
          v-model="selectedTeam"
          :items="teams"
          item-title="name"
          item-value="id"
          label="Teams"
      />
      <v-data-table
          v-model:items-per-page="itemsPerPage"
          :headers="headers"
          :items="users"
          :search="search"
          class="elevation-1"
          item-value="id"
      >
        <template v-slot:top>
          <v-toolbar flat>
            <v-dialog v-model="dialog.open" max-width="800px">
              <template v-slot:activator="{ props }">
                <v-btn color="secondary" dark class="mb-2" v-bind="props" @click="showNew">
                  New User</v-btn>
              </template>
              <v-card>
                <v-card-title><h5>{{ dialogTitle[dialog.type] }}</h5></v-card-title>
                <v-card-text>
                  <v-form>
                    <v-container>
                      <template v-if="dialog.type === 'new' || dialog.type === 'edit'">
                        <v-col>
                          <v-row>
                            <v-text-field v-model="editedUser.name" label="Name" type="text"/>
                          </v-row>
                          <v-row>
                            <v-text-field v-model="editedUser.email" label="Email" type="text"/>
                          </v-row>
                          <v-row>
                            <v-select
                                v-model="editedUser.role"
                                :items="roles"
                                item-title="role"
                                item-value="role"
                                label="Role"
                            />
                          </v-row>
                          <v-row>
                            <v-select
                                v-model="editedUser.team"
                                :items="teams"
                                item-title="name"
                                item-value="id"
                                label="Team"
                            />
                          </v-row>

                        </v-col>
                      </template>
                      <template v-if="dialog.type === 'delete'">
                        <h3>Are you sure you want to delete this user?</h3>
                      </template>

                      <template v-else-if="dialog.type === 'details'">
                        <v-col>
                          <v-row>
                            <v-text-field v-model="selectedUser.name" label="Name" type="text" readonly/>
                          </v-row>
                          <v-row>
                            <v-text-field v-model="selectedUser.email" label="Email" type="text" readonly/>
                          </v-row>
                          <v-row>
                            <v-text-field v-model="selectedUser.role" label="Role" type="text" readonly/>
                          </v-row>
                          <v-row>
                            <v-text-field v-model="selectedUser.teamName" label="Team" type="text" readonly/>
                          </v-row>
                        </v-col>
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
import {User} from "@/models/User";

export default {
  name: "UsersComponent",
  inject: ['teamsService', 'usersService'],
  components: {BaseCard},
  data() {
    return {
      teams: [],
      projects: [],
      users: [],
      teamMembers: [],
      teamName: '',
      searchTerm: "",
      selectedTeam: null,
      selectedUser: null,
      team: null,
      dialog: {
        type: null,
        open: false
      },
      editedUser: {
        id: null,
        name: null,
        role: null,
        email: null,
      },
      roles: [
        {role: 'ADMIN'},
        {role: 'USER'},
      ],
      headers: [
        {title: 'Name', value: 'name'},
        {title: 'Role', value: 'role'},
        {title: 'Email', value: 'email'},
        {title: 'Actions', value: 'actions', sortable: false},
      ],
      search: '',
      itemsPerPage: 10,
    }
  },

  computed: {
    dialogTitle() {
      return {
        new: 'New User',
        edit: 'Edit User',
        delete: 'Delete User',
        details: 'User Details',
      }
    },
  },

  watch: {
    search(val) {
      this.searchTerm = val;
    },

    async selectedTeam(val) {
      if (val) {
        this.teams = await this.teamsService.asyncGetById(val.id);
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
    console.log(this.teams)
  },

  methods: {
    async initialize() {
      this.teams = await this.teamsService.asyncGetAll();
      this.users = await this.usersService.asyncGetAll();
      this.assignSelectedUser(new User());
    },

    async saveNew() {
      // Validate the form fields (add additional validation as needed)
      if (!this.editedUser.name || !this.editedUser.email || !this.editedUser.role || !this.editedUser.team) {
        alert("Please fill in all fields.");
        return;
      }

      // Find the selected team in the teams array
      const selectedTeam = this.teams.find(team => team.id === this.editedUser.team);

      const userToSave = {
        name: this.editedUser.name,
        email: this.editedUser.email,
        role: this.editedUser.role,
        team: selectedTeam
      };

      const savedUser = await this.usersService.asyncSave(userToSave);
      if (savedUser) {
        await this.close();
      } else {
        alert("Failed to create user. Please try again.");
      }
    },

    async deleteConfirm() {
      await this.usersService.asyncDeleteById(this.selectedUser.id);
      await this.close();
    },

    async saveEdited() {
      // Validate the form fields (add additional validation as needed)
      if (!this.editedUser.name || !this.editedUser.email || !this.editedUser.role || !this.editedUser.team) {
        alert("Please fill in all fields.");
        return;
      }

      // Find the selected team in the teams array
      const selectedTeam = this.teams.find(team => team.id === this.editedUser.team);

      const userToSave = {
        name: this.editedUser.name,
        email: this.editedUser.email,
        role: this.editedUser.role,
        team: { id: selectedTeam.id } // Set the team id
      };

      const savedUser = await this.usersService.asyncUpdate(this.editedUser.id, userToSave);
      if (savedUser) {
        await this.close();
      } else {
        alert("Failed to update user. Please try again.");
      }
    },

    openDialog(type, user) {
      this.assignSelectedUser(user);
      this.dialog.type = type;
      this.dialog.open = true;
    },

    close() {
      this.dialog.open = false;
      this.initialize();
    },

    async showDetails(user) {

      if (user.teamId) {
        const team = await this.teamsService.asyncGetById(user.teamId);
        console.log(team);
        user.teamName = team ? team.name : 'No team';
      } else {
        user.teamName = 'No team';
      }

      this.openDialog('details', user);
    },

    showEdit(user) {
      this.openDialog('edit', user);
    },

    showDelete(user) {
      this.openDialog('delete', user);
    },

    async showNew() {
      this.openDialog('new', new User());
    },

    assignSelectedUser(user) {
      this.selectedUser = Object.assign(new User(), user);
      this.editedUser = Object.assign(new User(), user);
    },
  }
}
</script>

<style scoped>

</style>