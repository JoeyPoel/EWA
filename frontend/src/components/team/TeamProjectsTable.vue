<template>
  <v-container>
    <v-card>
      <v-data-table
          :headers="projectHeaders"
          :items="projects"
          class="elevation-3"
          item-value="name">
        <template v-slot:[`item.warehouseName`]="{ item }">
          {{ getWarehouseName(item) }}
        </template>
      </v-data-table>
    </v-card>
  </v-container>
</template>

<script>
export default {
  name: 'TeamProjectsTable',
  inject: ['projectsService', 'warehousesService'],
  props: {
    item: {
      type: Object,
      required: true
    },
  },
  data() {
    return {
      projectHeaders: [
        {title: 'ID', value: 'id'},
        {title: 'Location', value: 'location'},
        {title: 'Start Date', value: 'startDate'},
        {title: 'End Date', value: 'endDate'},
        {title: 'Status', value: 'status'},
      ],
      projects: [],
      warehouses: []
    }
  },
  async mounted() {
    console.log(`TeamProjectsTable: item changed to ${this.item}`);
    this.projects = await this.projectsService.asyncFindAllByTeamId(this.item.id);
    this.warehouses = await this.warehousesService.asyncFindAll();
  },
  methods: {
    getWarehouseName(team) {
      if (team.warehouseId && this.warehouses.length > 0) {
        return this.warehouses.find(warehouse => warehouse.id === team.warehouseId).name;
      }
      return '';
    },
  }
}
</script>

<style scoped>

</style>