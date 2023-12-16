<template>
  <v-container>
    <v-form>
      <v-row>
        <ItemField v-for="field in itemFields" :key="field.name" :type="field.type" :label="field.label"
                   :rules="field.rules" :disabled="allDisabled || field.disabled" :fieldName="field.name"
                   :item="itemCopy" :items="field.items"
                   @input-change="handleInputChange($event)" />
      </v-row>
      <v-row>
        <v-btn color="primary" @click="clearForm">Clear</v-btn>
        <v-btn :disabled="!changed" color="primary" @click="resetForm">Reset</v-btn>
        <v-btn :disabled="!changed" color="primary" @click="saveForm">Save</v-btn>
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
    }
  }
}
</script>

<style scoped>
</style>