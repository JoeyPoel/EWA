<template>
  <div>
    <v-data-table
        :headers="tableHeaders"
        :items="tableItems"
        :itemsPerPage="itemsPerPage"
        :search="searchTerm"
    >
      <template v-slot:top>
      </template>
      <template v-if="allowedActions > 0" v-slot:[`item.actions`]="{ item }">
        <v-btn v-for="(action, key) in actions" :key="key" v-model="item.index" :color="action.color"
               :title="action.title" :to="action.to" @click="selectedItem = item">
          <v-icon>{{ action.icon }}</v-icon>
        </v-btn>
      </template>
    </v-data-table>
    <dialog-component v-if="allowedActions && selectedItem && allowedActions.length > 0" :dialog="dialogConfig"
                      :item="selectedItem" :item-fields="itemFields" :detail-tabs="detailDialogTabs"/>
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
    tableHeaders: {
      type: Array,
      required: true
    },
    tableItems: {
      type: Array,
      required: true
    },
    itemsPerPage: {
      type: Number,
      default: 10
    },
    searchTerm: {
      type: String,
      default: ''
    },
    itemFields: {
      type: Array,
      required: true
    },
    dialogTitle: {
      type: String,
      required: true
    },
    dialogOpen: {
      type: Boolean,
      required: true
    },
    allowedActions: {
      type: Array,
      required: true
    },
    dialogConfig: {
      type: Object,
      required: true
    },
    detailDialogTabs: {
      type: Array,
      required: false
    }
  },
  data() {
    return {
      actions: [],
      selectedItem: null,
    };
  },
  created() {
    this.actions = this.allowedActions.map(action => {
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
    }
  },
};
</script>