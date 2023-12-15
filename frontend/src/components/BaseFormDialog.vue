<template>
  <v-dialog v-model="open" :max-width="maxWidth">
    <v-card>
      <v-card-title>
        <span class="headline">{{ dialog.type }}</span>
        <v-spacer></v-spacer>
        <v-btn icon @click="closeDialog">
          <v-icon>mdi-close</v-icon>
        </v-btn>
      </v-card-title>
      <v-card-text>
        <v-container>
          <template v-if="dialog.type === 'details'">
            <v-tabs v-model="detailTabsTitle">
              <v-tab value="details" title="Details"/>
              <div v-for="(tab, index) in dialog.detailTabs" :key="index">
                <v-tab :value="tab.name" :title="tab.title"/>
              </div>
            </v-tabs>
            <v-window v-model="detailTabsTitle">
              <v-window-item value="details">
                <base-item-form :item-fields="dialog.itemFields" :item="item"/>
              </v-window-item>
              <div v-for="(tab, index) in dialog.detailTabs" :key="index">
                <v-window-item :value="tab.title">
                  <component :is="tab.component" :item="item"/>
                </v-window-item>
              </div>
            </v-window>
          </template>
          <template v-if="dialog.type === 'new' || dialog.type === 'edit'">
            <base-item-form :item-fields="itemFields" :item="item"/>
          </template>
          <template v-else-if="dialog.type === 'delete'">
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
      detailTabsTitle: 'Details',
      open: false,
    }
  },
  watch: {
    dialog: {
      if (dialog){
        this.open = dialog.open;
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