<template>
  <li class="list-group-item d-flex justify-content-between align-items-start" @click="openModal">
    <div class="ms-2 me-auto">
      <div class="row">
        <div class="col">
          <h6 class="fw-bold">{{ project.name }}</h6>
        </div>
        <div class="col text-end">
          <span class="badge bg-info">{{ project.status }}</span>
          <button class="btn btn-danger btn-sm ms-2" @click.stop="deleteProject">Delete</button>
        </div>
      </div>
      <p>{{ project.team }}</p>
    </div>

    <!-- Modal -->
    <div v-if="isModalOpen" class="modal fade" :id="modalId" tabindex="-1" aria-labelledby="projectModalLabel" aria-hidden="true">
      <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
        <div class="modal-content">
          <div class="modal-header bg-success text-white">
            <h5 class="modal-title">{{ project && project.name ? project.name : 'New Project' }}</h5>
            <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal" aria-label="Close" @click="closeModal"></button>
          </div>
          <div class="modal-body">
            <div class="mb-3">
              <label for="editStatus" class="form-label text-success">Status:</label>
              <input type="text" id="editStatus" v-model="editedProject.status" class="form-control">
            </div>
            <div class="mb-3">
              <label for="editTeams" class="form-label text-success">Assigned Teams:</label>
              <input type="text" id="editTeams" v-model="editedProject.team" class="form-control">
            </div>
            <div class="mb-3">
              <label for="editDescription" class="form-label text-success">Description:</label>
              <textarea id="editDescription" v-model="editedProject.description" class="form-control"></textarea>
            </div>

            <button class="btn btn-success" @click="saveChanges">Save Changes</button>
            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal" @click="closeModal">Close</button>
          </div>
        </div>
      </div>
    </div>
    <!-- End Modal -->
  </li>
</template>

<script>
export default {
  name: "ProjectDetailComponent",
  props: {
    project: Object,
  },
  data() {
    return {
      editedProject: { ...this.project },
      isModalOpen: false,
    };
  },
  computed: {
    modalId() {
      return `projectModal-${this.project.id}`;
    },
  },
  methods: {
    openModal() {
      this.$emit("selectProject", this.project);
      this.isModalOpen = true;
    },

    closeModal() {
      this.$emit("deselectProject");
      this.isModalOpen = false;
    },

    deleteProject(event) {
      event.stopPropagation();
      this.$emit("deleteProject", this.project);
    },
    saveChanges() {
      this.$emit("saveChanges", this.editedProject);
      this.closeModal();
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
</style>
