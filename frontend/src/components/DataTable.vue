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
            <v-icon v-for="(action, key) in table.allowedActions" :key="key" @click="handleAction(action, item)">
              {{ action.icon }}
            </v-icon>
          </template>
        </v-data-table>
        <dialog-component v-if="dialog.open" :item="dialog.item" :item-fields="dialog.itemFields"
                          :max-width="dialog.maxWidth" :open="dialog.open" :title="dialog.title"
                          @close="dialog.open = false" :detail-tabs="dialog.detailTabs"/>
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
    },
    dialogConfig: {
      type: Object,
      required: true,
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
        title: '',
        item: {},
        itemFields: [],
        detailTabs: [],
        maxWidth: '800px',
      },
    };
  },
  watch: {
    dialogConfig: {
      handler: function (newVal) {
        this.dialog.title = newVal.title;
        this.dialog.item = newVal.item;
        this.dialog.itemFields = newVal.itemFields;
        this.dialog.detailTabs = newVal.detailTabs;
        this.dialog.open = newVal.open;
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
    handleAction(action, item) {
      this.$emit('action', {action: action, item: item});
    },
  },
};
</script>