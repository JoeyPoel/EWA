<template>
  <v-dialog v-model="isOpen" :max-width="maxWidth">
    <v-card>
      <v-card-text>
        <v-row>
          <v-col cols="10">
          </v-col>
          <v-col cols="2">
            <v-btn icon @click="closeDialog">
              <v-icon>$close</v-icon>
            </v-btn>
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
                <base-item-form :item-fields="itemFields" :item="item" :allDisabled="true"/>
              </v-window-item>
              <v-window-item v-for="tab in detailTabs" :key="tab.title" :value="tab.title">
                <component :is="tab.component.toString()" :item="item"/>
              </v-window-item>
            </v-window>
          </template>
          <template v-if="title === 'New' || title === 'Edit'">
            <base-item-form :item-fields="itemFields" :item="item"/>
          </template>
        </v-container>
      </v-card-text>
    </v-card>
  </v-dialog>
</template>
<script>

import BaseItemForm from "@/components/base/BaseItemForm.vue";
import TeamProjectsTable from "@/components/team/TeamProjectsTable.vue";

export default {
  name: 'BaseFormDialog',
  components: {
    BaseItemForm,
    TeamProjectsTable,
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
      dialog: false,
      teamProjects: TeamProjectsTable,
    }
  },
  mounted() {
    console.log(`BaseFormDialog: open changed to ${this.open}`);
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
  }
}
</script>

<style scoped>

</style>