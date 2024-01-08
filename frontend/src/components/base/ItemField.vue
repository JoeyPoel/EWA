<template>
  <v-col cols="12" md="6">
    <v-text-field v-if="type === 'text' || type === 'number'"
                  v-model="ItemCopy"
                  :label="label"
                  :rules="rules"
                  :type="type"
                  :readonly="disabled"
                  @update:model-value="onInputChange"/>
    <v-select v-else-if="type === 'select'"
              v-model="ItemCopy"
              :label="label"
              :rules="rules"
              :items="items"
              item-title="name"
              item-value="id"
              :readonly="disabled"
              @update:model-value="onInputChange"/>
    <v-select v-else-if="type === 'selectMultiple'"
              v-model="ItemCopy"
              :label="label"
              :rules="rules"
              :items="items"
              item-title="name"
              item-value="id"
              :multiple="true"
              :chips="true"
              :readonly="disabled"
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
    textInput(value) {
      this.onInputChange(value.data);
    },
    onInputChange(value) {
      const changes = {
        field: this.fieldName,
        value: value
      };
      this.$emit('input-change', changes);
    },
  }
}
</script>