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
          <p>{{ project.team.name }}</p>
        </div>
      </li>
    </ul>

    <div class="modal fade" id="projectModal" tabindex="-1" aria-labelledby="projectModalLabel" aria-hidden="true">
      <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
        <div v-if="selectedProject !== null" class="modal-content">
          <div class="modal-header bg-success text-white">
            <h5 class="modal-title">{{ selectedProject.name }} </h5>
            <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal" aria-label="Close"
                    @click="deselectProject"></button>
          </div>
          <div class="modal-body">
            <div class="mb-3">
              <h6 class="text-success">Status:</h6>
              {{ getStatusDisplayName(selectedProject.status) }}
            </div>
            <div class="mb-3">
              <h6 class="text-success">Assigned Team:</h6>
              {{ selectedProject.team.name }}
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
export default {
  name: "ProjectComponent",
  inject: ["projectsService"],
  data() {
    return {
      projects: [],
      selectedProject: null,
      searchTerm: "",
      sortBy: "status",
    };
  },
  async created() {
    this.projects = await this.projectsService.asyncGetAll();
    this.sortProjectsByStatus();
  },
  methods: {
    async selectProject(project) {
      this.selectedProject = project;
    },

    deselectProject() {
      this.selectedProject = null;
    },

    // TODO Remove double code -  available in ProjectListComponent.vue

    sortProjectsByStatus() {
      const statusOrder = [
        "PENDING",
        "CONFIRMED",
        "IN_PROGRESS",
        "FINISHED",
        "CANCELED"
      ];

      this.projects.sort((a, b) => {
        const indexA = statusOrder.indexOf(a.status);
        const indexB = statusOrder.indexOf(b.status);

        return indexA - indexB;
      });
    },

    getStatusDisplayName(status) {
      const statusDisplayNames = {
        PENDING: "Pending",
        CONFIRMED: "Confirmed",
        IN_PROGRESS: "In Progress",
        FINISHED: "Finished",
        CANCELED: "Canceled",
      }
      return statusDisplayNames[status];
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
