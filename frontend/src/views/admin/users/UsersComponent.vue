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

                        </v-col>
                      </template>
                      <template v-if="dialog.type === 'delete'">
                        <h3>Are you sure you want to delete this user?</h3>
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
        details: 'Project Details',
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
    console.log(this.editedUser.teams)
  },

  methods: {
    async initialize() {
      this.teams = await this.teamsService.asyncGetAll();
      this.users = await this.usersService.asyncGetAll();
      //TODO Endpoint for Roles or just use Roles array??
      // this.roles = await this.usersService.asyncGetAll();
      this.assignSelectedUser(new User());
    },

    async saveNew() {
      await this.usersService.asyncSave(this.editedUser);
      await this.close();
    },

    async deleteConfirm() {
      await this.usersService.asyncDeleteById(this.selectedUser.id);
      await this.close();
    },

    async saveEdited() {
      await this.usersService.asyncUpdate(this.editedUser.id, this.editedUser);
      await this.close();
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

    showDetails(user) {
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

    async deleteUser() {
      if (this.selectedUser) {
        await this.usersService.asyncDeleteById(this.selectedUser.id);
        await this.initialize();
      }
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