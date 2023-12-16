<template>
  <v-container>
    <v-form>
      <v-row>
        <ItemField v-for="(field, index) in itemFields" :key="index" :type="field.type" :label="field.label"
                   :rules="field.rules" :disabled="allDisabled || field.disabled" :field-name="field.name"
                   :item="item[field.name]"
                   :items="field.items" @input-change="(value) => handleInputChange(index, value)" />
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
    handleInputChange(index, value) {
      this.itemCopy[this.itemFields[index].name] = value;
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