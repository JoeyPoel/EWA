<template>
  <v-col cols="12" md="6">
    <v-text-field v-if="type === 'text' || type === 'number' || type === 'date'"
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
    <product-line-entity-table
        v-else-if="type === 'productLine'"
        :product-lines="ItemCopy"
        @update:model-value="onInputChange"/>
    <v-container v-else><h4>Unknown item field type!</h4></v-container>
  </v-col>
</template>

<script>
import ProductLineEntityTable from "@/components/product/ProductLineEntityTable.vue";

export default {
  name: "ItemField",
  components: {ProductLineEntityTable},
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