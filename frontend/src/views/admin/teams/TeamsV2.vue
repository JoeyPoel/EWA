<template>
  <v-container fluid>
    <base-card
        class="mt-1"
        color="secondary"
        title="Teams">
      <v-row>
        <v-col cols="5">
          <search-text-field :value="search" label="Search"/>
        </v-col>
        <v-col cols="5">
          <v-select v-model="selectedWarehouse"
                    :items="warehouses"
                    item-title="name"
                    item-value="id"
                    label="Warehouse"
          />
        </v-col>
        <v-col class="justify-content-center" cols="2">
          <v-btn @click="reset">Reset</v-btn>
        </v-col>
      </v-row>
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
import SearchTextField from "@/components/SearchTextField.vue";
import DataTable from "@/components/DataTable.vue";

export default {
  name: "TeamsComponent",
  inject: ['teamsService', 'warehousesService', 'usersService'],
  components: {
    DataTable,
    SearchTextField,
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
        {
          text: 'Name',
          value: 'name'
        },
        {
          text: 'Warehouse',
          value: 'warehouseName'
        },
        {
          text: 'Team Lead',
          value: 'teamLeadName'
        },
        {
          text: 'Actions',
          value: 'actions',
          sortable: false
        }
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
      this.teams = this.selectedWarehouse ?
          await this.teamsService.asyncFindAllByWarehouseId(this.selectedWarehouse) :
          await this.teamsService.asyncFindAll();
      this.warehouses = await this.warehousesService.asyncFindAll();
      this.users = await this.usersService.asyncFindAll();
      this.dialog.fields[1].items = this.warehouses;
      this.dialog.fields[2].items = this.users;
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