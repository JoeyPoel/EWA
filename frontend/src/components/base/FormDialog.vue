<template>
  <v-dialog v-model="isOpen" :max-width="maxWidth">
    <v-card>
      <v-card-text>
        <v-row>
          <v-col cols="10">
            <h3 v-if="item && item.title" class="text-center">{{ item.title }}</h3>
          </v-col>
          <v-col cols="2">
            <v-btn @click="closeDialog" icon="$close" color="secondary" class="float-right"/>
          </v-col>
        </v-row>
        <v-container>
          <template v-if="title === 'Details'">
            <v-tabs v-model="detailTabsTitle">
              <v-tab value="Details" text="Details" v-if="hasGeneratedDetails"/>
              <v-tab v-for="tab in detailTabs" :key="tab.title" :value="tab.title" :text="tab.title"/>
            </v-tabs>
            <v-window v-model="detailTabsTitle">
              <v-window-item value="Details" v-if="hasGeneratedDetails">
                <item-form :item-fields="itemFields" :item="item" :allDisabled="true"/>
              </v-window-item>
              <v-window-item v-for="tab in detailTabs" :key="tab.title" :value="tab.title">
                <component :is="tab.component" :item="item"/>
              </v-window-item>
            </v-window>
          </template>
          <template v-if="title === 'New' || title === 'Edit'">
            <item-form :item-fields="itemFields" :item="item" :is-new-item="title === 'New'" @save="handleSave"
                       @delete="handleDelete"/>
          </template>
          <template v-if="title === 'Error'">
            <v-row  >
              <p class="ml-1" v-html="formattedMessage"></p>
            </v-row>
          </template>
        </v-container>
      </v-card-text>
    </v-card>
  </v-dialog>
</template>
<script>

import ItemForm from "@/components/base/ItemForm.vue";
import TeamProjectsTable from "@/components/team/TeamProjectsTable.vue";
import ProductStockLevelsTable from "@/components/product/ProductStockLevelsTable.vue";
import ProjectMaterialsTable from "@/components/project/ProjectMaterialsTable.vue";
import InventoryTransactionsTable from "@/components/inventory/InventoryTransactionsTable.vue";
import WarehouseStockLevelsTable from "@/components/warehouses/WarehouseStockLevelsTable.vue";
import WarehouseProjectsTable from "@/components/warehouses/WarehouseProjectsTable.vue";
import WarehouseTeamsTable from "@/components/warehouses/WarehouseTeamsTable.vue";
import WarehouseTransactionsTable from "@/components/warehouses/WarehouseTransactionsTable.vue";

export default {
  name: 'FormDialog',
  components: {
    ItemForm,
    TeamProjectsTable,
    ProductStockLevelsTable,
    ProjectMaterialsTable,
    InventoryTransactionsTable,
    WarehouseStockLevelsTable,
    WarehouseProjectsTable,
    WarehouseTeamsTable,
    WarehouseTransactionsTable
  },
  props: {
    open: {
      type: Boolean,
      required: true
    },
    item: {
      type: Object,
      required: false
    },
    title: {
      type: String,
      required: true
    },
    itemFields: {
      type: Array,
      required: false
    },
    detailTabs: {
      type: Array,
      required: false
    },
    hasGeneratedDetails: {
      type: Boolean,
      default: true
    },
    maxWidth: {
      type: String,
      default: '800px'
    },
  },
  data() {
    return {
      detailTabsTitle: 'Details',
      dialog: false,
    }
  },
  mounted() {
    this.dialog = this.open;
  },
  computed: {
    formattedMessage() {
      return this.item.message.replace(/\n/g, '<br>');
    },
    isOpen: {
      get() {
        return this.open;
      },
      set() {
       this.closeDialog();
      }
    }
  },
  methods: {
    closeDialog() {
      this.$emit('close');
    },
    handleSave(item) {
      this.$emit('save', item);
      this.closeDialog();
    },
    handleDelete(item) {
      this.$emit('delete', item);
    }
  }
}
</script>

<style scoped>

</style>