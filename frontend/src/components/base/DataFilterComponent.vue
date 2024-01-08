<template>
      <v-row :no-gutters="true">
        <v-col cols="10" sm="11">
            <v-row :no-gutters="true">
              <v-col v-if="canSearch" cols="12" :sm="columWidth" class="align-self-center px-sm-2">
                <v-text-field v-model="searchValue" label="Search" prepend-inner-icon="$search" variant="outlined"/>
              </v-col>
              <v-col v-if="canSortByWarehouse" cols="12" :sm="columWidth" class="align-self-center px-sm-2">
                <v-select v-model="selectedWarehouse" :items="warehouses" item-title="name" item-value="id"
                          label="Warehouse" prepend-inner-icon="$warehouse" variant="outlined"/>
              </v-col>
              <v-col v-if="canSortByTeam" cols="12" :sm="columWidth" class="align-self-center px-sm-2">
                <v-select v-model="selectedTeam" :items="teams" item-title="name" item-value="id" label="Team"
                          prepend-inner-icon="$team" variant="outlined"/>
              </v-col>
            </v-row>
        </v-col>
        <v-col cols="2" sm="1" class="p-1 mt-11 mt-sm-0">
          <v-row :no-gutters="true" justify="center">
              <v-btn :disabled="!searchValue && !selectedWarehouse && !selectedTeam" color="secondary" icon="$restore"
                     rounded="sm" variant="outlined" @click="reset"/>
          </v-row>
        </v-col>
      </v-row>
</template>
<script> export default {
  name: 'dataFilter',
  inject: ['warehousesService', 'teamsService'],
  props: {
    canSearch: {
      type: Boolean,
      default: false,
    },
    canSortByWarehouse: {
      type: Boolean,
      default: false,
    },
    canSortByTeam: {
      type: Boolean,
      default: false,
    },
  },
  data() {
    return {
      searchValue: null,
      warehouses: [],
      selectedWarehouse: null,
      teams: [],
      selectedTeam: null,
    }
  },
  async mounted() {
    if (this.canSortByWarehouse) {
      this.warehouses = await this.warehousesService.asyncFindAll();
      this.warehouses.unshift({id: null, name: 'All warehouses'});
    }
    if (this.canSortByTeam) {
      this.teams = await this.teamsService.asyncFindAll();
      this.teams.unshift({id: null, name: 'All team'});
    }
  },
  watch: {
    searchValue() {
      console.log("search")
      console.log(this.searchValue)
      this.$emit('filterChange', this.searchValue);
    },
    selectedWarehouse() {
      this.$emit('warehouseChange', this.selectedWarehouse);
    },
    selectedTeam() {
      this.$emit('teamChange', this.selectedTeam);
    }
  },
  computed: {
    columWidth() {
      const width = 12;
      let count = 0;
      if (this.canSearch) {
        count++;
      }
      if (this.canSortByWarehouse) {
        count++;
      }
      if (this.canSortByTeam) {
        count++;
      }
      return width / count;
    }
  },
  methods: {
    reset() {
      this.searchValue = null;
      this.selectedWarehouse = null;
      this.selectedTeam = null;
      this.$emit('input', this.searchValue);
      this.$emit('warehouse', this.selectedWarehouse);
      this.$emit('team', this.selectedTeam);
    }
  }
} </script>