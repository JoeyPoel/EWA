                <template>
  <entity-data-table :dialog-config="dialogConfig" :filter-config="filterConfig" :table-config="tableConfig"
                     title="Users" @delete="handleDelete" @save="handleSave"
                     @update-tableConfig="tableConfig = $event" @update-dialogConfig="dialogConfig = $event"
                     @update-filterConfig="filterConfig = $event" @team-change="selectedTeam = $event"/>
</template>

<script>
import EntityDataTable from "@/components/base/EntityDataTable.vue";
import {User} from "@/models/User";

export default {
  name: "UserEntityTable",
  inject: ['teamsService', 'usersService', 'emailService'],
  components: {EntityDataTable},
  data() {
    return {
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
      teams: [],
      projects: [],
      users: [],
      teamMembers: [],
      teamName: '',
      searchTerm: "",
      selectedTeam: null,
      selectedUser: null,
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
  watch: {
    selectedTeam: {
      handler: async function () {
        await this.fetchUsers();
      },
      deep: true
    }
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
    },
    async fetchTeams() {
      let data = this.teams = await this.teamsService.asyncFindAll();
      this.dialogConfig.itemFields.find(field => field.name === 'teamId').items =
          data.map(team => ({name: team.name, id: team.id}));
    },
    generatePassword(length) {
      const characters = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
      let result = '';
      for (let i = 0; i < length; i++) {
        result += characters.charAt(Math.floor(Math.random() * characters.length));
      }
      return result;
    },
    async passwordMail(item, password) {
      try {
        // Pass the email of the newly created user directly
        const email = item.email;
        await this.emailService.sendPassGenEmail(email, password)
      } catch (e) {
        console.error("Failed to send password reset email: ", e);
      }
    },

    async handleSave(item) {
      // Validate the form fields
      if (!item.name || item.name.length < 3) {
        alert("Name must be at least 3 characters.");
        return;
      }

      const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
      if (!item.email || !emailRegex.test(item.email)) {
        alert("Please enter a valid email.");
        return;
      }

      if (!item.role) {
        alert("Please select a role.");
        return;
      }

      if (!item.teamId) {
        alert("Please select a team.");
        return;
      }

      // Generate a password for new users
      let password;
      if (!item.id) {
        password = this.generatePassword(10);
        item.password = password;
      }

      const savedItem = item.id ?
          await this.usersService.asyncUpdate(item.id, item) :
          await this.usersService.asyncSave(item);

      if (savedItem) {
        this.dialogConfig.open = false;

        // If the user is new, send the password via email
        if (!item.id) {
          await this.passwordMail(item, password);
        }
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
    },
  }
}

</script>

<style scoped>
</style>

