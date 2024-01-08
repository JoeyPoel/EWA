<template>
  <entity-data-table :dialog-config="dialogConfig" :filter-config="filterConfig" :table-config="tableConfig"
                     title="Products" @delete="handleDelete" @save="handleSave"
                     @update-tableConfig="tableConfig = $event" @update-dialogConfig="dialogConfig = $event"
                     @update-filterConfig="filterConfig = $event"/>
</template>

<script>
import EntityDataTable from "@/components/base/EntityDataTable.vue";
import {Product} from "@/models/Product";

export default {
  name: "ProductsV2",
  inject: ['productsService'],
  components: {EntityDataTable},
  data() {
    return {
      products: [],
      categories: [],
      tableConfig: {
        entityName: 'Product',
        headers: [
          {title: "ID", value: "id", key: "id"},
          {title: 'Name', value: 'name', key: 'name'},
          {title: 'Description', value: 'description', key: 'description'},
          {title: 'Price', value: 'price', key: 'price'},
          {title: 'Actions', value: 'actions', sortable: false}
        ],
        items: this.products,
        itemsPerPage: 10,
        searchTerm: '',
        actions: [
          {action: 'Details', icon: '$info', color: 'primary'},
          {action: 'Edit', icon: '$edit', color: 'primary'},
        ],
        canAdd: true,
      },
      dialogConfig: {
        open: false,
        title: '',
        item: {},
        baseObject: new Product(),
        itemFields: [
          {name: 'name', label: 'Name', type: 'text', required: true},
          {name: 'description', label: 'Description', type: 'text', required: false},
          {name: 'price', label: 'Price', type: 'number', required: true},
          {name: 'categoryId', label: 'Category', type: 'select', required: true, items: []}
        ],
        detailTabs: [
          {title: 'Stock', component: 'ProductStockLevelsTable'},
        ]
      },
      filterConfig: {
        canSearch: true,
        canSortByWarehouse: false,
        canSortByTeam: false,
        selectedWarehouse: null
      }
    }
  },
  async mounted() {
    await this.initialize();
  },
  methods: {
    async initialize() {
      await this.fetchProducts();
      await this.fetchCategories();
      this.dialogConfig.itemFields.find(field => field.name === 'categoryId').items = this.categories;
    },

    async fetchProducts() {
      this.products = await this.productsService.asyncFindAll();
      this.products.forEach(product => {
        product.categoryName = this.getCategoryNameById(product.categoryId);
      });
      this.tableConfig.items = this.products;
    },

    async fetchCategories() {
      let data = await this.productsService.asyncFindAllCategories();
      this.categories = data.map(category => {
        return {id: category.id, name: category.name}
      })
    },
    async handleSave(item) {
      const savedItem = item.id ?
          await this.productsService.asyncUpdateById(item.id, item) :
          await this.productsService.asyncAdd(item);
      if (savedItem) {
        this.dialogConfig.open = false;
      } else {
        console.error("Failed to save item");
      }
      await this.initialize();
    },
    async handleDelete(item) {
      const deletedItem = await this.productsService.asyncDeleteById(item.id);
      if (deletedItem) {
        this.dialogConfig.open = false;
      } else {
        console.error("Failed to delete item");
      }
      await this.initialize();
    },
    getCategoryNameById(categoryId) {
      const category = this.categories.find(cat => cat.id === categoryId);
      return category ? category.name : 'Unknown';
    }
  }
}
</script>

<style scoped>

</style>