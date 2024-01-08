<template>
  <entity-data-table :dialog-config="dialogConfig" :filter-config="filterConfig" :table-config="tableConfig"
                     title="Users" @delete="handleDelete" @save="handleSave"
                     @update-tableConfig="tableConfig = $event" @update-dialogConfig="dialogConfig = $event"
                     @update-filterConfig="filterConfig = $event" @team-change="selectedTeam = $event"/>
</template>

<script>
import EntityDataTable from "@/components/EntityDataTable.vue";
import {User} from "@/models/User";

export default {
  name: "UsersV2",
  inject: ['teamsService', 'usersService'],
  components: {EntityDataTable},
  data() {
    return {
      selectedTeam: null,
      users: [],
      teams: [],
      tableConfig: {
        entityName: 'User',
        headers: [
          {title: 'Name', value: 'name', key: 'name'},
          {title: 'Role', value: 'role', key: 'role'},
          {title: 'Email', value: 'email', key: 'email'},
          {title: 'Actions', value: 'actions', sortable: false}
        ],
        items: this.users,
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
        baseObject: new User(),
        itemFields: [
          {name: 'name', label: 'Name', type: 'text', required: true},
          {name: 'role', label: 'Role', type: 'select', required: true, items: [
              {name: 'Admin', id: 'ADMIN'},
              {name: 'User', id: 'USER'},
            ]},
          {name: 'email', label: 'Email', type: 'text', required: true},
          {name: 'teamId', label: 'Team', type: 'select', required: true, items: []}
        ],
      },
      filterConfig: {
        canSearch: true,
        canSortByTeam: true,
        selectedTeam: null
      }
    }
  },
  async mounted() {
    await this.initialize();
  },
  methods: {
    async initialize() {
      await this.fetchTeams();
      await this.fetchUsers();
    },
    async fetchUsers() {
      this.users = this.selectedTeam ?
          await this.usersService.asyncFindAllByTeamId(this.selectedTeam) :
          await this.usersService.asyncFindAll();
      this.users.map(user => {
        if (user.team){
          user.teamId = user.team;
          user.team = user.team.name;
        }
      })
      this.tableConfig.items = this.users;
      console.log(this.users)
    },
    async fetchTeams() {
      let data = this.teams = await this.teamsService.asyncFindAll();
      this.dialogConfig.itemFields.find(field => field.name === 'teamId').items =
          data.map(team => ({name: team.name, id: team.id}));
      console.log(this.teams)
    },
    async handleSave(item) {
      console.log(item)
      const savedItem = item.id ?
          await this.usersService.asyncUpdate(item.id, item) :
          await this.usersService.asyncSave(item);
      if (savedItem) {
        console.log(savedItem)
        this.dialogConfig.open = false;
      } else {
        console.error("Failed to save item");
      }
      await this.initialize();
    },
    async handleDelete(item) {
      const deletedItem = await this.usersService.asyncDeleteById(item.id);
      if (deletedItem) {
        this.dialogConfig.open = false;
      } else {
        console.error("Failed to delete item");
      }
      await this.initialize();
    }
  }
}
</script>

<style scoped>

</style>