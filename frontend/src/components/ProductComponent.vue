<template>
  <div class="container mt-3">
    <!-- Search bar and Add Product Button -->
    <div class="d-flex justify-content-between mb-3">
      <input v-model="searchTerm" type="text" class="form-control w-25" placeholder="Search for name...">
      <button @click="showAddModal" class="btn btn-success">Add Product</button>
    </div>

    <!-- Product List -->
    <ul class="list-group product-list">
      <li
          class="list-group-item d-flex justify-content-between align-items-start"
          v-for="(product, index) in filteredProducts"
          :key="index"
          @click="selectProduct(product)">
        <div class="ms-2 me-auto">
          <div class="fw-bold">{{ product.name }}</div>
          {{ product.description }}
        </div>
      </li>
    </ul>

    <router-view
        v-if="shouldShowModal"
        :product="selectedProduct"
        @close-modal="deselectProduct"
        @add-product="handleNewProduct"
    ></router-view>

  </div>
</template>

<script>

import {Product} from '@/models/Product'

export default {
  name: "ProductComponent",
  inject: ['productsService'],
  data() {
    return {
      products: [],
      productCount: 0,
      selectedProduct: null,
      searchTerm: "",
      isModalVisible: false
    }
  },

  async created() {
    this.products = await this.productsService.asyncFindAll()
  },

  methods: {
    async handleNewProduct(newProduct) {
      this.searchTerm = ""
      const product = Product.createRandomDummyProduct(this.productCount);
      product.name = newProduct.name;
      product.description = newProduct.description;
      const savedProduct = await this.productsService.asyncSave(product);

      if (savedProduct){
        this.products.push(product);
        this.deselectProduct();
      }

    },

    selectProduct(product) {
      this.selectedProduct = product;
      this.$router.push({name: 'ProductDetail', params: {id: product.id}});
    },

    deselectProduct() {
      this.selectedProduct = null;
      this.$router.push({name: 'Product'});
    },

    showAddModal() {
      this.$router.push({ name: 'ProductAdd'});
    }
  },

  computed: {
    filteredProducts() {
      if (!this.searchTerm) return this.products;

      return this.products.filter(product => {
        return product.name.toLowerCase().includes(this.searchTerm.toLowerCase());
      });
    },
    shouldShowModal() {
      return this.selectedProduct !== null || this.$route.name === 'ProductAdd';
    }
  }
}
</script>
<style scoped>
.list-group-item:hover {
  cursor: pointer;
  background-color: #f5f5f5;
}

.product-list {
  max-height: 90vh;
  overflow-y: auto;
}
</style>