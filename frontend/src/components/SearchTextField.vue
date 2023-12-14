<template>
  <v-row>
    <v-col>
      <v-text-field v-model="searchValue" label="Search" prepend-inner-icon="$search" variant="outlined"/>
    </v-col>
    <v-col>
      <v-select v-model="selectedWarehouse" :items="warehouses" item-title="name" item-value="id" label="Warehouse"
                variant="outlined">
        <template #prepend-inner>
          <v-icon color="grey"> $warehouse</v-icon>
        </template>
        <template v-slot:prepend-item>
          <v-list-item title="All warehouses" @click="selectedWarehouse = null;">
          </v-list-item>
        </template>
      </v-select>
    </v-col>
  </v-row>
</template>
<script> export default {
  name: 'SearchTextField',
  // props: ['warehouses', 'search', 'warehouseId'],
  props: {
    warehouses: {
      type: Array,
      required: true,
    },
    search: {
      required: true,
    },
    warehouseId: {
      required: true,
    },
  },
data() {
    return {
      selectedWarehouse: null,
      searchValue: null,
    }
  },
  watch: {
    warehouses(val){
      if (val){
        console.log(val);
        return true;
      }
      return null;
    },
    search() {
      this.$emit('input', this.searchValue);
    },
    selectedWarehouse() {
      this.$emit('warehouse', this.selectedWarehouse);
    }
  },
} </script>