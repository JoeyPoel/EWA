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
              </div>
            </div>
          </div>
          <p>{{ project.team }}</p>
        </div>
      </li>
    </ul>

    <div class="pt-3">
      <button type="button" class="btn btn-primary mb-3" data-bs-toggle="modal" data-bs-target="#projectModal "
              @click="this.isAddingNewProject = true">
        New Project
      </button>
    </div>

    <div class="modal fade" id="projectModal" tabindex="-1" aria-labelledby="projectModalLabel" aria-hidden="true">
      <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
        <div class="modal-content">
          <div class="modal-header bg-success text-white">
            <h5 class="modal-title">
              {{ originalProject && originalProject.name ? originalProject.name : 'Untitled Project' }}</h5>
            <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal" aria-label="Close"
                    @click="deselectProject"></button>
          </div>
          <div class="modal-body">
            <div class="mb-3">
              <h6 class="text-success">Name:</h6>
              <input type="text" id="editName" v-model="editedProject.name" class="form-control">
            </div>
            <div class="mb-3">
              <h6 class="text-success">Status:</h6>
              <select id="editStatus" v-model="editedProject.status" class="form-select">
                <option v-for="statusOption in Project.statusList" :key="statusOption.value"
                        :value="statusOption.value">
                  {{ statusOption.displayName }}
                </option>
              </select>
            </div>
            <div class="mb-3">
              <h6 class="text-success">Assigned Teams:</h6>
              <select id="editTeams" v-model="editedProject.team" class="form-select">
                <option v-for="team in teamNames" :key="team" :value="team">
                  {{ team }}
                </option>
              </select>
            </div>
            <div class="mb-3">
              <h6 class="text-success">Description:</h6>
              <textarea id="editDescription" v-model="editedProject.description" class="form-control"></textarea>
            </div>
          </div>
          <div class="modal-footer d-flex justify-content-start">
            <button type="button" class="btn btn-primary" data-bs-dismiss="modal" @click="saveProject">Save</button>
            <button type="button" class="btn btn-danger" aria-label="Delete" data-bs-dismiss="modal"
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
import {Project} from "@/models/project.js";

export default {
  name: "ProjectListComponent",
  inject: ['projectService'],
  async created() {
    const projectsData = await this.projectService.asyncFindAll();
    this.projects = projectsData.map(project => new Project(
        project.id,
        project.status,
        project.team.name,
        project.description,
        project.name
    ));

    console.log("Projects before:", this.projects);
    const uniqueTeamNames = Array.from(new Set(this.projects.map(project => project.team)));
    this.teamNames = uniqueTeamNames;
    console.log("Team names:", this.teamNames);


    this.editedProject = {
      id: 0,
      status: Project.statusList[0].value,
      team: this.teamNames[0],
      description: "",
      name: ""
    };

  },
  data() {
    return {
      //projects: Array.from({length: 5}, (_, i) => Project.createDummyProject(i + 1)),
      projects: [],
      selectedProject: {},
      editedProject: {},
      originalProject: {},
      teamNames: [],
      searchTerm: "",
      sortBy: "status",
      isAddingNewProject: false,
    };
  },
  methods: {

    async fetchProjects() {
      const projectsData = await this.projectService.asyncFindAll();
      this.projects = projectsData.map(project => new Project(
          project.id,
          project.status,
          project.team.name,
          project.description,
          project.name
      ));
    },

    selectProject(project) {
      this.originalProject = project;
      this.editedProject = {...project};
      this.selectedProject = {...project};
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


    /*deleteProject() {
      const confirmDelete = window.confirm("Are you sure you want to delete this project?");
      if (confirmDelete) {
        const index = this.projects.findIndex(p => p === this.originalProject);
        if (index !== -1) {
          this.projects.splice(index, 1);
        }
        this.deselectProject()
      }
    },
*/

    async deleteProject() {
      const confirmDelete = window.confirm("Are you sure you want to delete this project?");
      if (confirmDelete) {
        await this.projectService.asyncDeleteById(this.originalProject.id);
        await this.fetchProjects();
        this.deselectProject();
      }
    },

    getStatusDisplayName(status) {
      return Project.statusList.find(s => s.value === status)?.displayName;
    },

    saveProject() {
      if (!this.validateProject()) return;
      if (this.isAddingNewProject) {
        this.addNewProject();
      } else {
        this.editExistingProject();
      }
    },

    validateProject() {
      if (!this.editedProject.name) {
        alert("Project name is required");
        return false;
      }

      if (!this.editedProject.status) {
        alert("Project status is required");
        return false;
      }

      if (!this.editedProject.team) {
        alert("Project team is required");
        return false;
      }

      if (!this.editedProject.description) {
        alert("Project description is required");
        return false;
      }
      return true;
    },

    async editExistingProject() {
      /*const index = this.projects.findIndex(p => p === this.originalProject);
      if (index !== -1) {
        this.projects.splice(index, 1, this.editedProject);
      }
      this.originalProject = this.editedProject;*/
      await this.projectService.asyncAdd(this.editedProject);
      this.deselectProject()
      await this.fetchProjects();
      console.log("Saved changes to project", this.editedProject);
    },

    async addNewProject() {
      try {
        if (!this.validateProject()) {
          return;
        }

        const editedProjectCopy = { ...this.editedProject };

        await this.projectService.asyncAdd(editedProjectCopy);

        this.deselectProject();
        await this.fetchProjects();

        console.log("Added new project", editedProjectCopy);
      } catch (error) {
        console.error("Error adding new project", error);
        // Handle errors as needed
      }
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

.badge-column {
  position: absolute;
  bottom: 50px;
  right: 50px;
}

</style>
