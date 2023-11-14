<template>
  <div class="m-0 border border-2 rounded border-light-subtle">
    <div class="row m-0 justify-content-center border border-2 rounded border-light-subtle ">
      <new-warehouse-product-component :products="products" :warehouses="warehouses" @add-product="addProduct"/>
    </div>
    <div class="row m-0 justify-content-center">
      <div class="col m-0 align-self-center">
        <div class="row m-0 justify-content-center">
          <div class="scrollPanel">
            <table>
              <thead>
              <tr>
                <th class="fw-light">Product ID</th>
                <th class="fw-light">Product Name</th>
<!--                <th class="fw-light">Current stock</th>-->
                <th class="fw-light">Minimum stock level</th>
                <th class="fw-light">Actions</th>
              </tr>
              </thead>
              <tbody>
              <tr class="border border-light-subtle border-1" v-for="product in inventory" :key="product.productId">
                <td>{{ product.productId }}</td>
                <td>{{ WarehouseProduct.getProductName(product.productId, products) }}</td>
<!--                <td>{{ ProductTransaction.getCurrentQuantity(product.productId, selected.id, transactions) }}</td>-->
                <td>
                  <input class="form-control" type="number" v-model="product.minimumQuantity"/>
                </td>
                <td>
                  <div class="row p-1">
                    <a class="link-success " @click="saveProduct(product)">
                      Save
                    </a>
                    <a class="link-danger" @click="removeProduct(product)">
                      Remove
                    </a>
                  </div>
                </td>
              </tr>
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {Warehouse} from "@/models/Warehouse";
import {WarehouseProduct} from "@/models/WarehouseProduct";
// import {ProductTransaction} from "@/models/productTransaction";
import NewWarehouseProductComponent from "@/components/warehouses/NewWarehouseProductComponent.vue";

export default {
  name: "WarehouseDetailInventoryComponent",
  components: {NewWarehouseProductComponent},
  inject: ['productsService'],
  props: {
    selected: {
      type: Object,
      required: true
    },
    warehouses: {
      type: Array,
      required: true
    },
    products: {
      type: Array,
      required: true
    },
    inventory: {
      type: Array,
      required: true
    },
  },
  data() {
    return {
      lastId: 1000,
      isActive: true,
      Warehouse: Warehouse,
      WarehouseProduct: WarehouseProduct,
    }
  },
  async created() {
  },
  computed: {
  },
  methods: {
    addProduct(product) {
      this.$emit('add-product', product)
    },
    removeProduct(product) {
      this.$emit('remove-product', product)
    },
    saveProduct(product) {
      this.$emit('save-product', product)
    }
    // generateTransactions() {
    //   for (let i = 0; i < this.warehouses.length; i++) {
    //     for (let j = 0; j < this.products.length; j++) {
    //       for (let k = 0; k < 5; k++) {
    //         this.transactions.push(
    //             new ProductTransaction(this.lastId++,
    //                 this.warehouses[i].id,
    //                 this.products[j].id,
    //                 Math.floor(Math.random() * 20) + 5
    //             ))
    //       }
    //     }
    //   }
    // },
    // generateInventories() {
    //   for (let i = 0; i < this.warehouses.length; i++) {
    //     for (let j = 0; j < this.products.length; j++) {
    //       this.inventories.push(
    //           new WarehouseProduct(i,
    //               this.warehouses[i].id,
    //               this.products[j].id,
    //               Math.floor(Math.random() * 100) + 5
    //           ))
    //     }
    //   }
    // },
  },
}
</script>

<style scoped>
.scrollPanel {
  max-height: 40vh;
  overflow-y: auto;
}
</style>