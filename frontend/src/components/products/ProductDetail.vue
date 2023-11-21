<template>
  <div class="modal fade" ref="modalRef" tabindex="-1" aria-labelledby="ModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
      <div class="modal-content">

        <div class="modal-header">
          <h5 class="modal-title">{{ product.name }}</h5>
          <button @click="closeModal" type="button" class="btn-close" data-bs-dismiss="modal"
                  aria-label="Close"></button>
        </div>

        <div class="card text-center">
          <div class="card-header">
            <ul class="nav nav-tabs card-header-tabs">

              <li class="nav-item">
                <a class="nav-link active"
                   aria-current="true"
                   data-bs-target="#details-content"
                   data-bs-toggle="tab">
                  Details
                </a>
              </li>

              <li class="nav-item">
                <a class="nav-link"
                   data-bs-target="#stock-content"
                   data-bs-toggle="tab">
                  Stock
                </a>
              </li>

            </ul>
          </div>

          <div class="card-body tab-content">

            <div class="tab-pane fade show active" id="details-content">
              <h6>Description: </h6>
              {{ product.description }}
            </div>

            <div class="tab-pane fade" id="stock-content">
              <h5 class="card-title">In Development</h5>
            </div>

          </div>

        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {Modal} from 'bootstrap';

export default {
  name: "ProductDetail",

  props: {
    product: Object
  },

  mounted() {
    this.modal = new Modal(this.$refs.modalRef);

    if (this.product) {
      this.modal.show();
    }

    this.$refs.modalRef.addEventListener('hidden.bs.modal', this.closeModal);
    console.log('Modal ref:', this.$refs);
  },

  beforeUnmount() {
    if (this.$refs.modalRef) {
      this.$refs.modalRef.removeEventListener('hidden.bs.modal', this.closeModal);
    }
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
.modal {
  --bs-modal-header-border-color: transparent;
}

.modal-header {
  outline: none;
}

.card {
  --bs-card-border-radius: none;
}
</style>