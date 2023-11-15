<!-- EditTeamModal.vue -->

<!--<template>-->
<!--  <div class="modal" tabindex="-1" role="dialog" style="display: block;">-->
<!--    <div class="modal-dialog" role="document">-->
<!--      <div class="modal-content">-->
<!--        <div class="modal-header">-->
<!--          <h5 class="modal-title">Edit Team</h5>-->
<!--          <button type="button" class="close" @click="closeModal">-->
<!--            <span aria-hidden="true">&times;</span>-->
<!--          </button>-->
<!--        </div>-->
<!--        <div class="modal-body">-->
<!--          <label for="name">Name</label>-->
<!--          <input type="text" v-model="teamName" class="form-control">-->

<!--          <label for="chooseWarehouse">Warehouse</label>-->
<!--          <select v-model="selectedWarehouse" class="form-control">-->
<!--            <option v-for="warehouse in warehouses" :key="warehouse.id" :value="warehouse.id">-->
<!--              {{ warehouse.name }}-->
<!--            </option>-->
<!--          </select>-->
<!--        </div>-->
<!--        <div class="modal-footer">-->
<!--          <button type="button" class="btn btn-secondary" @click="closeModal">Close</button>-->
<!--          <button type="button" class="btn btn-primary" @click="saveChanges">Save changes</button>-->
<!--        </div>-->
<!--      </div>-->
<!--    </div>-->
<!--  </div>-->
<!--</template>-->
<template>
  <div class="modal fade" ref="addModalRef" tabindex="-1" aria-labelledby="ModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
      <div class="modal-content">
        <div class="modal-header">
          <h1 class="modal-title fs-5 text-center" id="ModalLabel">New Team</h1>
          <button @click="closeModal" type="button" class="btn-close" data-bs-dismiss="modal"
                  aria-label="Close"></button>
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
          <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
          <button type="button" class="btn btn-primary" @click="saveChanges">Save changes</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>

import {Modal} from "bootstrap";

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
    this.modal = new Modal(this.$refs.addModalRef);
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
      this.$emit('closeModal');
    },
  },
};
</script>

<style scoped>

</style>
