<template>
  <!--  <div class="container mt-3">-->
  <!--    <div class="d-flex justify-content-between mb-3">-->
  <!--      <div class="col-md-4">-->
  <!--        <input v-model="searchTerm" class="form-control" placeholder="Search for project..." type="text">-->
  <!--      </div>-->
  <!--      <div class="col-md-auto justify-content-end">-->
  <!--        <button class="btn btn-success m-2" @click="sortProjectsByStatus">Sort by Status</button>-->
  <!--        <button class="btn btn-primary m-2" data-bs-target="#projectModal" data-bs-toggle="modal" type="button"-->
  <!--                @click="this.isAddingNewProject = true">-->
  <!--          New Project-->
  <!--        </button>-->
  <!--      </div>-->
  <!--    </div>-->


  <!--    <ul class="list-group project-list">-->
  <!--      <li-->
  <!--          v-for="(project, index) in filteredProjects"-->
  <!--          :key="index"-->
  <!--          class="list-group-item d-flex justify-content-between align-items-start"-->
  <!--          data-bs-target="#projectModal"-->
  <!--          data-bs-toggle="modal"-->
  <!--          @click="selectProject(project)"-->
  <!--      >-->
  <!--        <div class="ms-2 me-auto">-->
  <!--          <div class="row">-->
  <!--            <div class="col">-->
  <!--              <h6 class="fw-bold">{{ project.name }}</h6>-->
  <!--            </div>-->
  <!--            <div class="row">-->
  <!--              <p>Team: {{ project.team.name }}</p>-->
  <!--            </div>-->
  <!--            <div class="col text-end">-->
  <!--              <div class="badge-column">-->
  <!--                        <span class="badge badge-fixed-position position-md-static bg-info">{{-->
  <!--                            getStatusDisplayName(project.status)-->
  <!--                          }}</span>-->
  <!--              </div>-->
  <!--            </div>-->
  <!--          </div>-->
  <!--        </div>-->
  <!--      </li>-->
  <!--    </ul>-->


  <!--    <div class="pt-3 fixed-bottom">-->
  <!--      <button class="btn  btn-primary mb-3" data-bs-target="#projectModal " data-bs-toggle="modal" type="button"-->
  <!--              @click="this.isAddingNewProject = true">-->
  <!--        New Project-->
  <!--      </button>-->
  <!--    </div>-->

  <!--    <div id="projectModal" aria-hidden="true" aria-labelledby="projectModalLabel" class="modal fade" tabindex="-1">-->
  <!--      <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">-->
  <!--        <div class="modal-content">-->
  <!--          <div class="modal-header bg-white text-white">-->
  <!--            <h5 class="modal-title text-dark">-->
  <!--              {{ originalProject && originalProject.name ? originalProject.name : 'Untitled Project' }}</h5>-->
  <!--            <button aria-label="Close" class="btn-close btn-close-dark" data-bs-dismiss="modal" type="button"-->
  <!--                    @click="deselectProject"></button>-->
  <!--          </div>-->

  <!--          <div class="modal-body">-->
  <!--            <div class="mb-3">-->
  <!--              <h6 class="text">Name:</h6>-->
  <!--              <input id="editName" v-model="editedProject.name" class="form-control" type="text">-->
  <!--              <div v-if="!isValidProjectName" class="text-danger mt-1">Project name is required</div>-->
  <!--            </div>-->
  <!--            <div class="mb-3">-->
  <!--              <h6 class="text">Status:</h6>-->
  <!--              <select id="editStatus" v-model="editedProject.status" class="form-select">-->
  <!--                <option v-for="statusOption in Project.statusList" :key="statusOption.value"-->
  <!--                        :value="statusOption.value">-->
  <!--                  {{ statusOption.displayName }}-->
  <!--                </option>-->
  <!--              </select>-->
  <!--              <div v-if="!isValidProjectStatus" class="text-danger mt-1">Project status is required</div>-->
  <!--            </div>-->
  <!--            <div class="mb-3">-->
  <!--              <h6 class="text">Assigned teams:</h6>-->
  <!--              <select id="editTeams" v-model="editedProject.team" class="form-select">-->
  <!--                <option v-for="team in teams" :key="team" :value="team">-->
  <!--                  {{ team.name }}-->
  <!--                </option>-->
  <!--              </select>-->
  <!--              <div v-if="!isValidProjectTeam" class="text-danger mt-1">Project team is required</div>-->
  <!--            </div>-->
  <!--            <div class="mb-3">-->
  <!--              <h6 class="text">Description:</h6>-->
  <!--              <textarea id="editDescription" v-model="editedProject.description" class="form-control"></textarea>-->
  <!--            </div>-->
  <!--            <div v-if="!isValidProjectDescription" class="text-danger mt-1">Project description is required</div>-->
  <!--          </div>-->

  <!--          <div class="modal-footer d-flex justify-content-start">-->
  <!--            <button :disabled="!isValidProject()" class="btn btn-primary" data-bs-dismiss="modal" type="button"-->
  <!--                    @click="saveProject">Save-->
  <!--            </button>-->
  <!--            <button aria-label="Delete" class="btn btn-danger" data-bs-dismiss="modal" type="button"-->
  <!--                    @click="deleteProject">Delete-->
  <!--            </button>-->
  <!--            &lt;!&ndash;            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal" @click="deselectProject">Close</button>&ndash;&gt;-->
  <!--          </div>-->
  <!--        </div>-->
  <!--      </div>-->
  <!--    </div>-->
  <!--  </div>-->
  <v-container fluid>
    <base-card class="mt-1" color="secondary" title="Projects">
      <v-data-table
          v-model:items-per-page="itemsPerPage"
          :headers="headers"
          :items="projects"
          :search="search"
          class="elevation-1"
          item-value="id">
        <template v-slot:top>
          <v-toolbar flat>
            <v-dialog v-model="dialogNew" max-width="800px">
              <template v-slot:activator="{ props }">
                <v-text-field v-model="search" label="Search Project" prepend-inner-icon="$search" variant="outlined">
                </v-text-field>
                <v-spacer></v-spacer>
                <v-btn class="mb-2" color="secundary" dark v-bind="props" @click="newProject">New Project</v-btn>
              </template>
              <v-card title="New Project">
                <v-card-text>
                  <v-container>
                    <v-form>
                      <v-col>
                        <!--                        TODO: re-add rules-->
                        <v-row>
                          <v-text-field v-model="editedProject.name" label="Name" required/>
                        </v-row>
                        <v-row>
                          <v-text-field v-model="editedProject.startDate" label="Start Date" type="date" required/>
                        </v-row>
                        <v-row>
                          <v-text-field v-model="editedProject.endDate" label="End Date" type="date"  required/>
                        </v-row>
                        <v-row>
                          <v-select v-model="editedProject.status" :items="Project.statusList"
                                    item-title="displayName" item-value="value" label="Status"/>
                        </v-row>
                        <v-row>
                          <v-select v-model="editedProject.teamId" :items="teams" label="Team"
                                    :item-title="item => item.name" :item-value="item => item.id"/>
                        </v-row>
                        <v-row>
                          <v-textarea v-model="editedProject.description" label="Description"/>
                        </v-row>
                      </v-col>
                    </v-form>
                  </v-container>
                </v-card-text>
              </v-card>
              <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn color="blue darken-1" text @click="close">Cancel</v-btn>
                <v-btn color="blue darken-1" text @click="saveNew">Save</v-btn>
              </v-card-actions>
            </v-dialog>
            <v-dialog v-model="dialogEdit" max-width="800px">
              <v-card title="Edit Project">
                <v-card-text>
                  <v-form>
                    <v-col>
                      <!--                        TODO: re-add rules-->
                      <v-row>
                        <v-text-field v-model="editedProject.name" label="Name" required></v-text-field>
                      </v-row>
                      <v-row>
                        <v-text-field v-model="editedProject.startDate" label="Start Date" type="date"
                                      required></v-text-field>
                      </v-row>
                      <v-row>
                        <v-text-field v-model="editedProject.endDate" label="End Date" type="date"
                                      required></v-text-field>
                      </v-row>
                      <v-row>
                        <v-select v-model="editedProject.status" :items="Project.statusList"
                                  item-title="displayName" item-value="value" label="Status">
                        </v-select>
                      </v-row>
                      <v-row>
                        <v-select v-model="editedProject.teamId" :items="teams" label="Team"
                                  :item-title="item => item.name" :item-value="item => item.id"/>
                      </v-row>
                      <v-row>
                        <v-textarea v-model="editedProject.description" label="Description"></v-textarea>
                      </v-row>
                    </v-col>
                  </v-form>
                </v-card-text>
                <v-card-actions>
                  <v-spacer></v-spacer>
                  <v-btn color="blue darken-1" variant="text" @click="close">Cancel</v-btn>
                  <v-btn color="blue darken-1" variant="text" @click="saveEdited">Save</v-btn>
                </v-card-actions>
              </v-card>
            </v-dialog>
            <v-dialog v-model="dialogDetail" max-width="800px">
              <v-card>
                <v-tabs v-model="tab" bg-color="transparent">
                  <v-tab value="details">Project Detail</v-tab>
                  <v-tab value="tasks">Project Tasks</v-tab>
                  <v-tab value="materials">Project Materials</v-tab>
                </v-tabs>
                <v-card-text>
                  <v-window v-model="tab">
                    <v-window-item value="details">
                      <v-container>
                        <v-list>
                          <v-list-item title="Name" :subtitle="selectedProject.name"/>
                          <v-list-item title="Start Date" :subtitle="selectedProject.startDate"/>
                          <v-list-item title="End Date" :subtitle="selectedProject.endDate"/>
                          <v-list-item title="Status" :subtitle="selectedProject.status"/>
                          <v-list-item title="Team" :subtitle="teams.find(t => t.id === selectedProject.teamId)?.name"/>
                          <v-list-item title="Description" :subtitle="selectedProject.description"/>
                        </v-list>
                      </v-container>
                    </v-window-item>
                    <v-window-item value="tasks">
                      <v-container>
                        <h2>
                          In development
                        </h2>
                      </v-container>
                    </v-window-item>
                    <v-window-item value="materials">
                      <v-container>
                        <h2>
                          In development
                        </h2>
                      </v-container>
                    </v-window-item>
                  </v-window>
                </v-card-text>
              </v-card>
            </v-dialog>
            <v-dialog v-model="dialogDelete" max-width="800px">
              <v-card>
                <v-card-title class="text-h5">Are you sure you want to delete this warehouse?</v-card-title>
                <v-card-actions>
                  <v-spacer></v-spacer>
                  <v-btn color="blue-darken-1" variant="text" @click="close">Cancel</v-btn>
                  <v-btn color="blue-darken-1" variant="text" @click="deleteItemConfirm">OK</v-btn>
                  <v-spacer></v-spacer>
                </v-card-actions>
              </v-card>
            </v-dialog>
          </v-toolbar>
        </template>
        <template v-slot:[`item.status`]="{ item }">
          <!--          TODO: change text color-->
          <v-chip :color="getStatusColor(item)" :text="getStatusDisplayName(item.status)">
          </v-chip>
        </template>
        <template v-slot:[`item.teamName`]="{ item }">
          {{ this.teams.find(t => t.id === item.teamId)?.name }}
        </template>
        <template v-slot:[`item.actions`]="{ item }">
          <v-icon @click="seeDetails(item)">$info</v-icon>
          <v-icon @click="edit(item)">$edit</v-icon>
          <v-icon @click="deleteProject(item)">$delete</v-icon>
        </template>
      </v-data-table>
    </base-card>
  </v-container>

