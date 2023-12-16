<template>
  <v-dialog v-model="openDialog" :max-width="maxWidth">
    <v-card>
      <v-card-title>
        <span class="headline">{{ dialogType }}</span>
        <v-spacer></v-spacer>
        <v-btn icon @click="closeDialog">
          <v-icon>mdi-close</v-icon>
        </v-btn>
      </v-card-title>
      <v-card-text>
        <v-container>
          <template v-if="dialogType === 'Details'">
            <v-tabs v-model="detailTabsTitle">
              <v-tab value="Details" title="Details"/>
              <div v-for="(tab, index) in detailTabs" :key="index">
                <v-tab :value="tab.name" :title="tab.title"/>
              </div>
            </v-tabs>
            <v-window v-model="detailTabsTitle">
              <v-window-item value="Details">
                <base-item-form :item-fields="itemFields" :item="item"/>
              </v-window-item>
              <div v-for="(tab, index) in detailTabs" :key="index">
                <v-window-item :value="tab.title">
                  <component :is="tab.component" :item="item"/>
                </v-window-item>
              </div>
            </v-window>
          </template>
          <template v-if="dialogType === 'New' || dialogType === 'Edit'">
            <base-item-form :item-fields="itemFields" :item="item"/>
          </template>
          <template v-else-if="dialogType === 'Delete'">
            <span>Are you sure you want to delete this item?</span>
          </template>
        </v-container>
      </v-card-text>
    </v-card>
  </v-dialog>
</template>
<script>

import BaseItemForm from "@/components/base/BaseItemForm.vue";

export default {
  name: 'BaseFormDialog',
  components: {
    BaseItemForm
    // ItemForm,
  },
  props: {
    dialog: {
      type: Object,
      required: true
    },
    maxWidth: {
      type: String,
      default: '800px'
    },
    item: {
      type: Object,
      required: true
    },
  },
  data() {
    return {
    }
  },
  computed: {
    itemFields() {
      return this.dialog.itemFields;
    },
    detailTabs() {
      return this.dialog.detailTabs;
    },
    dialogType() {
      return this.dialog.type;
    },
    openDialog() {
      return this.dialog.open;
    },
    detailTabsTitle: {
      get() {
        return this.detailTabs[0].name;
      },
      set(val) {
        this.detailTabsTitle = val;
      }
    }
  },
  methods: {
    closeDialog() {
      this.$emit('dialog-closed');
    },
    // handleSave(item) {
    //   this.$emit('save-item', item);
    // }
  }
}
</script>

<style scoped>

</style>