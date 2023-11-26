<template>
  <v-container fluid>
    <base-card class="mt-1" title="Inventory" color="secondary">
      <v-data-table-server
          v-model:items-per-page="itemsPerPage"
          :headers="headers"
          :items="serverItems"
          :items-length="totalItems"
          :loading="loading"
          :search="search"
          :server-items-length="totalItems"
          item-value="name"
          @update:options="loadTableData">
        <template v-slot:top>
          <v-toolbar flat>
            <v-dialog v-model="dialogNewOrder" max-width="800px">
              <template #activator="{ props }">
                <v-btn class="mb-2" color="secondary" dark v-bind="props"
                       @click="formTitle = 'Add Product Order'">
                  Add inventory order
                </v-btn>
              </template>
              <v-card>
                <v-card-title>
                  <span class="text-h5"> New Product Order</span>
                </v-card-title>
                <v-card-text>
                  <!--                  Add rules-->
                  <v-container>-->
                    <v-col>
                      <v-row>
                        <v-select v-model="order.warehouse" :items="warehouses" item-text="name" item-value="id"
                                  label="Warehouse">
                        </v-select>
                      </v-row>
                      <v-row>
                        <v-col cols="5">
                          <v-text-field v-model="order.orderDate" label="Order date" type="date"></v-text-field>
                        </v-col>
                        <v-col cols="5">
                          <v-text-field v-model="order.deliveryDate" label="Delivery date" type="date">
                          </v-text-field>
                        </v-col>
                        <v-col cols="2">
                          <v-checkbox v-model="order.isDelivered" label="Delivered"></v-checkbox>
                        </v-col>
                      </v-row>
                      <v-row>
                        <v-col cols="12">
                          <v-select v-model="order.products" :items="products" chips item-text="name"
                                    item-value="id" label="Products" multiple></v-select>
                        </v-col>
                      </v-row>
                    </v-col>
                  </v-container>
                </v-card-text>
                <v-card-actions>
                  <v-spacer></v-spacer>
                  <v-btn color="blue-darken-1" variant="text" @click="closeNew">Cancel</v-btn>
                  <v-btn color="blue-darken-1" variant="text" @click="save">Save</v-btn>
                </v-card-actions>
              </v-card>
            </v-dialog>
            <v-dialog v-model="dialogNewTransaction" max-width="800px">
              <template #activator="{ props }">
                <v-btn class="mb-2" color="secondary" dark v-bind="props"
                       @click="formTitle = 'Add Product Transaction'">
                  Add Product Transaction
                </v-btn>
              </template>
              <v-card>
                <v-card-title>
                  <span class="text-h5">Product Transaction</span>
                </v-card-title>
                <v-card-text>
                  <!--                  Add rules-->
                  <v-container>
                    <v-col>
                      <v-row>
                        <v-col cols="6">
                          <v-select v-model="transaction.category" :items="Transaction.CATEGORIES" item-text="text"
                                    item-value="value" label="Product">
                          </v-select>
                        </v-col>
                        <v-col cols="6">
                          <v-select v-model="transaction.warehouseId" :items="warehouses" item-text="name"
                                    item-value="id"
                                    label="Warehouse">
                          </v-select>
                        </v-col>
                      </v-row>
                      <v-row>
                        <v-col cols="6">
                          <v-text-field v-model="transaction.quantity" label="Quantity" type="number"></v-text-field>
                        </v-col>
                        <v-col cols="6">
                          <v-select v-model="transaction.product" :items="products" item-text="name" item-value="id"
                                    label="Product">
                          </v-select>
                        </v-col>
                      </v-row>
                      <v-row>
                        <v-textarea v-model="transaction.description" label="Order description"></v-textarea>
                      </v-row>
                    </v-col>
                  </v-container>
                </v-card-text>
                <v-card-actions>
                  <v-spacer></v-spacer>
                  <v-btn color="blue-darken-1" variant="text" @click="closeNew">Cancel</v-btn>
                  <v-btn color="blue-darken-1" variant="text" @click="save">Save</v-btn>
                </v-card-actions>
              </v-card>
            </v-dialog>
            <v-dialog v-model="dialogDetails" max-width="800px">
              <v-card title="Product Transactions">
                <template v-slot:text>
                  <v-data-table-server
                      v-model:items-per-page="transactionsPerPage"
                      :headers="transactionsHeaders"
                      :items="transactions"
                      :items-length="totalTransactions"
                      :loading="transactionsLoading"
                      :search="transactionsSearch"
                      item-value="id"
                      @update:options="loadTransactionsData">
                  </v-data-table-server>
                </template>
                <v-card-actions>
                  <v-spacer></v-spacer>
                  <v-btn color="blue-darken-1" variant="text" @click="closeDetails">Close</v-btn>
                </v-card-actions>
              </v-card>
            </v-dialog>
          </v-toolbar>
        </template>
        <!--    TODO : Make icons lighter unless hovered over-->
        <template v-slot:[`item.actions`]="{ item }">
          <v-icon class="me-2" size="small" @click="seeDetails(item)"> $info</v-icon>
          <v-icon class="me-2" size="small" @click="addSelectedProductTransaction(item)"> $edit</v-icon>
          <v-icon size="small" @click="deleteItem(item)"> $delete</v-icon>
        </template>
        <template v-slot:no-data>
          <v-card>
            <v-card-title class="text-h5">No inventory items found</v-card-title>
          </v-card>
        </template>
      </v-data-table-server>
    </base-card>
  </v-container>
