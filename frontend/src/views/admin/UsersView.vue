<template>
  <v-container :fluid="true">
    <user-entity-table/>
  </v-container>
</template>

<script>
import UserEntityTable from "@/components/user/UserEntityTable.vue";

export default {
  name: "UserViewAdmin",
  components: {UserEntityTable}
}
</script>
