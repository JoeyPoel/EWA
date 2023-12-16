<template>
  <div>
    <v-card>
      <v-card-text>
        <v-data-table
            :headers="table.headers"
            :items="table.items"
            :itemsPerPage="table.itemsPerPage"
            :search="table.searchTerm"
        >
          <template v-slot:top>
          </template>
          <template v-slot:[`item.actions`]="{ item }">
            <v-icon v-for="(action, key) in table.allowedActions" :key="key" @click="handleSelect(item)">
              {{action.icon}}
            </v-icon>
          </template>
        </v-data-table>
        <dialog-component v-if="showDialog" :max-width="dialogMaxWidth" :dialog="dialog" :item="dialog.item"/>
      </v-card-text>
    </v-card>
  </div>
</template>
<script>


import BaseFormDialog from "@/components/BaseFormDialog.vue";

export default {
  components: {
    dialogComponent: BaseFormDialog
  },
  props: {
    tableConfig: {
      type: Object,
      required: true,
      // headers: [{title: 'Name', value: 'name'}],
      // items: [{id: 1, name: 'example'}],
      // itemsPerPage: 10,
      // searchTerm: '',
      // allowedActions: ['View', 'Edit', 'Delete', 'New']
    },
    dialogConfig: {
      type: Object,
      required: true,
      //   dialogConfig example:
      //   {
      //     title: 'example',
      //     open: false,
      //     itemFields: [
      //       {
      //         name: 'name',
      //         label: 'Name',
      //         type: 'text',
      //         rules: [
      //           v => !!v || 'Name is required'
      //         ],
      //         disabled: false
      //       }
      //     ],
      //     detailTabs: [
      //       {
      //         title: 'Details',
      //         component: 'user-details',
      //       },
    }
  },
  data() {
    return {
      table: {
        headers: [],
        items: [],
        itemsPerPage: 10,
        searchTerm: '',
        allowedActions: [],
      },
      dialog: {
        open: false,
        action: null,
        item: null,
        itemFields: [],
        detailTabs: [],
      },
    };
  },
  computed: {
    showDialog() {
      if (this.dialogConfig === undefined) {
        console.log('dialogConfig is undefined');
        return false;
      }
      if (!this.dialogConfig.open) {
        console.log('dialogConfig.open is false');
        return false;
      }
      if(!this.tableConfig.allowedActions){
        console.log('tableConfig.allowedActions is undefined');
        return false;
      }
      if (!this.tableConfig.allowedActions.length > 0){
        console.log('tableConfig.allowedActions is empty');
        return false;
      }
      if (!this.actions.length > 0){
        console.log('actions is empty');
        return false;
      }
      return true;
    },
    dialogMaxWidth() {
      return this.dialogConfig.maxWidth ? this.dialogConfig.maxWidth : '800px';
    },
  },
  watch: {
    dialogConfig: {
      handler: function (newVal) {
        this.dialog.open = newVal.open;
        this.dialog.action = newVal.action;
        this.dialog.item = newVal.item;
        this.dialog.itemFields = newVal.itemFields;
        this.dialog.detailTabs = newVal.detailTabs;
      },
      deep: true
    },
    tableConfig: {
      handler: function (newVal) {
        this.table.headers = newVal.headers;
        this.table.items = newVal.items;
        this.table.itemsPerPage = newVal.itemsPerPage;
        this.table.searchTerm = newVal.searchTerm;
        this.table.allowedActions = newVal.allowedActions;
      },
      deep: true
  },
},
  created() {

    this.actions = [...this.tableConfig.allowedActions];
    if (this.actions.length > 0) {
      this.actions.map(action => {
        switch (action) {
          case 'View':
            return {to: '/details', color: 'primary', icon: 'mdi-eye', title: 'Details'};
          case 'Edit':
            return {to: '/edit', color: 'primary', icon: 'mdi-pencil', title: 'Edit'};
          case 'Delete':
            return {to: '/delete', color: 'error', icon: 'mdi-delete', title: 'Delete'};
          default:
            return {to: '', color: 'error', icon: 'mdi-eye', title: 'Unknown action'};
        }
      });
    }
  },
  mounted() {
  },
  methods: {
    handleClose() {
      this.$emit('close');
    },
    handleSelect(item) {
      console.log(`selected item in DataTable: ${JSON.stringify(item)}`);
      this.$emit('select', item);
    },
  },
};
</script>