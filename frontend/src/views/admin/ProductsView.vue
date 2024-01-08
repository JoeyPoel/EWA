<template>
  <v-container :fluid="true">
    <product-entity-table/>
  </v-container>
</template>

<script>
import ProductEntityTable from "@/components/product/ProductEntityTable.vue";

export default {
  name: "ProductViewAdmin",
  components: {ProductEntityTable}
}
</script>