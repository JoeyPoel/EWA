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
              <div class="badge-column">
                <span class="badge badge-fixed-position position-md-static bg-info">{{
                    getStatusDisplayName(project.status)
                  }}</span>
<!--              <span class="badge badge-fixed-position position-md-static bg-info">{{ project.status }}</span>-->
            </div>
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
              {{ getStatusDisplayName(selectedProject.status)}}
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
      projects: Array.from({length: 5}, (_, i) => Project.createDummyProject(i + 1)),
      selectedProject: {},
      searchTerm: "",
      sortBy: "status",
    };
  },
  methods: {
    selectProject(project) {
      this.selectedProject = project;
    },

    deselectProject() {
      this.selectedProject = {};
    },

    // TODO Remove double code -  available in ProjectListComponent.vue

    sortProjectsByStatus() {
      const statusOrder = [
        "in_progress",
        "completed",
        "on_hold",
        "cancelled"
      ];

      this.projects.sort((a, b) => {
        const indexA = statusOrder.indexOf(a.status);
        const indexB = statusOrder.indexOf(b.status);

        return indexA - indexB;
      });
    },

    getStatusDisplayName(status) {
      return Project.statusList.find(s => s.value === status)?.displayName;
    },

    // Methods for the modal
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

.badge-column {
  position: absolute;
  bottom: 50px;
  right: 50px;
}
</style>
