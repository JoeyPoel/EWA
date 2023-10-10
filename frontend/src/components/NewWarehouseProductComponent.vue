<template>
  <div class="row">
    <div class="col col-9 align-self-center ">
      <div class="row">
        <div class="col col-6">
          <div class="row">
            <div class="col col-3 align-self-center text-end">
              <label class="form-label" for="product">Product</label>
            </div>
            <div class="col col-9">
              <select class="form-control" id="product" v-model="warehouseProduct.productId">
                <option v-for="product in this.products" :key="product.id" :value="product.id">
                  {{ product.name }}
                </option>
              </select>
            </div>
          </div>
        </div>
        <div class="col col-6">
          <div class="row">
            <div class="col col-7 align-self-center text-end">
              <label class="form-label" for="quantity">Minimum stock level</label>
            </div>
            <div class="col col-5">
              <input class="form-control" id="quantity" type="number" v-model="warehouseProduct.minimumQuantity"/>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="col col-3">
      <div class="row ">
        <button class="btn btn-success" @click="addProduct()">
          Add Product
        </button>
      </div>
    </div>
  </div>
</template>

<script>
import {WarehouseProduct} from "@/models/WarehouseProduct"

export default {
  name: "NewWarehouseProductComponent",
  props: {
    warehouses: {
      type: Array,
      required: true
    },
    products: {
      type: Array,
      required: true
    },
  },
  data() {
    return {
      warehouseProduct: new WarehouseProduct(),
    };
  },
  methods: {
    addProduct() {
      this.warehouseProduct.warehouseId = parseInt(this.$route.params.id);
      this.$emit("add-product", this.warehouseProduct);
      this.warehouseProduct = new WarehouseProduct();
    },
  }
}
</script>

<style scoped>

</style>