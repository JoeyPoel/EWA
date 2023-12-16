<template>
  <v-dialog v-model="isOpen" :max-width="maxWidth">
    <v-card>
      <v-card-title>
        <v-row>
          <v-col cols="10">
            <h5>{{ title }}</h5>
          </v-col>
          <v-col cols="2">
            <v-btn icon @click="closeDialog">
              <v-icon>$close</v-icon>
            </v-btn>
          </v-col>
        </v-row>
      </v-card-title>
      <v-card-text>
        <v-container>
          <template v-if="title === 'Details'">
            <v-tabs v-model="detailTabsTitle">
              <v-tab value="Details" text="Details"/>
              <div v-for="(tab, index) in detailTabs" :key="index">
                <v-tab :value="tab.name" :title="tab.title"/>
              </div>
            </v-tabs>
            <v-window v-model="detailTabsTitle">
              <v-window-item value="Details">
                <base-item-form :item-fields="itemFields" :item="item" :allDisabled="true"/>
              </v-window-item>
              <div v-for="(tab, index) in detailTabs" :key="index">
                <v-window-item :value="tab.title">
                  <component :is="tab.component" :item="item"/>
                </v-window-item>
              </div>
            </v-window>
          </template>
          <template v-if="title === 'New' || title === 'Edit'">
            <base-item-form :item-fields="itemFields" :item="item"/>
          </template>
          <template v-else-if="title === 'Delete'">
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
    }
  },
  computed: {
    isOpen(){
      return this.open;
    },
  },
  methods: {
    closeDialog() {
      this.$emit('close');
    },
  }
}
</script>

<style scoped>

</style>