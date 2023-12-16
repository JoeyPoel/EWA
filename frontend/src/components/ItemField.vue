<template>
  <v-col cols="12" md="6">
    <v-text-field v-if="type === 'text'"
                  v-model="value"
                  :disabled="disabled"
                  :label="label"
                  :rules="rules"
                  @input="onInputChange"/>
    <v-select v-else-if="type === 'select'"
              v-model="value"
              :disabled="disabled"
              :label="label"
              :rules="rules"
              :items="items"
              :item-title="item => item.name"
              :item-value=" item => item.id"
              @input="onInputChange"/>
    <v-select v-else-if="type === 'selectMultiple'"
              v-model="value"
              :disabled="disabled"
              :label="label"
              :rules="rules"
              :items="items"
              :item-title="item => item.name"
              :item-value=" item => item.id"
              multiple
              @input="onInputChange"/>
  </v-col>
</template>

<script>
import {VSelect, VTextField} from "vuetify/components";

export default {
  name: "ItemField",
  components: {
    VSelect,
    VTextField
  },
  props: {
    type: {
      type: String,
      required: true
    },
    fieldName: {
      type: String,
      required: true
    },
    disabled: {
      type: Boolean,
      required: false,
      default: false
    },
    label: {
      type: String,
      required: true
    },
    rules: {
      type: Array,
      required: false,
      default: () => []
    },
    item: {
      required: true,
    },
    items: {
      type: Array,
      required: false,
    },
  },
  computed: {
    value: {
      get() {
        return this.item;
      },
      set(value) {
        this.$emit('input', value);
      }
    }
  },
  methods: {
    onInputChange(value) {
      this.$emit('input-change', {field: this.fieldName, value: value});
    },
  }
}
</script>