</template>

<script>
// import BaseTable from "@/components/base/BaseTable.vue";
import BaseCard from "@/components/base/BaseCard.vue";
import {PageSettings} from "@/models/PageSettings";
import {Product} from "@/models/Product";
import {Transaction} from "@/models/Transaction";
import {InventoryProductDTO} from "@/models/InventoryProductDTO";
import {Order} from "@/models/Order";

export default {
  name: "InventoryComponent",
  computed: {
    Transaction() {
      return Transaction
    },
    Product() {
      return Product
    }
  },
  inject: ['warehousesService', 'inventoryService', 'productsService', 'transactionsService'],

  data() {
    return {
      itemsPerPage: 10,
      headers: [
        {title: 'ID', key: 'productId'},
        {title: 'Product', key: 'name'},
        {title: 'Quantity', key: 'quantity'},
        {title: 'Price', key: 'price'},
        {title: 'Actions', key: 'actions', align: 'center', sortable: false}
      ],
      search: '',
      serverItems: [],
      loading: true,
      totalItems: 0,
      formTitle: '',
      dialogNew: false,
      dialogDelete: false,
      dialogDetails: false,
      dialogOrder: false,
      editedIndex: -1,
      transaction: new Transaction(),
      defaultItem: {},
      selectedDetailProduct: {},
      order: new Order(),

      products: [],
      warehouses: [],
      transactions: [],
      transactionsPerPage: 10,
      transactionsHeaders: [
        {title: 'Date', key: 'transactionDate'},
        {title: 'ID', key: 'id'},
        {title: 'Category', key: 'transactionCategory'},
        {title: 'Quantity', key: 'quantity'},
      ],
      transactionsSearch: '',
      totalTransactions: 0,
      transactionsLoading: true,
      dialogNewOrder: false,
      dialogNewTransaction: false,
    }
  },
  components: {
    BaseCard
  },

  async created() {
    this.products = await this.productsService.asyncFindAll();
    this.warehouses = await this.warehousesService.asyncGetAllWarehouses();
  },

  async mounted() {
    this.products = await this.productsService.asyncFindAll();
    this.warehouses = await this.warehousesService.asyncGetAllWarehouses();
  },
  watch: {
    dialogNew(val) {
      val || this.closeNew()
    },
    dialogDelete(val) {
      val || this.closeDelete()
    },
  },

  methods: {
    async loadTableData({page, itemsPerPage, search}) {
      this.loading = true;
      const serverData = await this.inventoryService
          .asyncFindAllPaginated(new PageSettings(page, itemsPerPage, search));
      if (serverData === undefined) return;
      this.serverItems = serverData.content;
      this.totalItems = serverData.totalElements;
      this.loading = false;
    },

    async loadTransactionsData({page, itemsPerPage, search}) {
      this.transactionsLoading = true;
      const serverData = await this.transactionsService
          .asyncFindAllTransactionsByProductIdPaginated(
              this.selectedDetailProduct.productId, new PageSettings(page, itemsPerPage, search));
      if (serverData === undefined) return;
      this.transactions = serverData.content.map(transaction => {
        return {
          id: transaction.id,
          transactionCategory: transaction.transactionCategory,
          quantity: transaction.quantity,
          transactionDate: new Date(transaction.transactionDate).toLocaleDateString()
        }
      })
      this.totalTransactions = serverData.totalElements;
      this.transactionsLoading = false;
    },

    handleFilterChange() {
      //   TODO: Handle filter change
      console.log('Filter changed. Still need to implement this.')
      console.log(this.tableFilter)
    },

    seeDetails(item) {
      this.selectedDetailProduct = Object.assign(new InventoryProductDTO(), item)
      this.editedIndex = this.serverItems.indexOf(item)
      this.dialogDetails = true
    },

    addSelectedProductTransaction(item) {
      this.formTitle = `Add product transaction`;
      this.dialogOrder = true
      this.editedIndex = this.serverItems.indexOf(item)
      this.transaction = Object.assign({}, item)
      this.dialogNew = true
    },

    deleteItem(item) {
      this.formTitle = `Delete ${this.modelName}`;
      this.editedIndex = this.serverItems.indexOf(item)
      this.transaction = {}
      this.dialogDelete = true
    },

    deleteItemConfirm() {
      this.$emit('delete-item', this.transaction)
      // this.tableItems.splice(this.editedIndex, 1)
      this.closeDelete()
    },

    closeDetails() {
      this.transaction = {}
      this.dialogDetails = false
    },

    closeNew() {
      this.$nextTick(() => {
        this.transaction = Object.assign({}, this.defaultItem)
        this.editedIndex = -1
      })
      this.dialogNew = false
    },

    closeDelete() {
      this.$nextTick(() => {
        this.transaction = Object.assign({}, this.defaultItem)
        this.editedIndex = -1
      })
    },

    save() {
      if (this.editedIndex > -1) {
        this.$emit('edit-item', this.transaction)
        Object.assign(this.serverItems[this.editedIndex], this.transaction)
      } else {
        this.$emit('add-item', this.transaction)
        // this.tableItems.push(this.editedItem)
      }
      this.closeNew()
    }
  },
}
</script>
