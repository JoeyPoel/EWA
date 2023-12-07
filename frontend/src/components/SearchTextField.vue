<template>
  <v-text-field v-model="fieldValue" :label="label" prepend-inner-icon="$search" variant="outlined"/>
</template>
<script> export default {
  name: 'SearchTextField', props: ['label', 'value'], computed: {
    fieldValue: {
      get: function () {
        return this.value;
      }, set: function (newValue) {
        this.$emit('input', newValue);
      }
    }
  }
} </script>