<template>
  <div>
    <v-data-table
        :headers="dialogConfig.headers"
        :items="dialogConfig.items"
        :itemsPerPage="dialogConfig.itemsPerPage"
        :search="dialogConfig.searchTerm"
    >
      <template v-slot:top>
      </template>
      <template v-if="tableConfig.allowedActions > 0" v-slot:[`item.actions`]="{ item }">
        <v-btn v-for="(action, key) in actions" :key="key" v-model="item.index" :color="action.color"
               :title="action.title" :to="action.to" @click="handleSelect">
          <v-icon>{{ action.icon }}</v-icon>
        </v-btn>
      </template>
    </v-data-table>
    <dialog-component v-if="tableConfig.allowedActions && selectedItem && allowedActions.length > 0" :detail-tabs="dialogConfig.detailTabs"
                      :dialog="dialogConfig" :item="selectedItem"
                      :item-fields="dialogConfig.itemFields"/>
  </div>
</template>
<script>


import BaseFormDialog from "@/components/BaseFormDialog.vue";
import {all} from "core-js/internals/document-all";

export default {
  computed: {
    all() {
      return all
    }
  },
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
  created() {
    this.actions = this.tableConfig.allowedActions.map(action => {
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