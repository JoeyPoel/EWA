<template>
  <div class="modal fade" ref="addModalRef" tabindex="-1" aria-labelledby="ModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
      <div class="modal-content">
        <div class="modal-header">
          <h1 class="modal-title fs-5 text-center" id="ModalLabel">New Warehouse</h1>
          <button @click="closeModal" type="button" class="btn-close" data-bs-dismiss="modal"
                  aria-label="Close"></button>
        </div>
        <div class="modal-body">
          <div class="row">
            <form class="col">
              <div class="mt-1 mb-1 row">
                <div class="col col-4 align-self-center text-end">
                  <label class="form-label" for="name">Name</label>
                </div>
                <div class="col col-8">
                  <input class="form-control" id="name" type="text" v-model="this.newWarehouse.name"/>
                </div>
              </div>
              <div class="mt-1 mb-1 row">
                <div class="col col-4 align-self-center text-end">
                  <label class="form-label" for="location">Location</label>
                </div>
                <div class="col col-8">
                  <select class="form-control" id="location" v-model="this.newWarehouse.location">
                    <option v-for="location in Warehouse.locationList" :key="location">
                      {{ location }}
                    </option>
                  </select>
                </div>
              </div>
              <div class="mt-1 mb-1 row">
                <div class="col col-4 align-self-center text-end">
                  <label class="form-label" for="postcode">Postcode</label>
                </div>
                <div class="col col-8">
                  <input class="form-control" id="postcode" type="text" v-model="this.newWarehouse.postcode"/>
                </div>
              </div>
              <div class="mt-1 mb-1 row">
                <div class="col col-4 align-self-center text-end">
                  <label class="form-label" for="country">Country</label>
                </div>
                <div class="col col-8">
                  <input class="form-control" id="country" type="text" v-model="this.newWarehouse.country"/>
                </div>
              </div>
              <div class="mt-1 mb-1 row">
                <div class="col col-4 align-self-center text-end">
                  <label class="form-label" for="contactName">Contact Name</label>
                </div>
                <div class="col col-8">
                  <input class="form-control" id="contactName" type="text" v-model="this.newWarehouse.contactName"/>
                </div>
              </div>
              <div class="mt-1 mb-1 row">
                <div class="col col-4 align-self-center text-end">
                  <label class="form-label" for="contactEmail">Contact Email</label>
                </div>
                <div class="col col-8">
                  <input class="form-control" id="contactEmail" type="text" v-model="this.newWarehouse.contactEmail"/>
                </div>
              </div>
              <div class="mt-1 mb-1 row">
                <div class="col col-4 align-self-center text-end">
                  <label class="form-label" for="contactPhone">Contact Phone</label>
                </div>
                <div class="col col-8">
                  <input class="form-control" id="contactPhone" type="text" v-model="this.newWarehouse.contactPhone"/>
                </div>
              </div>
            </form>
          </div>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
          <button type="button" class="btn btn-primary" @click="saveWarehouse">Save Warehouse</button>
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
  computed: {
    Warehouse() {
      return Warehouse
    }
  },

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

    saveWarehouse() {

      // this.$emit('add-product', { ...this.newProduct });
      this.closeModal();
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