<template>
  <v-select
      :items="warehouses"
      item-title="name"
      item-value="id"
      variant="solo"
      label="Choose a warehouse"
      @change="warehouseSelected"
  ></v-select>

  <v-data-table :headers="headers" :items="products">
    <template v-slot:top>
      <v-toolbar flat>
        <v-toolbar-title>Products</v-toolbar-title>
        <v-divider class="mx-4" inset vertical></v-divider>
        <v-spacer></v-spacer>
        <v-dialog v-model="dialog" max-width="500px">
          <template>
            <v-select
                v-model="warehouse"
                :items="warehouses"
                dense
                item-text="name"
                item-value="id"
                label="Warehouse"
                outlined
            ></v-select>
          </template>
          <template #activator="{ props }">
            <v-btn class="mb-2" color="primary" dark v-bind="props">New Item</v-btn>
          </template>
          <v-card>
            <v-card-title>
              <span class="text-h5">{{ formTitle }}</span>
            </v-card-title>
            <v-card-text>
              <v-container>
                <v-row>
                  <v-col cols="12" md="4" sm="6">
                    <v-text-field v-model="editedItem.name" label="Dessert name"></v-text-field>
                  </v-col>
                  <v-col cols="12" md="4" sm="6">
                    <v-text-field v-model="editedItem.calories" label="Calories"></v-text-field>
                  </v-col>
                  <v-col cols="12" md="4" sm="6">
                    <v-text-field v-model="editedItem.fat" label="Fat (g)"></v-text-field>
                  </v-col>
                  <v-col cols="12" md="4" sm="6">
                    <v-text-field v-model="editedItem.carbs" label="Carbs (g)"></v-text-field>
                  </v-col>
                  <v-col cols="12" md="4" sm="6">
                    <v-text-field v-model="editedItem.protein" label="Protein (g)"></v-text-field>
                  </v-col>
                </v-row>
              </v-container>
            </v-card-text>

            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn color="blue-darken-1" variant="text" @click="close">Cancel</v-btn>
              <v-btn color="blue-darken-1" variant="text" @click="save">Save</v-btn>
            </v-card-actions>
          </v-card>
        </v-dialog>
        <v-dialog v-model="dialogDelete" max-width="500px">
          <v-card>
            <v-card-title class="text-h5">Are you sure you want to delete this item?</v-card-title>
            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn color="blue-darken-1" variant="text" @click="closeDelete">Cancel</v-btn>
              <v-btn color="blue-darken-1" variant="text" @click="deleteItemConfirm">OK</v-btn>
              <v-spacer></v-spacer>
            </v-card-actions>
          </v-card>
        </v-dialog>
      </v-toolbar>
    </template>

    <template v-slot:[`item.actions`]="{ item }">
      <v-icon class="me-2" size="small" @click="seeDetails(item)"> $info</v-icon>
      <v-icon class="me-2" size="small" @click="editItem(item)"> $edit</v-icon>
      <v-icon size="small" @click="deleteItem(item)"> $delete</v-icon>
    </template>

    <template v-slot:no-data>
      <v-btn color="primary" @click="initialize">Reset</v-btn>
    </template>
  </v-data-table>
</template>

<script>
import {InventoryProductDTO} from "@/models/InventoryProductDTO";

export default {
  data: () => ({
    dialog: false,
    dialogDelete: false,
    headers: [
      {title: 'ID', key: 'productId',},
      {title: 'Name', key: 'name'},
      {title: 'Price ', key: 'price'},
      {title: 'Quantity ', key: 'quantity'},
    ],
    items: [],
    desserts: [],
    editedIndex: -1,
    editedItem:  new InventoryProductDTO(),
    defaultItem: new InventoryProductDTO(),
    warehouses: [],
    products: [],
  }),

  inject: ['warehousesService', 'inventoryService'],

  computed: {
    formTitle() {
      return this.editedIndex === -1 ? 'New Item' : 'Edit Item'
    },
  },

  watch: {
    dialog(val) {
      val || this.close()
    },
    dialogDelete(val) {
      val || this.closeDelete()
    },
  },

  async created() {
    await this.initialize()
  },

  methods: {
    async initialize() {
      this.products = await this.inventoryService.asyncFindAllProductsHavingTransactions();
      console.log(this.products);
      this.warehouses = await this.warehousesService.asyncGetAllWarehouses();
      // this.desserts = [
      //   {
      //     name: 'Frozen Yogurt',
      //     calories: 159,
      //     fat: 6.0,
      //     carbs: 24,
      //     protein: 4.0,
      //   },
      //   {
      //     name: 'Ice cream sandwich',
      //     calories: 237,
      //     fat: 9.0,
      //     carbs: 37,
      //     protein: 4.3,
      //   },
      //   {
      //     name: 'Eclair',
      //     calories: 262,
      //     fat: 16.0,
      //     carbs: 23,
      //     protein: 6.0,
      //   },
      //   {
      //     name: 'Cupcake',
      //     calories: 305,
      //     fat: 3.7,
      //     carbs: 67,
      //     protein: 4.3,
      //   },
      //   {
      //     name: 'Gingerbread',
      //     calories: 356,
      //     fat: 16.0,
      //     carbs: 49,
      //     protein: 3.9,
      //   },
      //   {
      //     name: 'Jelly bean',
      //     calories: 375,
      //     fat: 0.0,
      //     carbs: 94,
      //     protein: 0.0,
      //   },
      //   {
      //     name: 'Lollipop',
      //     calories: 392,
      //     fat: 0.2,
      //     carbs: 98,
      //     protein: 0,
      //   },
      //   {
      //     name: 'Honeycomb',
      //     calories: 408,
      //     fat: 3.2,
      //     carbs: 87,
      //     protein: 6.5,
      //   },
      //   {
      //     name: 'Donut',
      //     calories: 452,
      //     fat: 25.0,
      //     carbs: 51,
      //     protein: 4.9,
      //   },
      //   {
      //     name: 'KitKat',
      //     calories: 518,
      //     fat: 26.0,
      //     carbs: 65,
      //     protein: 7,
      //   },
      // ]
    },

    editItem(item) {
      this.editedIndex = this.desserts.indexOf(item)
      this.editedItem = Object.assign({}, item)
      this.dialog = true
    },

    deleteItem(item) {
      this.editedIndex = this.desserts.indexOf(item)
      this.editedItem = Object.assign({}, item)
      this.dialogDelete = true
    },

    deleteItemConfirm() {
      this.desserts.splice(this.editedIndex, 1)
      this.closeDelete()
    },

    async warehouseSelected(warehouseId) {
      this.products = await this.inventoryService.asyncGetProductsByWarehouseId(warehouseId);
      console.log(this.products);
    },

    close() {
      this.dialog = false
      this.$nextTick(() => {
        this.editedItem = Object.assign({}, this.defaultItem)
        this.editedIndex = -1
      })
    },

    closeDelete() {
      this.dialogDelete = false
      this.$nextTick(() => {
        this.editedItem = Object.assign({}, this.defaultItem)
        this.editedIndex = -1
      })
    },

    save() {
      if (this.editedIndex > -1) {
        Object.assign(this.desserts[this.editedIndex], this.editedItem)
      } else {
        this.desserts.push(this.editedItem)
      }
      this.close()
    },
  },
}

