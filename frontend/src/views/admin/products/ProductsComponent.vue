<template>
  <v-container fluid>
    <v-card class="mt-1" color="secondary" title="Products">
      <v-row>
        <v-col>
          <data-filter :search="search" :can-search="true" @input="search = $event"/>
        </v-col>
      </v-row>
      <v-data-table
          v-model:items-per-page="itemsPerPage"
          :headers="headers"
          :items="products"
          :search="search"
          class="elevation-1"
          item-value="id">
        <template v-slot:top>
          <v-toolbar flat>
            <v-dialog v-model="dialogNew" max-width="800px">
              <template v-slot:activator="{ props }">
                <v-btn color="secondary" dark class="mb-2" v-bind="props">New Product</v-btn>
              </template>
              <v-card>
                <v-card-title><h5>New Product</h5></v-card-title>
                <v-card-text>
                  <v-form>
                    <v-container>
                      <v-col>
                        <v-row>
                          <v-text-field v-model="editedProduct.name" label="Name" type="text"/>
                        </v-row>
                        <v-row>
                          <v-text-field v-model="editedProduct.description" label="Description" type="text"/>
                        </v-row>
                        <v-row>
                          <v-text-field v-model="editedProduct.price" label="Price" type="number"/>
                        </v-row>
                        <v-row>
                          <v-select
                              v-model="editedProduct.categoryId"
                              :items="categories"
                              :item-title="item => item.name"
                              :item-value="item => item.id"
                              label="Category"
                          />
                        </v-row>
                      </v-col>
                    </v-container>
                  </v-form>
                </v-card-text>
                <v-card-actions>
                  <v-spacer></v-spacer>
                  <v-btn color="blue darken-1" variant="text" @click="close">Cancel</v-btn>
                  <v-btn color="blue darken-1" variant="text" @click="saveNew">Save</v-btn>
                </v-card-actions>
              </v-card>
            </v-dialog>
            <v-dialog v-model="dialogEdit" max-width="800px">
              <v-card>
                <v-card-title><h5>Edit Product</h5></v-card-title>
                <v-card-text>
                  <v-form>
                    <v-col>
                      <v-row>
                        <v-text-field v-model="editedProduct.name" label="Name" type="text"></v-text-field>
                      </v-row>
                      <v-row>
                        <v-text-field v-model="editedProduct.description" label="Description"
                                      type="text"></v-text-field>
                      </v-row>
                      <v-row>
                        <v-text-field v-model="editedProduct.price" label="Price"
                                      type="text"></v-text-field>
                      </v-row>
                    </v-col>
                  </v-form>
                </v-card-text>
                <v-card-actions>
                  <v-spacer></v-spacer>
                  <v-btn color="blue darken-1" variant="text" @click="close">Cancel</v-btn>
                  <v-btn color="blue darken-1" variant="text" @click="saveEdited">Save</v-btn>
                </v-card-actions>
              </v-card>
            </v-dialog>
            <v-dialog v-model="dialogDetail" max-width="800px">
              <v-card>
                <v-tabs v-model="tab" bg-color="transparent">
                  <v-tab value="details">Product Detail</v-tab>
                  <v-tab value="stock">Stock</v-tab>
                </v-tabs>
                <v-card-text>
                  <v-window v-model="tab">
                    <v-window-item value="details">
                      <v-container>
                        <v-row>
                          <v-col>
                            <v-list>
                              <v-list-item>
                                <v-list-item-title>Product Name</v-list-item-title>
                                <v-list-item-subtitle>{{ selectedProduct.name }}</v-list-item-subtitle>
                              </v-list-item>
                              <v-list-item>
                                <v-list-item-title>Product Description</v-list-item-title>
                                <v-list-item-subtitle>{{ selectedProduct.description }}</v-list-item-subtitle>
                              </v-list-item>
                              <v-list-item>
                                <v-list-item-title>Product Price</v-list-item-title>
                                <v-list-item-subtitle>{{ selectedProduct.price }}</v-list-item-subtitle>
                              </v-list-item>
                            </v-list>
                          </v-col>
                        </v-row>
                      </v-container>
                    </v-window-item>
                    <v-window-item value="stock">
                      <v-data-table
                          :headers="stockHeaders"
                          :items="productStockLevels"
                          class="elevation-1">
                      </v-data-table>
                    </v-window-item>
                  </v-window>
                </v-card-text>
              </v-card>
            </v-dialog>
            <v-dialog v-model="dialogDelete" max-width="800px">
              <v-card>
                <v-card-title><h5>Delete Product</h5></v-card-title>
                <v-card-text>
                  <v-form>
                    <v-container>
                      <h3>Are you sure you want to delete this product?</h3>
                    </v-container>
                  </v-form>
                </v-card-text>
                <v-card-actions>
                  <v-spacer></v-spacer>
                  <v-btn variant="text" @click="close">Cancel</v-btn>
                  <v-btn variant="text" @click="deleteConfirm">OK</v-btn>
                </v-card-actions>
              </v-card>
            </v-dialog>
          </v-toolbar>
        </template>
        <template v-slot:[`item.actions`]="{ item }">
          <v-icon @click="seeDetails(item)">$info</v-icon>
          <v-icon @click="editProduct(item)">$edit</v-icon>
          <v-icon @click="deleteProduct(item)">$delete</v-icon>
        </template>
        <template v-slot:no-data>
          <v-btn color="secondary" @click="initialize">Reset</v-btn>
        </template>
      </v-data-table>
    </v-card>
  </v-container>
