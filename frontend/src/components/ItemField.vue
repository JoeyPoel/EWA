<template>
  <v-col cols="12" md="6">
    <v-text-field v-if="type === 'text'"
                  v-model="ItemCopy"
                  :disabled="disabled"
                  :label="label"
                  :rules="rules"
                  @input="onInputChange"/>
    <v-select v-else-if="type === 'select'"
              v-model="ItemCopy"
              :disabled="disabled"
              :label="label"
              :rules="rules"
              :items="items"
              item-title="name"
              item-value="id"
              @update:model-value="onInputChange"/>
    <v-select v-else-if="type === 'selectMultiple'"
              v-model="ItemCopy"
              :disabled="disabled"
              :label="label"
              :rules="rules"
              :items="items"
              item-title="name"
              item-value="id"
              :multiple="true"
              @update:model-value="onInputChange"/>
  </v-col>
</template>

<script>
export default {
  name: "ItemField",
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
      default: () => []
    },
  },
  watch: {
  },
  computed: {
    ItemCopy: {
      get() {
        return this.item[this.fieldName];
      },
      set(value) {
        this.$emit('input-change', value);
      }
    }
  },
  methods: {
    onInputChange(value) {
      const changes = {
        field: this.fieldName,
        value: value
      };
      console.log(changes)
      console.log(`ItemField: ${this.fieldName} changed to ${value}`);
      this.$emit('input-change', changes);
    },
  }
}
</script>