// <div class="container border border-1 rounded-2 border-light-subtle">
//   <div class="container-fluid text-light mb-4 pt-4 text-dark">
//     <div class="row justify-content-center">
//       <div class="col-md-6 col-lg-4 mb-4">
//         <label class="form-label" for="selectWarehouse">Choose a warehouse:</label>
//         <select id="warehouse-select" v-model="selectedWarehouseId" class="form-control form-control-sm">
//           <option v-for="warehouse in warehouses" :key="warehouse.id" :value="warehouse.id">{{
//           warehouse.name
//         }}
//         </option>
//       </select>
//     </div>
//     <div class="col-md-6 col-lg-4 mb-4">
//       <label class="form-label" for="searchBar">Search for an item:</label>
//       <input id="searchBar" v-model="searchQuery" class="form-control form-control-sm" type="text"
//       @input="handleSearch">
//     </div>
//     <div class="col-md-6 col-lg-4 mb-4">
//       <label class="form-label" for="selectSorting">Choose a sorting system:</label>
//       <select id="selectSorting" v-model="selectedSorting" class="form-control form-control-sm">
//         <option value="id">Sort on ID</option>
//         <option value="reverse id">Reverse sort on ID</option>
//         <option value="name">Sort on name</option>
//         <option value="reverse name">Reverse sort on name</option>
//         <option value="stock">Sort on Stock level</option>
//         <option value="reverse stock">Reverse sort on Stock level</option>
//       </select>
//     </div>
//   </div>
// </div>
// <RouterView/>
// </div>


// inject: ['warehousesService', 'inventoryService'],
//     name: "WarehouseComponent"
//   data() {
//     return {
//       products: [],
//       warehouses: [],
//       filteredProducts: [],
//       selectedWarehouseId: null,
//       selectedSorting: "id",
//       searchQuery: "",
//     }
//   },
//   async created() {
//     this.warehouses = await this.warehousesService.asyncGetAllWarehouses();
//   },
//   watch: {
//     async $route() {
//       this.selectedWarehouseId = this.$route.params.id;
//     },
//     selectedWarehouseId: function () {
//       this.$router.push({name: 'WarehouseInventory', params: {id: this.selectedWarehouseId}});
//       this.searchQuery = "";
//       this.selectedSorting = "id"
//     },
//     selectedSorting: function () {
//       if (this.selectedSorting === "id") {
//         this.idSorting();
//       }
//       if (this.selectedSorting === "reverse id") {
//         this.idSorting();
//         this.filteredProducts.reverse();
//       }
//       if (this.selectedSorting === "name") {
//         this.nameSorting();
//       }
//       if (this.selectedSorting === "reverse name") {
//         this.nameSorting();
//         this.filteredProducts.reverse();
//       }
//       if (this.selectedSorting === "stock") {
//         this.stockLevelSorting();
//       }
//       if (this.selectedSorting === "reverse stock") {
//         this.stockLevelSorting();
//         this.filteredProducts.reverse();
//       }
//     },
//   },
//   methods: {
//     handleSearch() {
//       const searchQuery = this.searchQuery.toLowerCase().trim();
//       if (searchQuery !== "") { // If searchQuery is not null
//         const products = this.products.filter(product => product.warehouseId === this.selectedWarehouseId); // Only from this warehouse
//         this.filteredProducts = products.filter(product => product.name.toLowerCase().includes(searchQuery)); // Filter it on includes searchQuery
//       } else {
//         this.filteredProducts = this.products.filter(product => product.warehouseId === this.selectedWarehouseId); // Else return the normal product list of this warehouse
//       }
//     },
//     idSorting() {
//       this.filteredProducts.sort((a, b) => a.id - b.id);
//     },
//     nameSorting() {
//       this.filteredProducts.sort((a, b) => {
//         const nameA = a.name.toUpperCase();
//         const nameB = b.name.toUpperCase();
//         if (nameA < nameB) {
//           return -1;
//         }
//         if (nameA > nameB) {
//           return 1;
//         }
//         return 0;
//       });
//     },
//     stockLevelSorting() {
//       this.filteredProducts.sort((a, b) => a.quantity - b.quantity);
//     },
//   },
// }
</script>
