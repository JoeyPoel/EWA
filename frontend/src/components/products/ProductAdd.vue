<template>
  <div class="modal fade" ref="addModalRef" tabindex="-1" aria-labelledby="ModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
      <div class="modal-content">

        <div class="modal-header">
          <h1 class="modal-title fs-5" id="ModalLabel">Modal title</h1>
          <button @click="closeModal" type="button" class="btn-close" data-bs-dismiss="modal"
                  aria-label="Close"></button>
        </div>

        <div class="modal-body">

          <div class="mb-3">
            <label for="InputProductName" class="form-label">Product Name</label>
            <input type="text" class="form-control" v-model="newProduct.name">
          </div>

          <div class="mb-3">
            <label for="InputProductDescription" class="form-label">Product Description</label>
            <input type="text" class="form-control" v-model="newProduct.description">
          </div>

        </div>

        <div class="modal-footer">
          <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
          <button type="button" class="btn btn-primary" @click="saveProduct">Save Product</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {Modal} from 'bootstrap';

export default {
  name: "ProductAdd",

  data() {
    return {
      newProduct: {
        name: '',
        description: ''
      }
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

    saveProduct() {
      this.$emit('add-product', { ...this.newProduct });
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