<template>
  <div class="container mt-3">
    <div class="d-flex justify-content-between mb-3">
      <input v-model="searchTerm" type="text" class="form-control w-25" placeholder="Search for project...">
      <button @click="sortProjectsByStatus" class="btn btn-success">Sort by Status</button>
    </div>

    <ul class="list-group project-list">
      <ProjectDetailComponent
          v-for="(project, index) in filteredProjects"
          :key="index"
          :project="project"
          v-model:editedProject="editedProject"
          @selectProject="selectProject(project)"
          @deselectProject="deselectProject"
          @deleteProject="deleteProject"
      />
    </ul>

    <button @click="addNewProject" class="btn btn-primary mt-3">Add New Project</button>
  </div>
</template>

<script>
import ProjectDetailComponent from "@/components/ProjectDetailComponent.vue";
import { Project } from "@/models/project.js";

export default {
  name: "ProjectListComponent",
  components: { ProjectDetailComponent },
  data() {
    return {
      projects: Array.from({ length: 5 }, (_, i) => Project.createDummyProject(i + 1)),
      selectedProject: {},
      editedProject: {},
      searchTerm: "",
      sortBy: "status",
    };
  },
  methods: {
    selectProject(project) {
      this.editedProject = project;
    },
    deselectProject() {
      this.editedProject = null;
    },

    saveChanges(editedProject) {
      const index = this.projects.findIndex(p => p === this.selectedProject);
      if (index !== -1) {
        this.$set(this.projects, index, editedProject);
      }
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


  },
  computed: {
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
