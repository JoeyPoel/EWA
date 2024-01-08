<template>
  <v-container :fluid="true">
    <project-entity-table/>
  </v-container>
</template>

<script>
import ProjectEntityTable from "@/components/project/ProjectEntityTable.vue";

export default {
  name: "ProjectAdminView",
  components: {ProjectEntityTable}
}
</script>
