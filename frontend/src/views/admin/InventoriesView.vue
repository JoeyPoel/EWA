<template>
  <v-container :fluid="true">
    <inventory-entity-table/>
  </v-container>
</template>

<script>
import InventoryEntityTable from "@/components/inventory/InventoryEntityTable.vue";

export default {
  name: "InventoryViewAdmin",
  components: {InventoryEntityTable},
}
</script>
