<template>
  <v-dialog v-model="isOpen" :max-width="maxWidth">
    <v-card>
      <v-card-text>
        <v-row>
          <v-col cols="10"/>
          <v-col cols="2">
            <v-btn @click="closeDialog" icon="$close" color="secondary" class="float-right"/>
          </v-col>
        </v-row>
        <v-container>
          <template v-if="title === 'Details'">
            <v-tabs v-model="detailTabsTitle">
              <v-tab value="Details" text="Details"/>
              <v-tab v-for="tab in detailTabs" :key="tab.title" :value="tab.title" :text="tab.title"/>
            </v-tabs>
            <v-window v-model="detailTabsTitle">
              <v-window-item value="Details">
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
        </v-container>
      </v-card-text>
    </v-card>
  </v-dialog>
</template>
<script>

import ItemForm from "@/components/base/ItemForm.vue";
import TeamProjectsTable from "@/components/tables/TeamProjectsTable.vue";
import ProductStockLevelsTable from "@/components/tables/ProductStockLevelsTable.vue";
import ProjectMaterialsTable from "@/components/tables/ProjectMaterialsTable.vue";

export default {
  name: 'FormDialog',
  components: {
    ItemForm,
    TeamProjectsTable,
    ProductStockLevelsTable,
    ProjectMaterialsTable
  },
  props: {
    open: {
      type: Boolean,
      required: true
    },
    item:{
      type: Object,
      required: true
    },
    title:{
      type: String,
      required: true
    },
    itemFields: {
      type: Array,
      required: true
    },
    detailTabs: {
      type: Array,
      required: false
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