<template>
  <v-container fluid>
    <base-card class="mt-1" color="secondary" title="Projects">
        <v-text-field v-model="search" label="Search Project" prepend-inner-icon="$search" variant="outlined">
        </v-text-field>
        <v-spacer></v-spacer>
      <v-data-table
        v-model:items-per-page="itemsPerPage"
        :headers="headers"
        :items="projects"
        :search="search"
        class="elevation-1"
        item-value="id">
        <template v-slot:top>
          <v-toolbar flat>
            <v-dialog v-model="dialogDetail" max-width="800px">
              <v-card>
                <v-tabs v-model="tab" bg-color="transparent">
                  <v-tab value="details">Project Detail</v-tab>
                  <v-tab value="materials">Project Materials</v-tab>
                  <v-tab value="tasks">Project Tasks</v-tab>
                </v-tabs>
                <v-card-text>
                  <v-window v-model="tab">
                    <v-window-item value="details">
                      <v-row>
                        <v-col>
                          <v-list>
                            <v-list-item>
                              <v-list-item-title>Name</v-list-item-title>
                              <v-list-item-subtitle>{{ selectedProject.name }}</v-list-item-subtitle>
                            </v-list-item>
                            <v-list-item>
                              <v-list-item-title>Start Date</v-list-item-title>
                              <v-list-item-subtitle>{{ selectedProject.startDate }}</v-list-item-subtitle>
                            </v-list-item>
                            <v-list-item>
                              <v-list-item-title>End Date</v-list-item-title>
                              <v-list-item-subtitle>{{ selectedProject.endDate }}</v-list-item-subtitle>
                            </v-list-item>
                            <v-list-item>
                              <v-list-item-title>Status</v-list-item-title>
                              <v-list-item-subtitle>{{ getStatusDisplayName(selectedProject.status) }}</v-list-item-subtitle>
                            </v-list-item>
                            <v-list-item>
                              <v-list-item-title>Team</v-list-item-title>
                              <v-list-item-subtitle>{{ teams.find(t => t.id === selectedProject.teamId)?.name }}</v-list-item-subtitle>
                            </v-list-item>
                            <v-list-item>
                              <v-list-item-title>Description</v-list-item-title>
                              <v-list-item-subtitle>{{ selectedProject.description }}</v-list-item-subtitle>
                            </v-list-item>
                          </v-list>
                        </v-col>
                      </v-row>
                    </v-window-item>
                    <v-window-item value="materials">
                      <v-container>
                        <v-data-table
                            :headers="projectProductHeaders"
                            :items="projectProducts"
                            :items-per-page-options="[5, 10]"
                            :search="projectProductSearch"
                            :sort-by="['name']"
                            class="elevation-1">
                        </v-data-table>
                      </v-container>
                    </v-window-item>
                    <v-window-item value="tasks">
                      <v-container>
                        <v-data-table
                            :headers="projectTaskHeaders"
                            :items="projectTasks"
                            :items-per-page-options="[5, 10]"
                            :search="projectTaskSearch"
                            :sort-by="['order']"
                            class="elevation-1">
                          <template v-slot:[`item.status`]="{ item }">
                            <v-chip :color="getStatusColor(item)">
                              {{ getTaskStatusDisplayName(item.status) }}
                            </v-chip>
                          </template>
                        </v-data-table>
                      </v-container>
                    </v-window-item>
                  </v-window>
                </v-card-text>
              </v-card>
            </v-dialog>
          </v-toolbar>
        </template>
        <template v-slot:[`item.status`]="{ item }">
          <v-chip :color="getStatusColor(item)" :text="getStatusDisplayName(item.status)">
          </v-chip>
        </template>
        <template v-slot:[`item.teamName`]="{ item }">
          {{ this.teams.find(t => t.id === item.teamId)?.name }}
        </template>
        <template v-slot:[`item.actions`]="{ item }">
          <v-icon @click="seeDetails(item)">$info</v-icon>
        </template>
      </v-data-table>
    </base-card>
  </v-container>

</template>

<script>

