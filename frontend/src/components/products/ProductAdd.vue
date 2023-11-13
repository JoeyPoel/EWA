<template>
  <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <h1 class="modal-title fs-5" id="exampleModalLabel">Modal title</h1>
          <button @click="closeModal" type="button" class="btn-close" data-bs-dismiss="modal"
                  aria-label="Close"></button>
        </div>
        <div class="modal-body">
          ...
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
          <button type="button" class="btn btn-primary">Save changes</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {Modal} from 'bootstrap';

export default {
  name: "ProductAdd",

  mounted() {
    this.modal = new Modal(this.$refs.modalRef);

    if (this.product) {
      this.modal.show();
    }

    this.$refs.modalRef.addEventListener('hidden.bs.modal', this.closeModal);
  },

  methods: {
    closeModal() {
      this.modal.hide();
      this.$emit('close-modal');
    }
  },
};
</script>

<style scoped>

</style>