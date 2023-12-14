<template>
  <v-col cols="12" md="6">
    <component :is="field.type"
               v-model="value"
               :disabled="field.disabled"
               :label="field.label"
               :rules="field.rules"
               v-bind="field.type === 'selectOne' || field.type === 'selectMultiple' ? {
                 'item-text': 'name',
                 'item-value': 'id',
                  items: field.items
               } :
               {}"
               @input="onInputChange"
    />
  </v-col>
</template>

<script>
export default {
  name: "ItemField",
  props: {
    field: {
      type: Object,
      required: true
    }
  },
  computed: {
    value: {
      get() {
        return this.field.value;
      },
      set(value) {
        this.$emit('input', value);
      }
    }
  },
  components: {
    'select-one': 'v-select',
    'select-multiple': 'v-select',
    'txt': 'v-text-field',
  },
  methods: {
    onInputChange(value) {
      this.$emit('input-change', {field: this.field, value: value});
    }
  }
}
</script>