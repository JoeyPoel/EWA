<template>
  <v-container fluid>
    <base-card class="mt-1" color="secondary" title="Products">
      <v-data-table
          v-model:items-per-page="itemsPerPage"
          :headers="headers"
          :items="products"
          :search="search"
          class="elevation-1"
          item-value="id"
      >
        <template v-slot:top>
          <v-toolbar flat>
            <v-dialog v-model="dialogNew" max-width="800px">
              <template v-slot:activator="{ props }">
                <v-text-field v-model="search" label="Search Warehouse" prepend-inner-icon="$search"
                              variant="outlined"/>
                <v-spacer></v-spacer>
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
                    <v-container>
                      <h1>In development</h1>
                    </v-container>
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
                        <h1>In development</h1>
                      </v-container>
                    </v-window-item>
                    <v-window-item value="stock">
                      <v-container>
                        <h1>In development</h1>
                      </v-container>
                    </v-window-item>
                  </v-window>
                </v-card-text>
                <v-card-actions>
                  <v-spacer></v-spacer>
                  <v-btn color="blue darken-1" variant="text" @click="close">Cancel</v-btn>
                </v-card-actions>
              </v-card>
            </v-dialog>
            <v-dialog v-model="dialogDelete" max-width="800px">
              <v-card>
                <v-card-title><h5>Delete Product</h5></v-card-title>
                <v-card-text>
                  <v-form>
                    <v-container>
                      <h1>In development</h1>
                    </v-container>
                  </v-form>
                </v-card-text>
                <v-card-actions>
                  <v-spacer></v-spacer>
                  <v-btn color="blue darken-1" variant="text" @click="close">Cancel</v-btn>
                  <v-btn color="blue darken-1" variant="text" @click="deleteConfirm">OK</v-btn>
                </v-card-actions>
              </v-card>
            </v-dialog>
          </v-toolbar>
        </template>
        <template v-slot:[`item.actions`]="{ product }">
          <v-icon class="me-2" size="small" @click="seeDetails(product)">$info</v-icon>
          <v-icon class="me-2" size="small" @click="editProduct(product)">$edit</v-icon>
          <v-icon size="small" @click="deleteProduct(product)">$delete</v-icon>
        </template>
        <template v-slot:no-data>
          <v-btn color="secondary" @click="initialize">Reset</v-btn>
        </template>
      </v-data-table>
    </base-card>
  </v-container>
</template>

<script>
import BaseCard from "@/components/base/BaseCard.vue";
import {Product} from "@/models/Product";

export default {
  name: "ProductsComponent",
  inject: ['productsService'],
  components: {
    BaseCard,
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
    };
  },

  async mounted() {
    await this.initialize();
    console.log(this.products)
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
      this.products = await this.productsService.asyncGetAll();
    },

    async getCategories() {
      this.categories = await this.productsService.asyncGetAllCategories();
    },

    async saveNew() {
      await this.productsService.asyncAdd(this.editedProduct);
      this.close();
      await this.getProducts();
    },

    deleteConfirm() {
      console.log('deleteConfirm -- Still in development');
      this.close();
    },

    saveEdited() {
      console.log('saveEdited -- Still in development');
      this.close();
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

    seeDetails(product) {
      this.assignSelectedProduct(product);
      this.dialogDetail = true;
    },

    assignSelectedProduct(product) {
      this.editedProduct = Object.assign(new Product(), product);
      this.defaultProduct = Object.assign(new Product(), product);
    },
  }
}
</script>

<style scoped>

</style>