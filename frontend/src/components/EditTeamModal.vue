<!-- EditTeamModal.vue -->

<template>
  <div class="modal" tabindex="-1" role="dialog" style="display: block;">
    <div class="modal-dialog" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title">Edit Team</h5>
          <button type="button" class="close" @click="closeModal">
            <span aria-hidden="true">&times;</span>
          </button>
        </div>
        <div class="modal-body">
          <label for="name">Name</label>
          <input type="text" v-model="teamName" class="form-control">

          <label for="chooseWarehouse">Warehouse</label>
          <select v-model="selectedWarehouse" class="form-control">
            <option v-for="warehouse in warehouses" :key="warehouse.id" :value="warehouse.id">
              {{ warehouse.name }}
            </option>
          </select>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-secondary" @click="closeModal">Close</button>
          <button type="button" class="btn btn-primary" @click="saveChanges">Save changes</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    team: Object, // Team object to edit
    warehouses: Array, // Array of warehouses
  },
  data() {
    return {
      teamName: '',
      selectedWarehouse: null,
    };
  },
  mounted() {
    // Populate modal fields when component is mounted
    this.teamName = this.team.name;
    this.selectedWarehouse = this.team.warehouseId;
  },
  methods: {
    saveChanges() {
      // Emit an event with updated team data
      this.$emit('save', {
        id: this.team.id,
        name: this.teamName,
        warehouseId: this.selectedWarehouse,
      });
      this.closeModal();
    },
    closeModal() {
      this.$emit('close');
    },
  },
};
</script>

<style scoped>
/* Your modal CSS styles */
</style>
