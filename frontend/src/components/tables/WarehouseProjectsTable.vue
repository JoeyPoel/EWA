<template>
  <v-container>
    <v-data-table
        :headers="warehouseProjectHeaders"
        :items="warehouseProjects"
        :sort-by="['startDate']"
        :items-per-page-options="detailTableItemsPerPageOptions"
        class="elevation-1">
      <template v-slot:[`item.status`]="{ item }">
        <v-chip :color="Project.getStatusColor(item)" :text="Project.getStatusDisplayName(item.status)"/>
      </template>
    </v-data-table>
  </v-container>
</template>

<script>
import {Project} from "@/models/Project";

export default {
  name: "WarehouseProjectsTable",
  inject: ['projectsService'],
  computed: {
    Project() {
      return Project
    }
  },
  props: {
    item: {
      type: Object,
      required: true
    },
  },
  data(){
    return {
      warehouseProjectHeaders: [
        {title: "Project Name", value: "name"},
        {title: "Location", value: "location"},
        {title: "Start Date", value: "startDate"},
        {title: "End Date", value: "endDate"},
        {title: "Status", value: "status", key: "status"},
      ],
      warehouseProjects: [],
      detailTableItemsPerPageOptions: [5, 10, 15],
    }
  },
  async mounted() {
    await this.loadWarehouseProjects();
  },
  methods:{
    async loadWarehouseProjects() {
      this.warehouseProjects = await this.projectsService.asyncFindAllByWarehouseId(this.item.id);
    },
  }
}
</script>