</template>

<script>
import {Product} from "@/models/Product";
import dataFilter from "@/components/DataFilterComponent.vue";

export default {
  name: "ProductsComponent",
  inject: ['productsService', 'inventoryService'],
  components: {
    dataFilter
  },
  data() {
    return {
      headers: [
        {title: "ID", value: "id"},
        {title: "Name", value: "name"},
        {title: "Description", value: "description"},
        {title: "Price", value: "price"},
        {title: "Actions", value: "actions", sortable: false},
      ],
      stockHeaders: [
        {title: "Warehouse Name", value: "warehouseName"},
        {title: "Stock", value: "stockLevel"}
      ],

      productStockLevels: [],
      tab: "",
      search: "",
      products: [],
      categories: [],
      itemsPerPage: 10,

      dialogNew: false,
      dialogEdit: false,
      dialogDetail: false,
      dialogDelete: false,

      editedProduct: new Product(),
      defaultProduct: new Product(),
      selectedProduct: new Product()
    };
  },

  async mounted() {
    await this.initialize();
  },

  watch: {
    dialogDelete(val) {
      val || this.close();
    },
    dialogEdit(val) {
      val || this.close();
    },
    dialogNew(val) {
      val || this.close();
    },
    dialogDetail(val) {
      val || this.close();
    },
  },

  methods: {
    async initialize() {
      await this.getProducts();
      await this.getCategories();
    },

    async getProducts() {
      this.products = await this.productsService.asyncFindAll();
    },

    async getCategories() {
      this.categories = await this.productsService.asyncFindAllCategories();
    },

    async saveNew() {
      await this.productsService.asyncAdd(this.editedProduct);
      this.close();
      await this.getProducts();
    },

    async deleteConfirm() {
      await this.productsService.asyncDeleteById(this.selectedProduct.id);
      this.close();
      await this.getProducts();
    },

    async saveEdited() {
      if (!this.editedProduct.equals(this.defaultProduct)) {
        await this.productsService.asyncUpdateById(this.editedProduct.id, this.editedProduct);
        this.close();
        await this.getProducts();
        return;
      }
      this.close();
    },

    async fetchProductStockLevels(productId) {
      const stockData = await this.inventoryService.asyncFindStockByProductId(productId);
      this.productStockLevels = Object.entries(stockData).map(([warehouseName, stockLevel]) => ({
        warehouseName,
        stockLevel
      }));
    },

    close() {
      this.assignSelectedProduct(new Product())
      this.dialogNew = false;
      this.dialogEdit = false;
      this.dialogDetail = false;
      this.dialogDelete = false;
    },

    editProduct(product) {
      this.assignSelectedProduct(product);
      this.dialogEdit = true;
    },

    deleteProduct(product) {
      this.assignSelectedProduct(product);
      this.dialogDelete = true;
    },

    async seeDetails(product) {
      this.assignSelectedProduct(product);
      await this.fetchProductStockLevels(product.id);
      this.dialogDetail = true;
    },

    assignSelectedProduct(product) {
      this.editedProduct = Object.assign(new Product(), product);
      this.defaultProduct = Object.assign(new Product(), product);
      this.selectedProduct = Object.assign(new Product(), product);
    },
  }
}
</script>

<style scoped>

</style>