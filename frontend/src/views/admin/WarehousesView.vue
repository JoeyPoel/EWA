<template>
  <v-container :fluid="true">
    <warehouse-entity-table/>
  </v-container>
</template>

<script>
import WarehouseEntityTable from "@/components/warehouses/WarehouseEntityTable.vue";

export default {
  name: "WarehouseViewAdmin",
  components: {WarehouseEntityTable}
}
</script>
