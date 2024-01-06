<template>
  <v-container>
    <v-card>
      <v-card-text>
        <v-data-table
            :headers="projectHeaders"
            :items="projects"
            class="elevation-1"
            item-value="name">
          <template v-slot:[`item.warehouseName`]="{ item }">
            {{ getWarehouseName(item) }}
          </template>
        </v-data-table>
      </v-card-text>
    </v-card>
  </v-container>
</template>

<script>
export default {
  name: "TeamProjectsTable",
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
        {text: 'Name', value: 'name'},
        {text: 'Warehouse', value: 'warehouseName'},
        {text: 'Actions', value: 'actions', sortable: false},
      ],
      projects: null,
      warehouses: null
    }
  },
  async mounted() {
    this.projects = await this.projectsService.asyncFindAllByTeamId(this.team.id);
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