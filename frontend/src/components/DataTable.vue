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
        <v-btn v-for="(action, key) in actions" :key="key" :color="action.color" :title="action.title"
               :to="action.to" v-model="item.index">
          <v-icon>{{ action.icon }}</v-icon>
        </v-btn>
      </template>
    </v-data-table>
    <DialogComponent :open="dialogOpen" :title="dialogTitle" @close="handleClose">
      <template v-slot:content>
      </template>
    </DialogComponent>
  </div>
</template>
<script>
import dialogComponent from './DialogComponent.vue';

export default {
  components: {
    dialogComponent
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
    }
  },
  data() {
    return {
      actions: []
    };
  },
  created() {
    this.actions = this.allowedActions.map(action => {
      switch (action) {
        case 'View':
          return {to: '/view', color: 'primary', icon: 'mdi-eye', title: 'View'};
        case 'Edit':
          return {to: '/edit', color: 'primary', icon: 'mdi-pencil', title: 'Edit'};
        case 'Delete':
          return {to: '/delete', color: 'error', icon: 'mdi-delete', title: 'Delete'};
        default:
          return {to: '/view', color: 'primary', icon: 'mdi-eye', title: 'View'};
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