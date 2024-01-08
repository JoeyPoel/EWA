<template>
  <v-container>
    <v-data-table
        :headers="warehouseTransactionHeaders"
        :items="warehouseTransactions"
        :items-per-page-options="detailTableItemsPerPageOptions"
        :sort-by="['date']"
        class="elevation-1">
      <template v-slot:item="{ item }">
        <tr>
          <td>{{ item.id }}</td>
          <td>{{ item.transactionDate }}</td>
          <td>{{ item.productId }}</td>
          <td>{{ item.quantity }}</td>
          <td>
            <v-chip :color="item.color" small>
              {{ item.transactionType }}
            </v-chip>
          </td>
        </tr>
      </template>
    </v-data-table>
  </v-container>
</template>

<script>
import {Project} from "@/models/Project";
import {Transaction} from "@/models/Transaction";

export default {
  name: "WarehouseTransactionsTable",
  computed: {
    Transaction() {
      return Transaction
    },
    Project() {
      return Project
    }
  },
  inject: ['transactionsService'],
  props: {
    item: {
      type: Object,
      required: true
    },
  },
  data(){
    return {
      warehouseTransactionHeaders: [
        {title: "ID", value: "id"},
        {title: "Date", value: "transactionDate"},
        {title: "Product ID", value: "productId"},
        {title: "Quantity", value: "quantity"},
        {title: "Transaction Type", value: "transactionType"},
      ],
      warehouseTransactions: [],
      detailTableItemsPerPageOptions: [5, 10, 15],
    }
  },
  async mounted() {
    await this.loadWarehouseTransactions();
  },
  methods: {
    async loadWarehouseTransactions() {
      this.warehouseTransactions = await this.transactionsService.asyncFindAllByWarehouseId(this.item.id);
      for (const transaction of this.warehouseTransactions) {
        transaction.transactionDate =
        transaction.transactionType = Transaction.CATEGORY[transaction.transactionType] || transaction.transactionType;
        transaction.color = Transaction.getTransactionFlowColor(transaction, this.item.warehouseId);
      }
    },
  }
}
</script>

<style scoped>

</style>