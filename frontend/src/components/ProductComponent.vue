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
import {product} from '@/models/product.js';

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

    // for (let i = 0; i < 3; i++) {
    //   this.productCount++
    //   this.products.push(
    //       product.createRandomDummyProduct(this.productCount)
    //   );
    // }
  },

  methods: {
    addProduct() {
      this.searchTerm = ""

      this.productCount++
      this.products.push(product.createRandomDummyProduct(this.productCount));
    },

    handleNewProduct(newProduct) {
      const Product = product.createRandomDummyProduct(this.productCount);
      Product.name = newProduct.name;
      Product.description = newProduct.description;
      this.products.push(Product);
      this.deselectProduct();
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