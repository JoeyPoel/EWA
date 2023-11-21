<template>
  <div class="container mt-3">
    <div class="d-flex justify-content-between mb-3">
      <input v-model="searchTerm" class="form-control w-25" placeholder="Search for project..." type="text">
      <button class="btn btn-success" @click="sortProjectsByStatus">Sort by Status</button>
    </div>

    <ul class="list-group project-list">
      <li
          v-for="(project, index) in filteredProjects"
          :key="index"
          class="list-group-item d-flex justify-content-between align-items-start"
          data-bs-target="#projectModal"
          data-bs-toggle="modal"
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
              </div>
            </div>
          </div>
          <p>{{ project.team.name }}</p>
        </div>
      </li>
    </ul>

    <div class="pt-3">
      <button class="btn btn-primary mb-3" data-bs-target="#projectModal " data-bs-toggle="modal" type="button"
              @click="this.isAddingNewProject = true">
        New Project
      </button>
    </div>

    <div id="projectModal" aria-hidden="true" aria-labelledby="projectModalLabel" class="modal fade" tabindex="-1">
      <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
        <div class="modal-content">
          <div class="modal-header bg-success text-white">
            <h5 class="modal-title">
              {{ originalProject && originalProject.name ? originalProject.name : 'Untitled Project' }}</h5>
            <button aria-label="Close" class="btn-close btn-close-white" data-bs-dismiss="modal" type="button"
                    @click="deselectProject"></button>
          </div>
          <div class="modal-body">
            <div class="mb-3">
              <h6 class="text-success">Name:</h6>
              <input id="editName" v-model="editedProject.name" class="form-control" type="text">
              <div v-if="!isValidProjectName" class="text-danger mt-1">Project name is required</div>
            </div>
            <div class="mb-3">
              <h6 class="text-success">Status:</h6>

              <select id="editStatus" v-model="editedProject.status" class="form-select">
                <option v-for="statusOption in Project.statusList" :key="statusOption.value"
                        :value="statusOption.value">
                  {{ statusOption.displayName }}
                </option>
              </select>
              <div v-if="!isValidProjectStatus" class="text-danger mt-1">Project status is required</div>
            </div>
            <div class="mb-3">
              <h6 class="text-success">Assigned Teams:</h6>
              <select id="editTeams" v-model="editedProject.team" class="form-select">
                <option v-for="team in teams" :key="team" :value="team">
                  {{ team.name }}
                </option>
              </select>
              <div v-if="!isValidProjectTeam" class="text-danger mt-1">Project team is required</div>
            </div>
            <div class="mb-3">
              <h6 class="text-success">Description:</h6>
              <textarea id="editDescription" v-model="editedProject.description" class="form-control"></textarea>
            </div>
            <div v-if="!isValidProjectDescription" class="text-danger mt-1">Project description is required</div>
          </div>
          <div class="modal-footer d-flex justify-content-start">
            <button :disabled="!isValidProject()" class="btn btn-primary" data-bs-dismiss="modal" type="button"
                    @click="saveProject">Save
            </button>
            <button aria-label="Delete" class="btn btn-danger" data-bs-dismiss="modal" type="button"
                    @click="deleteProject">Delete
            </button>
            <!--            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal" @click="deselectProject">Close</button>-->
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {Project} from "@/models/Project.js";

export default {
  name: "ProjectListComponent",
  inject: ['projectsService', 'teamsService'],
  data() {
    return {
      projects: [],
      teams: [],
      selectedProject: {},
      editedProject: {},
      originalProject: {},
      searchTerm: "",
      sortBy: "status",
      isAddingNewProject: false,
    };
  },
  async mounted() {
    await this.fetchProjects();
    this.teams = await this.teamsService.asyncFindAll();
    this.sortProjectsByStatus();
  },
  methods: {
    selectProject(project) {
      this.originalProject = project;
      this.editedProject = {...project};
      this.selectedProject = {...project};
    },
    async fetchProjects() {
      this.projects = await this.projectsService.asyncFindAll();
    },
    deselectProject() {
      this.editedProject = {};
      this.originalProject = {};
      this.isAddingNewProject = false;
    },

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

    async deleteProject() {
      const confirmDelete = window.confirm("Are you sure you want to delete this project?");
      if (confirmDelete) {
        const deletedProject = await this.projectsService.asyncDeleteProjectById(this.originalProject.id);
        if (deletedProject) {
          this.deselectProject();
          await this.fetchProjects();
        }
      }
    },

    getStatusDisplayName(status) {
      return Project.statusList.find(s => s.value === status)?.displayName;
    },

    saveProject() {
      if (!this.isValidProject()) return;
      if (this.isAddingNewProject) {
        this.addNewProject();
      } else {
        this.editExistingProject();
      }
    },

    isValidProject() {
      return (
          this.isValidProjectName() &&
          this.isValidProjectStatus() &&
          // this.isValidProjectTeam() &&
          this.isValidProjectDescription()
      );
    },

    isValidProjectName() {
      // Implement your project name validation logic here
      return !!this.editedProject.name;
    },

    isValidProjectStatus() {
      // Implement your project status validation logic here
      return !!this.editedProject.status;
    },

    isValidProjectTeam() {
      // Implement your project team validation logic here
      return !!this.editedProject.team;
    },

    isValidProjectDescription() {
      // Implement your project description validation logic here
      return !!this.editedProject.description;
    },

    async editExistingProject() {
      await this.projectsService.asyncUpdateProject(this.editedProject.id, this.editedProject);
      this.deselectProject()
      await this.fetchProjects();
      console.log("Saved changes to project", this.editedProject);
    },

    async addNewProject() {
      if (!this.isValidProject()) {
        return;
      }
      await this.projectsService.asyncAddProject(this.editedProject);

      this.deselectProject();
      await this.fetchProjects();

      console.log("Added new project", this.editedProject);
    },

    findTeamIdByName(teamName) {
      const team = this.teams.find(t => t.name === teamName);
      return team ? team.id : null;
    }
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

.badge-column {
  position: absolute;
  bottom: 50px;
  right: 50px;
}

</style>