import {Project} from "@/models/Project.js";
import BaseCard from "@/components/base/BaseCard.vue";
import {jwtDecode} from "jwt-decode";
import {Task} from "@/models/Task";
export default {
  name: "ProjectsComponent.vue",
  computed: {
    Project() {
      return Project
    }
  },
  components: {BaseCard},
  inject: ["projectsService", "teamsService","usersService"],
  data() {
    return {
      headers: [
        {title: "Name", key: "name"},
        {title: "Team", key: "teamName"},
        {title: 'Start Date', key: 'startDate'},
        {title: 'End Date', key: 'endDate'},
        {title:  "Status", key: "status"},
        {title: "Description", key: "description"},
        {title: "Actions", key: "actions", sortable: false}],
      projectProductHeaders: [
        {title: "Name", value: "productName"},
        {title: "Quantity", value: "quantity"},
       // {title: "Warehouse", value: "warehouseName"},
        ],
      projectTaskHeaders: [
        {title: "Name", value: "name"},
        {title: "Description", value: "description"},
        {title: "Deadline", value: "deadline"},
        {title: "Status", value: "status"},
        {title: "Assigned To", value: "personalTodoListOwnerName"},
      ],
      projectTaskSearch: "",
      projectProductSearch: "",
      projectProducts: [],
      projectTasks: [],
      projects: [],
      teams: [],
      users: [],
      selectedProject: new Project(),
      search: "",
      tab: "",
      itemsPerPage: 10,
      userId: "",
      dialogDetail: false,
      dialogEdit: false,
    }
  },

  async mounted() {
    await this.initialize();
  },

  watch: {
    async dialogDetail(val) {
      if (!val) {
        this.close();
        return;
      }
      await this.loadProjectData();

    },
  },

  methods: {
    async initialize() {
      await this.getTeams();
      await this.getUserIdFromToken();
      await this.getProjects();
    },

    async loadProjectData() {
      await this.loadInventory();
      await this.loadTasks();
    },

    async getTeams() {
      this.teams = await this.teamsService.asyncGetAll();
      console.log(this.teams);
    },

    async getUserIdFromToken() {
      const isAuthenticated = localStorage.getItem("token");
      if (isAuthenticated) {
        const decodedToken = jwtDecode(isAuthenticated);
        this.userId = decodedToken.id;
      }
    },

    async getProjects() {
      try {
        const userTeams = this.teams.filter(team => team.membersIds.includes(this.userId));

        const allProjects = await this.projectsService.asyncGetAll();
        this.projects = allProjects.filter(project => {
          return userTeams.some(team => team.id === project.teamId);
        });

        console.log(this.projects);
      } catch (error) {
        console.error("Error fetching projects:", error);
      }
    },

    async loadInventory() {
      try {
        this.projectProducts = await this.projectsService.asyncGetProjectProducts(this.selectedProject.id);
        console.log(this.projectProducts);
      } catch (error) {
        console.error("Error fetching project products:", error);
      }
    },

    async loadTasks() {
      try {
        this.projectTasks = await this.projectsService.asyncGetProjectTasks(this.selectedProject.id);
        console.log(this.projectTasks);
      } catch (error) {
        console.error("Error fetching project tasks:", error);
      }
    },

    getStatusColor(project) {
      switch (project.status) {
        case "IN_PROGRESS":
          return "blue";
        case "FINISHED" || "DONE":
          return "green";
        case "TODO":
          return "red";
        default:
          return "grey";
      }
    },

    getStatusDisplayName(status) {
      return Project.statusList.find(s => s.value === status)?.displayName;
    },

    getTaskStatusDisplayName(status) {
      console.log(status);
      console.log(Task.statusList.find(s => s.value === status)?.displayName);
      return Task.statusList.find(s => s.value === status)?.displayName;
    },

    assignSelectedProject(project) {
      this.selectedProject = Object.assign(new Project(), project);
    },

    seeDetails(project) {
      this.assignSelectedProject(project);
      this.dialogDetail = true;
    },

    close() {
      this.assignSelectedProject(new Project())
      this.dialogDetail = false;
      this.dialogEdit = false;
    },
  },
}
</script>

<style scoped>
</style>