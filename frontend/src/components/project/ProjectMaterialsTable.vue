<template>
  <v-card>
    <v-data-table
        :headers="headers"
        :items="projectProducts"
        :items-per-page-options="[5, 10]"
        :sort-by="['name']"
        class="elevation-1"/>
  </v-card>
</template>

<script>
export default {
  name: "ProjectMaterialsTable",
  inject: ['projectsService'],
  props: {
    item: {
      type: Object,
      required: true
    }
  },
  data() {
    return {
      projectProducts: [],
      headers: [
        {title: "Name", value: "productName"},
        {title: "Quantity", value: "quantity"},
        {title: "Warehouse", value: "warehouseName"}
      ]
    }
  },
  async mounted() {
    await this.fetchProjectProducts(this.item.id);
  },
  methods: {
    async fetchProjectProducts(projectId) {
      this.projectProducts = await this.projectsService.asyncFindProjectProducts(projectId);
    },
  },
}
</script>

<style scoped>

</style>