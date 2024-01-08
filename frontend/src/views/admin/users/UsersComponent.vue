<template>
  <v-container fluid>
    <base-card class="mt-1" color="secondary" title="Users">
      <data-filter :search="search" :can-search="true" @input="search = $event"
                   :can-sort-by-team="true" @team="selectedTeam = $event"/>
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
                <v-spacer></v-spacer>
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
                      <v-window v-else-if="dialog.type === 'details'">
                        <v-window-item value="details">
                          <v-row>
                            <v-col>
                              <v-list>
                                <v-list-item>
                                  <v-list-item-title>Name</v-list-item-title>
                                  <v-list-item-subtitle>{{ selectedUser.name }}</v-list-item-subtitle>
                                </v-list-item>
                                <v-list-item>
                                  <v-list-item-title>Email</v-list-item-title>
                                  <v-list-item-subtitle>{{ selectedUser.email }}</v-list-item-subtitle>
                                </v-list-item>
                                <v-list-item>
                                  <v-list-item-title>Role</v-list-item-title>
                                  <v-list-item-subtitle>{{ selectedUser.role }}</v-list-item-subtitle>
                                </v-list-item>
                                <v-list-item>
                                  <v-list-item-title>Team</v-list-item-title>
                                  <v-list-item-subtitle>{{ selectedUser.teamName }}</v-list-item-subtitle>
                                </v-list-item>
                              </v-list>
                            </v-col>
                          </v-row>
                        </v-window-item>
                      </v-window>
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
    <v-snackbar v-model="snackbar.show">
      {{ snackbar.message }}
      <v-btn color="info" text @click="snackbar.show = false">
        Close
      </v-btn>
    </v-snackbar>
  </v-container>
</template>
<script>
import BaseCard from "@/components/base/BaseCard.vue";
import {User} from "@/models/User";
import dataFilter from "@/components/DataFilterComponent.vue";

export default {
  name: "UsersComponent",
  inject: ['teamsService', 'usersService', 'emailService'],
  components: {dataFilter, BaseCard},
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
      snackbar: {
        show: false,
        message: '',
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
        this.users = await this.usersService.asyncFindAllByTeamId(val);
      } else {
        this.users = await this.usersService.asyncFindAll();
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
      this.teams = await this.teamsService.asyncFindAll();
      if (this.selectedTeam) {
        this.users = await this.usersService.asyncFindAllByTeamId(this.selectedTeam);
      } else {
        this.users = await this.usersService.asyncFindAll();
      }
      this.assignSelectedUser(new User());
    },

    generatePassword(length) {
      const characters = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
      let result = '';
      for (let i = 0; i < length; i++) {
        result += characters.charAt(Math.floor(Math.random() * characters.length));
      }
      return result;
    },
    async passwordMail(password) {
      try {
        // Pass the email of the newly created user directly
        const email = this.editedUser.email;
        await this.emailService.sendPassGenEmail(email, password)
      } catch (e) {
        console.error("Failed to send password reset email: ", e);
      }
    },

    async saveNew() {
      // Validate the form fields
      if (!this.editedUser.name || this.editedUser.name.length < 3) {
        alert("Name must be at least 3 characters.");
        return;
      }

      const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
      if (!this.editedUser.email || !emailRegex.test(this.editedUser.email)) {
        alert("Please enter a valid email.");
        return;
      }

      if (!this.editedUser.role) {
        alert("Please select a role.");
        return;
      }

      if (!this.editedUser.team) {
        alert("Please select a team.");
        return;
      }
      //Find the selected team in the teams array
      const selectedTeam = this.teams.find(team => team.id === this.editedUser.team);

      const password = this.generatePassword(10); // Generate a random password of length 10

      const userToSave = {
        name: this.editedUser.name,
        email: this.editedUser.email,
        role: this.editedUser.role,
        team: selectedTeam,
        password: password,
      };

      const savedUser = await this.usersService.asyncSave(userToSave);
      if (savedUser) {
        // Call the passwordMail function to send the password reset email
        await this.passwordMail(userToSave.password);
        await this.close();
      } else {
        this.snackbar.message = "Failed to create user. Please try again.";
        this.snackbar.show = true;
      }
    },

    async deleteConfirm() {
      await this.usersService.asyncDeleteById(this.selectedUser.id);
      await this.close();
    },

    async saveEdited() {
      // Validate the form fields
      if (!this.editedUser.name || this.editedUser.name.length < 3) {
        alert("Name must be at least 3 characters.");
        return;
      }

      const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
      if (!this.editedUser.email || !emailRegex.test(this.editedUser.email)) {
        alert("Please enter a valid email.");
        return;
      }

      if (!this.editedUser.role) {
        alert("Please select a role.");
        return;
      }

      if (!this.editedUser.team) {
        alert("Please select a team.");
        return;
      }

      // Find the selected team in the teams array
      const selectedTeam = this.teams.find(team => team.id === this.editedUser.team);

      const userToSave = {
        name: this.editedUser.name,
        email: this.editedUser.email,
        role: this.editedUser.role,
        team: selectedTeam // Set the team id
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
        const team = await this.teamsService.asyncFindById(user.teamId);
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