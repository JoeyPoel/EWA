<template>
  <div class="container mt-3">
    <div class="d-flex justify-content-between mb-3">
      <input v-model="searchTerm" type="text" class="form-control w-25" placeholder="Search for project...">
      <button @click="sortProjectsByStatus" class="btn btn-success">Sort by Status</button>
    </div>

    <ul class="list-group project-list">
      <li
          class="list-group-item d-flex justify-content-between align-items-start"
          data-bs-toggle="modal"
          data-bs-target="#projectModal"
          v-for="(project, index) in filteredProjects"
          :key="index"
          @click="selectProject(project)">
        <div class="ms-2 me-auto">
          <div class="row">
            <div class="col">
              <h6 class="fw-bold">{{ project.name }}</h6>
            </div>
            <div class="col text-end">
              <span class="badge bg-info">{{ project.status }}</span>
            </div>
          </div>
          <p>{{ project.team }}</p>
        </div>
      </li>
    </ul>

    <div class="modal fade" id="projectModal" tabindex="-1" aria-labelledby="projectModalLabel" aria-hidden="true">
      <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
        <div class="modal-content">
          <div class="modal-header bg-success text-white">
            <h5 class="modal-title">{{ selectedProject.name }} </h5>
            <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal" aria-label="Close" @click="deselectProject"></button>
          </div>
          <div class="modal-body">
            <div class="mb-3">
              <h6 class="text-success">Status:</h6>
              {{ selectedProject.status }}
            </div>
            <div class="mb-3">
              <h6 class="text-success">Assigned Teams:</h6>
              {{ selectedProject.team }}
            </div>
            <div class="mb-3">
              <h6 class="text-success">Description:</h6>
              {{ selectedProject.description }}
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { Project } from "@/models/project.js";

export default {
  name: "ProjectComponent",
  data() {
    return {
      projects: [],
      selectedProject: {},
      searchTerm: "",
      sortBy: "status",
    };
  },
  created() {
    this.projects = [
      new Project("In Progress", "Solar Panel Installation Team A", "Residential Solar Panel Installation", "Project A"),
      new Project("Completed", "Solar Panel Installation Team B", "Commercial Solar Panel Installation", "Project B"),
      new Project("In Progress", "Solar Panel Installation Team C", "Solar Panel Retrofit for Industrial Facility", "Project C"),
      new Project("Completed", "Solar Panel Installation Team D", "Solar Panel Maintenance and Inspection", "Project D"),
      new Project("In Progress", "Solar Panel Installation Team E", "Community Solar Farm Setup", "Project E"),
    ];
  },
  methods: {
    selectProject(project) {
      this.selectedProject = project;
    },
    deselectProject() {
      this.selectedProject = {};
    },
    sortProjectsByStatus() {
      this.projects.sort((a, b) => {
        if (a.status < b.status) return -1;
        if (a.status > b.status) return 1;
        return 0;
      });
    },
  },
  computed: {
    filteredProjects() {
      if (!this.searchTerm) return this.projects;

      return this.projects.filter((project) => {
        return project.name.toLowerCase().includes(this.searchTerm.toLowerCase());
      });
    },
  },
};

</script>

<style scoped>
.modal {
  --bs-modal-header-border-color: transparent;
}

.modal-header {
  outline: none;
}

.list-group-item:hover {
  cursor: pointer;
  background-color: lightgreen;
}

.project-list {
  max-height: 90vh;
  overflow-y: auto;
}
</style>
