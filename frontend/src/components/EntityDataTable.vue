<template>
  <v-container :fluid="true">
    <v-card class="mt-1 font-weight-bold">
      <v-card-title class="bg-secondary text-center"><h3 class="fs-1">{{ title }}</h3></v-card-title>
      <DataTable :dialog-config="dialog" :table-config="table"
                 @action="handleDialogAction(true, $event.action, $event.item)"
                 @delete="handleDelete" @save="handleSave">
        <template v-for="customValue in table.customValues" v-slot:[`${customValue.value}`]>
          <slot :name="customValue.value"/>
        </template>
        <template v-slot:filter>
          <data-filter :can-search="filter.canSearch" :can-sort-by-team="filter.canSortByTeam"
                       :can-sort-by-warehouse="filter.canSortByWarehouse" @filterChange="table.searchTerm = $event"
                       @handleSelectedTeam="handleSelectedTeam" @warehouseChange="handleSelectedWarehouse"/>
        </template>
      </DataTable>
    </v-card>
  </v-container>
</template>

<script>
import dataFilter from "@/components/DataFilterComponent.vue";
import DataTable from "@/components/base/DataTable.vue";

export default {
  name: "EntityDataTable",
  components: {DataTable, dataFilter},
  props: {
    title: {
      type: String,
      required: true
    },
    dialogConfig: {
      type: Object,
      required: true
    },
    tableConfig: {
      type: Object,
      required: true
    },
    filterConfig: {
      type: Object,
      required: true
    },
  },
  computed: {
    table: {
      get() {
        return this.tableConfig;
      },
      set(value) {
        this.$emit('update-tableConfig', value);
      }
    },
    dialog: {
      get() {
        return this.dialogConfig;
      },
      set(value) {
        this.$emit('update-dialogConfig', value);
      }
    },
    filter: {
      get() {
        return this.filterConfig;
      },
      set(value) {
        this.$emit('update-filterConfig', value);
      }
    }
  },
  methods: {
    handleDialogAction(open, action, item) {
      this.dialog.title = action.action;
      this.dialog.item = item;
      this.dialog.open = open;
    },
    handleDelete(item) {
      this.$emit('delete', item);
    },
    handleSave(item) {
      this.$emit('save', item);
    },
    handleSelectedTeam(team) {
      this.$emit('team-change', team);
    },
    handleSelectedWarehouse(warehouse) {
      this.$emit('warehouse-change', warehouse);
    },
  }
}
</script>

<style scoped>

</style>