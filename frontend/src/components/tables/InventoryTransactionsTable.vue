<template>
  <v-data-table
      v-model:items-per-page="transactionsPerPage"
      :headers="transactionsHeaders"
      :items="transactions"
      :loading="transactionsLoading"
      :sort-by="[{key: 'transactionDate', order: 'desc'}]"
      density="compact"
      item-value="id"
  >
    <template v-slot:item="{ item }">
      <tr>
        <td>{{ item.id }}</td>
        <td>{{ item.transactionDate }}</td>
        <td>
          <v-chip :color="item.color" small text-color="white">{{ item.transactionType }}</v-chip>
        </td>
        <td>{{ item.quantity }}</td>
        <td>{{ item.orderId }}</td>
        <td>{{ item.projectName }}</td>
        <td>{{ item.fromWarehouseName }}</td>
      </tr>
    </template>

  </v-data-table>
</template>

<script>
import {Transaction} from "@/models/Transaction";

export default {
  name: "InventoryTransactionsTable",
  inject: ['transactionsService', 'warehousesService', 'projectsService'],
  props: {
    item: {
      type: Object,
      required: true,
    }
  },
  data() {
    return {
      transactions: [],
      projects: [],
      warehouses: [],
      transactionsPerPage: 10,
      transactionsHeaders: [
        {title: 'ID', key: 'id'},
        {title: 'Date', key: 'transactionDate'},
        {title: 'Type', value: 'transactionType', key: 'transactionType'},
        {title: 'Quantity', key: 'quantity'},
        {title: 'Order ID', key: 'orderId'},
        {title: 'Project', key: 'projectName'},
        {title: 'Received from', key: 'fromWarehouseName'},
      ],
      transactionsLoading: true,
    }
  },
  async created() {
    this.transactionsLoading = true;
    await this.initialize();
    await this.fetchProjects();
    await this.fetchWarehouses();
    this.transactionsLoading = false;
  },
  methods: {
    async initialize() {
      this.transactionsLoading = true;
      await this.fetchProjects();
      await this.fetchWarehouses();
      await this.fetchTransactions();
      this.transactionsLoading = false;
    },
    async fetchTransactions() {
      this.transactionsLoading = true;
      this.transactions = this.item.warehouseId ?
          await this.transactionsService.asyncFindAllByWarehouseId(this.item.warehouseId) :
          await this.transactionsService.asyncFindAllByProductId(this.item.productId);

      for (let i = 0; i < this.transactions.length; i++) {
        this.transactions[i].transactionType = Transaction.CATEGORY[this.transactions[i].transactionType] || this.transactions[i].transactionType
        this.transactions[i].transactionDate = new Date(this.transactions[i].transactionDate).toLocaleDateString()
        this.transactions[i].color = Transaction.getTransactionFlowColor(this.transactions[i], this.item.warehouseId)

        this.transactions[i].projectName = this.transactions[i].projectId ?
            this.projects.find(project => project.id === this.transactions[i].projectId).name :
            '';

        this.transactions[i].fromWarehouseName = this.transactions[i].transferFromWarehouseId ?
            this.warehouses.find(warehouse => warehouse.id === this.transactions[i].transferFromWarehouseId).name :
            '';
      }

      this.transactionsLoading = false;
    },
    async fetchProjects() {
      let data = await this.projectsService.asyncFindAll();
      this.projects = data.map(project => ({id: project.id, name: project.name}));
    },
    async fetchWarehouses() {
      let data = await this.warehousesService.asyncFindAll();
      this.warehouses = data.map(warehouse => ({id: warehouse.id, name: warehouse.name}));
    },

  },
}
</script>