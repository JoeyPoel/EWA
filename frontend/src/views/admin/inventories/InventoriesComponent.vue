<template>
  <v-container fluid>
    <base-card class="mt-1" color="secondary" title="Inventories">
      <v-row>
        <v-col>
          <v-text-field v-model="search" label="Search product" prepend-inner-icon="$search" variant="outlined">
          </v-text-field>
        </v-col>
        <v-col>
          <v-select v-model="selectedWarehouse" :items="warehouses" label="Warehouse" variant="outlined"
                    @change="loadTableData">
            <template #prepend-inner>
              <v-icon color="grey"> $warehouse</v-icon>
            </template>
            <template v-slot:prepend-item>
              <v-list-item title="All warehouses" @click="selectedWarehouse = null; loadTableData()">
              </v-list-item>
            </template>
          </v-select>
        </v-col>
      </v-row>
      <v-data-table
          v-model:items-per-page="itemsPerPage"
          :headers="headers"
          :items="serverItems"
          :search="search"
          class="elevation-1"
          item-value="name">
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
                  <v-container>
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
                  <v-container>
                    <v-col>
                      <v-row>
                        <v-col>
                          <v-select v-model="transaction.transactionType" :items="transActionCategories"
                                    label="Transaction category">
                          </v-select>
                        </v-col>
                        <v-col v-if="transaction.transactionType === 'TRANSFER' ">
                          <v-select v-model="transaction.transferFromWarehouseId" :items="warehouses"
                                    label="Transfer from">
                          </v-select>
                        </v-col>
                        <v-col>
                          <v-select v-model="transaction.warehouseId" :items="warehouses"
                                    :label="transaction.transactionType === 'TRANSFER' ? 'Transfer to' : 'Warehouse'">
                          </v-select>
                        </v-col>
                      </v-row>
                      <v-row>
                        <v-col cols="6">
                          <v-text-field v-model="transaction.quantity" label="Quantity" type="number"></v-text-field>
                        </v-col>
                        <v-col cols="6">
                          <v-select v-model="transaction.productId" :items="products" item-text="name" item-value="id"
                                    label="Product">
                          </v-select>
                        </v-col>
                      </v-row>
                      <v-row>
                        <v-textarea v-model="transaction.description" label="Transaction description"></v-textarea>
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
                  <v-data-table
                      v-model:items-per-page="transactionsPerPage"
                      :headers="transactionsHeaders"
                      :items="transactions"
                      :loading="transactionsLoading"
                      :sort-by="[{key: 'transactionDate', order: 'desc'}]"
                      item-value="id">
                    <template v-slot:[`item.transactionCategory`]="{ value }">
                      <v-chip :color="getColor(value)">
                        {{ value }}
                      </v-chip>
                    </template>
                  </v-data-table>
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
          <!--          <v-icon size="small" @click="deleteItem(item)"> $delete</v-icon>-->
        </template>
        <template v-slot:no-data>
          <v-card>
            <v-card-title class="text-h5">No inventory items found</v-card-title>
          </v-card>
        </template>
      </v-data-table>
    </base-card>
  </v-container>
</template>

<script>
import BaseCard from "@/components/base/BaseCard.vue";
import {Transaction} from "@/models/Transaction";
import {Order} from "@/models/Order";

export default {
  name: "InventoryComponent",
  computed: {
    Transaction() {
      return Transaction
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

      editedIndex: -1,
      transaction: new Transaction(),
      defaultItem: {},

      selectedWarehouse: null,
      selectedDetailProduct: {},
      order: new Order(),

      products: [],
      warehouses: [],
      transactions: [],
      transactionsPerPage: 10,
      transactionsHeaders: [
        {title: 'ID', key: 'id'},
        {title: 'Date', key: 'transactionDate'},
        {title: 'Category', key: 'transactionCategory'},
        {title: 'Quantity', key: 'quantity'},
      ],
      transactionsSearch: '',
      totalTransactions: 0,
      transactionsLoading: true,
      transActionCategories: [],

      dialogDetails: false,
      dialogNewOrder: false,
      dialogNewTransaction: false
    }
  },
  components: {
    BaseCard
  },

  async mounted() {
    await this.loadTableData()
  },

  watch: {
    dialogDetails(val) {
      val || this.closeDetails()
    },
    dialogNewOrder(val) {
      val || this.closeNew()
    },
    dialogNewTransaction(val) {
      val || this.closeNew()
    },
    selectedDetailProduct(val) {
      val && this.loadTransactionsData()
    },
    selectedWarehouse(val) {
      val && this.loadTableData()
    }
  },

  methods: {
    async loadTableData() {
      this.loading = true;
      console.log(this.selectedWarehouse)
      this.serverItems = this.selectedWarehouse ?
          await this.inventoryService.asyncGetAllByWarehouseId(this.selectedWarehouse) :
          await this.inventoryService.asyncGetAll();

      this.transActionCategories = Transaction.getCategories;

      this.products = await this.productsService.asyncGetAll().then(products => {
        return products.map(product => {
          return {
            title: product.name,
            value: product.id
          }
        })
      })

      this.warehouses = await this.warehousesService.asyncGetAll().then(warehouses => {
        return warehouses.map(warehouse => {
          return {
            title: warehouse.name,
            value: warehouse.id
          }
        })
      })

      this.loading = false;
    },

    async loadTransactionsData(productId) {
      this.transactionsLoading = true;
      const serverData = await this.transactionsService.asyncGetAllByProductId(
          productId)

      // TODO: Make a Model for this and map the data to it.
      this.transactions = serverData.map(transaction => {
        return {
          id: transaction.id,
          transactionCategory: Transaction.CATEGORY[transaction.transactionType],
          quantity: transaction.quantity,
          transactionDate: new Date(transaction.transactionDate).toLocaleDateString()
        }
      })
      this.transactionsLoading = false;
    },

    getColor(category) {
      return Transaction.getTransactionFlow(category, this.selectedWarehouse.id) === 'IN' ? 'green' : 'red';
    },


    async seeDetails(item) {
      // this.selectedDetailProduct = Object.assign(new InventoryProduct(), item)
      this.editedIndex = this.serverItems.indexOf(item)
      this.dialogDetails = true
      await this.loadTransactionsData(item.productId)
    },

    addSelectedProductTransaction(item) {
      this.editedIndex = this.serverItems.indexOf(item)

      this.transaction = new Transaction();
      this.transaction.productId = item.productId;
      if (this.selectedWarehouse !== {}) {
        this.transaction.warehouseId = this.selectedWarehouse.id;
      }

      this.dialogNewTransaction = true
    },

    closeDetails() {
      this.transaction = new Transaction();
      this.dialogDetails = false
    },

    closeNew() {
      this.$nextTick(() => {
        this.transaction = Object.assign({}, this.defaultItem)
        this.editedIndex = -1
      })
      this.dialogNew = false
    },

    save() {
      if (this.editedIndex > -1) {
        this.$emit('edit-item', this.transaction)
        Object.assign(this.serverItems[this.editedIndex], this.transaction)
      } else {
        this.$emit('add-item', this.transaction)
        // this.tableItems.push(this.editedWarehouse)
      }
      this.closeNew()
    }
  },
}
</script>
