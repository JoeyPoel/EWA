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
              <span class="badge bg-info">{{ getStatusDisplayName(project.status) }}</span>
            </div>
          </div>
          <p>{{ project.team }}</p>
        </div>
      </li>
    </ul>

    <div class="pt-3">
      <button type="button" class="btn btn-primary mb-3" data-bs-toggle="modal" data-bs-target="#projectModal"
                 @click="addNewProject">New Project</button>
    </div>

    <div class="modal fade" id="projectModal" tabindex="-1" aria-labelledby="projectModalLabel" aria-hidden="true">
      <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
        <div class="modal-content">
          <div class="modal-header bg-success text-white">
            <h5 class="modal-title">{{ originalProject && originalProject.name ? originalProject.name : 'Untitled Project' }}</h5>
            <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal" aria-label="Close" @click="deselectProject"></button>
          </div>
          <div class="modal-body">
            <div class="mb-3">
              <h6 class="text-success">Name:</h6>
              <input type="text" id="editName" v-model="editedProject.name" class="form-control">
            </div>
            <div class="mb-3">
              <h6 class="text-success">Status:</h6>
              <select id="editStatus" v-model="editedProject.status" class="form-select">
                <option v-for="statusOption in Project.statusList" :key="statusOption.value" :value="statusOption.value">
                  {{ statusOption.displayName }}
                </option>
              </select>
            </div>
            <div class="mb-3">
              <h6 class="text-success">Assigned Teams:</h6>
              <input type="text" id="editTeams" v-model="editedProject.team" class="form-control">
            </div>
            <div class="mb-3">
              <h6 class="text-success">Description:</h6>
              <textarea id="editDescription" v-model="editedProject.description" class="form-control"></textarea>
            </div>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal" @click="deselectProject">Close</button>
            <button type="button" class="btn btn-primary" data-bs-dismiss="modal" @click="saveProject">Save</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { Project } from "@/models/project.js";

export default {
  name: "ProjectListComponent",
  data() {
    return {
      projects: Array.from({ length: 5 }, (_, i) => Project.createDummyProject(i + 1)),
      selectedProject: {},
      editedProject: {},
      originalProject: {},
      searchTerm: "",
      sortBy: "status",
      isAddingNewProject: false,
    };
  },
  methods: {
    selectProject(project) {
      this.originalProject = project;
      this.editedProject = { ...project };
      this.selectedProject = { ...project };
    },

    deselectProject() {
      this.editedProject = {};
    },

    sortProjectsByStatus() {
      this.projects.sort((a, b) => a.status.localeCompare(b.status));
    },

    deleteProject(project) {
      const index = this.projects.findIndex(p => p === project);
      if (index !== -1) {
        this.projects.splice(index, 1);
      }
    },

    getStatusDisplayName(status) {
      return Project.statusList.find(s => s.value === status)?.displayName;
    },

    saveProject() {
      if (this.isAddingNewProject) {
        this.addNewProject();
      } else {
        this.editExistingProject();
      }
    },

    editExistingProject() {
      const index = this.projects.findIndex(p => p === this.originalProject);
      if (index !== -1) {
        this.projects.splice(index, 1, this.editedProject);
      }
      console.log("Saved changes to project", this.editedProject);

      this.originalProject = this.editedProject;
    },

    addNewProject() {
      const newProject = {
        id: this.projects.length + 1,
        status: "",
        team: "",
        description: "",
        name: "",
      };

      this.projects.push(newProject);
      this.editedProject = newProject;
    },
  },

  computed: {
    Project() {
      return Project
    },
    filteredProjects() {
      if (!this.searchTerm) return this.projects;

      return this.projects.filter((project) => {
        return project.name?.toLowerCase().includes(this.searchTerm.toLowerCase());
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
