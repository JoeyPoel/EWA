<template>
  <v-container :fluid="true">
    <team-entity-table/>
  </v-container>
</template>

<script>
import TeamEntityTable from "@/components/team/TeamEntityTable.vue";

export default {
  name: "TeamViewAdmin",
  components: {TeamEntityTable},
}
</script>
