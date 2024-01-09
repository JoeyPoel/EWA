<template>
  <v-container :fluid="true">
    <order-entity-table/>
  </v-container>
</template>

<script>
import OrderEntityTable from "@/components/order/OrderEntityTable.vue";

export default {
  name: "OrderViewAdmin",
  components: {OrderEntityTable}
}
</script>