<template>
  <div ref="addModalRef" aria-hidden="true" aria-labelledby="ModalLabel" class="modal fade" tabindex="-1">
    <div class="modal-dialog modal-dialog-centered">
      <div class="modal-content">
        <div class="modal-header">
          <h1 id="ModalLabel" class="modal-title fs-5">Add Product</h1>
          <button aria-label="Close" class="btn-close" data-bs-dismiss="modal" type="button"
                  @click="closeModal"></button>
        </div>
        <div class="modal-body">
          <div class="mb-3">
            <label class="form-label" for="InputProductName">Product Name</label>
            <input v-model="newProduct.name" class="form-control" type="text">
          </div>
          <div class="mb-3">
            <label class="form-label" for="InputProductDescription">Product Description</label>
            <input v-model="newProduct.description" class="form-control" type="text">
          </div>
          <div class="mb-3">
            <label class="form-label" for="InputProductPrice">Product Price</label>
            <input v-model="newProduct.price" class="form-control" type="number">
          </div>
          <div class="mb-3">
            <label class="form-label" for="InputProductCategory">Product Category</label>
            <select aria-label="Default select example" class="form-select" v-model="newProduct.categoryId">
              <option selected>Open this select menu</option>
              <option v-for="category in categories" :key="category.id" :value="category.id">
                {{ category.name }}
              </option>
            </select>
          </div>
        </div>
        <div class="modal-footer">
          <button class="btn btn-secondary" data-bs-dismiss="modal" type="button">Close</button>
          <button :disabled="!isFormValid" class="btn btn-primary" type="button" @click="saveProduct">Save Product
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {Modal} from 'bootstrap';
import {Product} from "@/models/Product";

export default {
  name: "ProductAdd",
  inject: ['productsService'],
  data() {
    return {
      newProduct: new Product(),
      categories: [],
    };
  },
  async mounted() {
    this.$nextTick(() => {
      // Check if the element is available
      if (this.$refs.addModalRef) {
        this.modal = new Modal(this.$refs.addModalRef);
        this.modal.show();
        this.$refs.addModalRef.addEventListener('hidden.bs.modal', this.closeModal);
      }
    });
    this.categories = await this.productsService.asyncGetAllProductCategories();
  },
  computed: {
    isFormValid() {
      if (!this.newProduct) {
        return;
      }
      return this.newProduct.name && this.newProduct.description &&
          this.newProduct.name.trim() !== '' && this.newProduct.description.trim() !== '';
    }
  },
  methods: {
    closeModal() {
      if (this.modal) {
        this.modal.hide();
      }
      this.$emit('close-modal');
    },
    resetForm() {
      this.newProduct = new Product();
    },
    async saveProduct() {
      await this.productsService.asyncAdd(this.newProduct);
      this.resetForm();
      this.closeModal();
      this.$emit('add-product', this.newProduct);
    }
  },
};
</script>

<style scoped>

</style>