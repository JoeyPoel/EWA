<template>
  <div ref="addModalRef" aria-hidden="true" aria-labelledby="ModalLabel" class="modal fade" tabindex="-1">
    <div class="modal-dialog modal-dialog-centered">
      <div class="modal-content">
        <div class="modal-header">
          <h1 id="ModalLabel" class="modal-title fs-5 text-center">New Warehouse</h1>
          <button aria-label="Close" class="btn-close" data-bs-dismiss="modal" type="button"
                  @click="closeModal"></button>
        </div>
        <div class="modal-body">
          <div class="row">
            <form class="col">
              <div class="mt-1 mb-1 row">
                <div class="col col-4 align-self-center text-end">
                  <label class="form-label" for="name">Name</label>
                </div>
                <div class="col col-8">
                  <input id="name" v-model="this.newWarehouse.name" class="form-control" type="text"/>
                </div>
              </div>
              <div class="mt-1 mb-1 row">
                <div class="col col-4 align-self-center text-end">
                  <label class="form-label" for="location">Address</label>
                </div>
                <div class="col col-8">
                  <input id="location" v-model="this.newWarehouse.address" class="form-control" type="text"/>
                </div>
              </div>
              <div class="mt-1 mb-1 row">
                <div class="col col-4 align-self-center text-end">
                  <label class="form-label" for="location">Location</label>
                </div>
                <div class="col col-8">
                  <input id="location" v-model="this.newWarehouse.location" class="form-control" type="text"/>
                </div>
              </div>
              <div class="mt-1 mb-1 row">
                <div class="col col-4 align-self-center text-end">
                  <label class="form-label" for="postcode">Postcode</label>
                </div>
                <div class="col col-8">
                  <input id="postcode" v-model="this.newWarehouse.postcode" class="form-control" type="text"/>
                </div>
              </div>
              <div class="mt-1 mb-1 row">
                <div class="col col-4 align-self-center text-end">
                  <label class="form-label" for="country">Country</label>
                </div>
                <div class="col col-8">
                  <input id="country" v-model="this.newWarehouse.country" class="form-control" type="text"/>
                </div>
              </div>
              <div class="mt-1 mb-1 row">
                <div class="col col-4 align-self-center text-end">
                  <label class="form-label" for="contactName">Contact Name</label>
                </div>
                <div class="col col-8">
                  <input id="contactName" v-model="this.newWarehouse.contactName" class="form-control" type="text"/>
                </div>
              </div>
              <div class="mt-1 mb-1 row">
                <div class="col col-4 align-self-center text-end">
                  <label class="form-label" for="contactEmail">Contact Email</label>
                </div>
                <div class="col col-8">
                  <input id="contactEmail" v-model="this.newWarehouse.contactEmail" class="form-control" type="text"/>
                </div>
              </div>
              <div class="mt-1 mb-1 row">
                <div class="col col-4 align-self-center text-end">
                  <label class="form-label" for="contactPhone">Contact Phone</label>
                </div>
                <div class="col col-8">
                  <input id="contactPhone" v-model="this.newWarehouse.contactPhone" class="form-control" type="text"/>
                </div>
              </div>
            </form>
          </div>
        </div>
        <div class="modal-footer">
          <button class="btn btn-secondary" data-bs-dismiss="modal" type="button">Close</button>
          <button class="btn btn-primary" type="button" @click="saveWarehouse">Save Warehouse</button>
        </div>
      </div>
    </div>
  </div>

</template>

<script>
import Modal from 'bootstrap/js/dist/modal';
import {Warehouse} from "@/models/Warehouse";

export default {
  name: "WarehouseAddModalComponent",
  inject: ['warehousesService'],
  computed: {},
  data() {
    return {
      newWarehouse: new Warehouse(),
    };
  },
  mounted() {
    this.$nextTick(() => {
      // Check if the element is available
      if (this.$refs.addModalRef) {
        this.modal = new Modal(this.$refs.addModalRef);
        this.modal.show();
        this.$refs.addModalRef.addEventListener('hidden.bs.modal', this.closeModal);
      }
    });
  },
  methods: {
    closeModal() {
      if (this.modal) {
        this.modal.hide();
      }
      this.$emit('close-modal');
    },
    async saveWarehouse() {
      const warehouse = await this.warehousesService.asyncAdd(this.newWarehouse);
      console.log(warehouse);
      if (warehouse) {
        this.$emit('warehouse-added', warehouse);
      } else {
        alert('Error adding warehouse');
      }
      this.$router.push("/warehouse/" + warehouse.id)
    }
  },
  beforeUnmount() {
    if (this.$refs.modalRef) {
      this.$refs.modalRef.removeEventListener('hidden.bs.modal', this.closeModal);
    }
  },
};

</script>

<style scoped>

</style>