</template>

<script>
import {Project} from "@/models/Project.js";
import BaseCard from "@/components/base/BaseCard.vue";

export default {
  name: "ProjectListComponent",
  computed: {
    Project() {
      return Project
    }
  },
  components: {BaseCard},
  inject: ['projectsService', 'teamsService'],
  data() {
    return {
      headers: [
        {title: 'Name', key: 'name'},
        //   TODO: Add team name
        {title: 'Team', key: 'teamName'},
        {title: 'Start Date', key: 'startDate'},
        {title: 'End Date', key: 'endDate'},
        {text: 'Status', key: 'status'},
        {title: 'Actions', key: 'actions', sortable: false}
      ],
      projects: [],
      teams: [],
      selectedProject: new Project(),
      editedProject: new Project(),
      search: "",
      tab: "",
      itemsPerPage: 10,
      isAddingNewProject: false,

      dialogDelete: false,
      dialogEdit: false,
      dialogDetail: false,
      dialogNew: false,
    };
  },

  async mounted() {
    await this.initialize();
  },

  watch: {
    dialogNew(val) {
      val || this.close();
    },
    dialogEdit(val) {
      val || this.close();
    },
    dialogDelete(val) {
      val || this.close();
    },
    dialogDetail(val) {
      val || this.close();
    },
  },

  methods: {
    async initialize() {
      await this.getProjects();
      await this.getTeams();
    },

    async getTeams() {
      this.teams = await this.teamsService.asyncGetAll();
    },

    async getProjects() {
      this.projects = await this.projectsService.asyncGetAll();
    },

    getStatusColor(project) {
      switch (project.status) {
        case "PENDING":
          return "yellow";
        case "CONFIRMED":
          return "green";
        case "IN_PROGRESS":
          return "blue";
        case "FINISHED":
          return "green";
        case "CANCELED":
          return "red";
        default:
          return "grey";
      }
    },

    getStatusDisplayName(status) {
      return Project.statusList.find(s => s.value === status)?.displayName;
    },

    newProject() {
      this.assignSelectedProject(new Project());
      this.dialogNew = true;
    },

    async saveNew() {
      if (!this.isValidProject()) {
        return;
      }
      await this.projectsService.asyncAdd(this.editedProject);
      this.close();
      await this.getProjects();
    },

    edit(project) {
      this.assignSelectedProject(project);
      this.dialogEdit = true;
    },

    async saveEdited() {
      if (!this.isValidProject()) {
        return;
      }
      await this.projectsService.asyncUpdate(this.editedProject.id, this.editedProject);
      this.close();
      await this.getProjects();
    },

    deleteProject(project) {
      this.assignSelectedProject(project);
      this.dialogDelete = true;
    },

    async deleteItemConfirm() {
      await this.projectsService.asyncDeleteById(this.selectedProject.id);
      this.close();
      await this.getProjects();
    },

    seeDetails(project) {
      console.log(project)
      this.assignSelectedProject(project);
      console.log(this.selectedProject);
      console.log(this.editedProject);
      this.dialogDetail = true;
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
      return !!this.editedProject.name;
    },

    isValidProjectStatus() {
      return !!this.editedProject.status;
    },

    isValidProjectTeam() {
      return !!this.editedProject.team;
    },

    isValidProjectDescription() {
      return !!this.editedProject.description;
    },

    assignSelectedProject(project) {
      this.selectedProject = Object.assign(new Project(), project);
      this.editedProject = Object.assign(new Project(), project);
    },

    close() {
      this.assignSelectedProject(new Project())
      this.dialogEdit = false;
      this.dialogDelete = false;
      this.dialogDetail = false;
      this.dialogNew = false;
    },
  },
};

</script>

<style scoped>
.modal {
  --bs-modal-header-border-color: transparent;
}

.modal-header {
  background-color: #28a745;
  color: white;
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
