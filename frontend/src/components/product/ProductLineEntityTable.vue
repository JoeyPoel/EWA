<template>
    <entity-data-table :dialog-config="dialogConfig" :filter-config="filterConfig" :table-config="tableConfig"
                       title="Products" @delete="handleDelete" @save="handleSave"
                       @update-tableConfig="tableConfig = $event" @update-dialogConfig="dialogConfig = $event"
                       @update-filterConfig="filterConfig = $event"/>
</template>

<script>
import EntityDataTable from "@/components/base/EntityDataTable.vue";

export default {
  name: "ProductLineEntityTable",
  components: {EntityDataTable},
  inject: ['productsService'],
  props:{
    productLines: {
      type: Array,
      required: true
    }
  },
  data(){
    return {
      products: [],
      tableConfig: {
        entityName: 'Product',
        headers: [
          {title: 'Name', value: 'name', key: 'name'},
          {title: 'Quantity', value: 'price', key: 'price'},
          {title: 'Actions', value: 'actions', sortable: false}
        ],
        items: this.products,
        itemsPerPage: 10,
        searchTerm: '',
        actions: [
          {action: 'Edit', icon: '$edit', color: 'primary'},
        ],
        canAdd: true,
      },
      dialogConfig: {
        open: false,
        title: '',
        item: {},
        baseObject: {},
        itemFields: [
          {name: 'product', label: 'Product', type: 'select', required: true},
          {name: 'quantity', label: 'Quantity', type: 'number', required: true}
        ],
        detailTabs:[
        ]

      },
      filterConfig: {
        canSearch: false,
        canSortByWarehouse: false,
        canSortByTeam: false,
        selectedWarehouse: null
      }
    }
  },
  methods: {
    handleDelete(item) {
      this.$emit('update:modelValue', this.productLines.filter(productLine => productLine.id !== item.id));
    },
    handleSave(item) {
      if (item.transactionId) {
        this.$emit('update:modelValue',
            this.productLines.map(productLine => productLine.transactionId === item.transactionId ? item : productLine));
      } else {
        this.$emit('update:modelValue', [...this.productLines, item]);
      }
    },
  },
}
</script>