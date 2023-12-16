<template>
  <div>
    <v-card>
      <v-card-text>

        <v-data-table
            :headers="tableConfig.headers"
            :items="tableConfig.items"
            :itemsPerPage="tableConfig.itemsPerPage"
            :search="tableConfig.searchTerm"
        >
          <template v-slot:top>
          </template>
          <template v-slot:[`item.actions`]="{ item }">
            <v-icon v-for="(action, key) in actions" :key="key" @click="handleSelect(item)">{{action.icon}}</v-icon>
          </template>
        </v-data-table>
        <dialog-component v-if="tableConfig.allowedActions && selectedItem && allowedActions.length > 0"
                          :detail-tabs="dialogConfig.detailTabs"
                          :dialog="dialogConfig" :item="selectedItem"
                          :item-fields="dialogConfig.itemFields"/>
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
      actions: [],
      selectedItem: null,
    };
  },
  watch: {
    selectedItem: function (val) {
      this.selectedItem = val;
    }
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
      this.$emit('select', item);
    },
  },
};
</script>