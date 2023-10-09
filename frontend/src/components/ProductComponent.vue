<template>
  <div class="container mt-3">
    <!-- Search bar and Add Product Button -->
    <div class="d-flex justify-content-between mb-3">
      <input v-model="searchTerm" type="text" class="form-control w-25" placeholder="Search for name...">
      <button @click="addProduct" class="btn btn-success">Add Product</button>
    </div>

    <!-- Product List -->
    <ul class="list-group product-list">
      <li
          class="list-group-item d-flex justify-content-between align-items-start"
          data-bs-toggle="modal"
          data-bs-target="#Modal"
          v-for="(product, index) in filteredProducts"
          :key="index"
          @click="selectProduct(product)">
        <div class="ms-2 me-auto">
          <div class="fw-bold">{{ product.name }}</div>
          {{ product.description }}
        </div>
      </li>
    </ul>

    <!-- Product Item Modal -->
    <div class="modal fade" id="Modal" tabindex="-1" aria-labelledby="ModalLabel" aria-hidden="true">
      <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
        <div class="modal-content">

          <div class="modal-header">
            <h5 class="modal-title">{{ selectedProduct.name }}</h5>
            <button @click="deselectProduct" type="button" class="btn-close" data-bs-dismiss="modal"
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
                {{ selectedProduct.description }}
              </div>

              <div class="tab-pane fade" id="stock-content">
                <h5 class="card-title">In Development</h5>
              </div>

            </div>

          </div>
        </div>
      </div>
    </div>

  </div>
</template>

<script>
import {product} from '@/models/product.js';

export default {
  name: "ProductComponent",

  data() {
    return {
      products: [],
      productCount: 0,
      selectedProduct: {},
      searchTerm: ""
    }
  },

  created() {
    for (let i = 0; i < 3; i++) {
      this.productCount++
      this.products.push(
          product.createRandomDummyProduct(this.productCount)
      );
    }
  },

  methods: {
    addProduct() {

      /**
       * Reset searchTerm because it would just be awkward if you have a search term that doesn't correspond with
       * the newly created random product. It wouldn't show if it didn't match, and I don't really like that.
       */

      this.searchTerm = ""

      /**
       * Here we just push a new product with random values for title and description.
       */

      this.productCount++
      this.products.push(product.createRandomDummyProduct(this.productCount));
    },

    selectProduct(product) {
      this.selectedProduct = product;
    },

    deselectProduct() {
      this.selectedProduct = {};
    }
  },

  computed: {
    filteredProducts() {
      if (!this.searchTerm) return this.products;

      return this.products.filter(product => {
        return product.name.toLowerCase().includes(this.searchTerm.toLowerCase());
      });
    }
  }
}
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

.list-group-item:hover {
  cursor: pointer;
  background-color: #f5f5f5;
}

.product-list {
  max-height: 90vh;
  overflow-y: auto;
}
</style>
