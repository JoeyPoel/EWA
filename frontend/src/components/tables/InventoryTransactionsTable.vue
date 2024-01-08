<template>
  <v-data-table
      v-model:items-per-page="transactionsPerPage"
      :headers="transactionsHeaders"
      :items="transactions"
      :loading="transactionsLoading"
      :sort-by="[{key: 'transactionDate', order: 'desc'}]"
      item-value="id">
    <template v-slot:[`item.transactionCategory`]="{ value }">
      <v-chip :color="value.color">
        {{ value }}
      </v-chip>
    </template>
  </v-data-table>
</template>

<script>
import {Transaction} from "@/models/Transaction";

export default {
  name: "InventoryTransactionsTable",
  computed: {
    Transaction() {
      return Transaction
    }
  },
  inject: ['transactionsService'],
  props: {
    item: {
      type: Object,
      required: true,
    }
  },
  data() {
    return {
      transactions: [],
      transactionsPerPage: 10,
      transactionsHeaders: [
        {title: 'ID', key: 'id'},
        {title: 'Date', key: 'transactionDate'},
        {title: 'Category', key: 'transactionCategory'},
        {title: 'Quantity', key: 'quantity'},
      ],
      transactionsLoading: true,
    }
  },
  async created() {
    this.transactionsLoading = true;
    await this.initialize();
    this.transactionsLoading = false;
  },
  methods: {
    async initialize() {
      this.transactionsLoading = true;
      const serverData = await this.transactionsService.asyncFindAllByProductId(
          this.item.productId)

      console.log(serverData)
      this.transactions = serverData.map(transaction => {
        console.log(transaction)
        return {
          id: transaction.id,
          transactionCategory: Transaction.CATEGORY[transaction.transactionType] || transaction.transactionType ||
              'Unknown',
          quantity: transaction.quantity,
          transactionDate: new Date(transaction.transactionDate).toLocaleDateString(),
          color: Transaction.getTransactionFlowColor(transaction, this.item.warehouseId)
        }
      })

      console.log(this.transactions)

      this.transactionsLoading = false;
      },

  },
}
</script>