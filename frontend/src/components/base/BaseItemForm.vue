<template>
  <v-container>
    <v-form>
      <v-row>
        <ItemField v-for="field in itemFields" :key="field.name" :type="field.type" :label="field.label"
                   :rules="field.rules" :disabled="allDisabled || field.disabled" :fieldName="field.name"
                   :item="itemCopy" :items="field.items"
                   @input-change="handleInputChange($event)" />
      </v-row>
      <v-row v-if="!allDisabled">
        <v-col cols="12" sm="6">
          <v-btn color="primary" @click="clearForm" :block="true" rounded="sm">Clear</v-btn>
        </v-col>
        <v-col cols="12" sm="6">
          <v-btn :disabled="!changed" color="primary" @click="resetForm" :block="true" rounded="sm">Reset</v-btn>
        </v-col>
        <v-col cols="12" sm="6">
          <v-btn :disabled="!changed" color="primary" @click="saveForm" :block="true" rounded="sm">Save</v-btn>
        </v-col>
        <v-col cols="12" sm="6">
          <v-btn color="primary-darken-1" @click="deleteForm" :block="true" rounded="sm">Delete</v-btn>
        </v-col>
      </v-row>
    </v-form>
  </v-container>
</template>

<script>
import ItemField from '../ItemField.vue'

export default {
  name: "baseItemForm",
  components: {
    ItemField
  },
  props: {
    item: {
      type: Object,
      required: true
    },
    itemFields: {
      type: Array,
      required: true
    },
    allDisabled: {
      type: Boolean,
      required: false,
      default: false
    }
  },
  data() {
    return {
      itemCopy: Object.assign({}, this.item),
    }
  },
  computed: {
    changed() {
      return JSON.stringify(this.itemCopy) !== JSON.stringify(this.item);
    }
  },
  methods: {
    handleInputChange(event) {
      this.itemCopy[event.field] = event.value;
      console.log(`BaseItemForm: ${event.field} changed to ${event.value}`);
      this.$emit('input-change', event.field, event.value);
    },
    clearForm() {
      this.itemCopy = {};
    },
    resetForm() {
      this.itemCopy = Object.assign({}, this.item);
    },
    saveForm() {
      this.$emit('save', this.itemCopy);
    },
    deleteForm() {
      this.$emit('delete', this.itemCopy);
    }
  }
}
</script>

<style scoped>
</style>