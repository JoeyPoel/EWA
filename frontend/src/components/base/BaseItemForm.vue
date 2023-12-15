<template>
  <div>
    <v-form>
      <v-container>
        <v-row>
          <ItemField v-for="(field, index) in itemFields" :key="index"
                     :field="field" @input-change="(value) => handleInputChange(index, value)"/>
        </v-row>
        <v-row>
          <v-btn color="primary" @click="clearForm">Clear</v-btn>
          <v-btn :disabled="!changed" color="primary" @click="resetForm">Reset</v-btn>
          <v-btn :disabled="!changed" color="primary" @click="saveForm">Save</v-btn>
        </v-row>
      </v-container>
    </v-form>
  </div>
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