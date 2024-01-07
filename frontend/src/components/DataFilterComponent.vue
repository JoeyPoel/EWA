<template>
  <v-card>
    <v-container>
      <v-row>
        <v-col cols="12" sm="10">
            <v-row>
              <v-col v-if="canSearch" cols="12" :sm="columWidth">
                <v-text-field v-model="searchValue" label="Search" prepend-inner-icon="$search" variant="outlined"/>
              </v-col>
              <v-col v-if="canSortByWarehouse" cols="12" :sm="columWidth">
                <v-select v-model="selectedWarehouse" :items="warehouses" item-title="name" item-value="id"
                          label="Warehouse"
                          prepend-inner-icon="$warehouse" variant="outlined"/>
              </v-col>
              <v-col v-if="canSortByTeam" cols="12" :sm="columWidth">
                <v-select v-model="selectedTeam" :items="teams" item-title="name" item-value="id" label="Team"
                          prepend-inner-icon="$team" variant="outlined"/>
              </v-col>
            </v-row>
        </v-col>
        <v-col cols="12" sm="2" class="align-self-sm-start mt-md-1">
          <v-btn :disabled="!searchValue && !selectedWarehouse && !selectedTeam" color="secondary" icon="$restore"
                 rounded="sm" @click="reset" :block="true"/>
        </v-col>
      </v-row>
    </v-container>
  </v-card>
</template>
<script> export default {
  name: 'dataFilter',
  inject: ['warehousesService', 'teamsService'],
  props: {
    warehouse: {
      required: false,
    },
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
      this.teams.unshift({id: null, name: 'All teams'});
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
      this.$emit('team', this.selectedTeam);
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