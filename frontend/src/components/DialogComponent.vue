<template>
  <v-dialog v-model="dialog" :max-width="maxWidth">
    <v-card>
      <v-card-title>
        <span class="headline">{{ title }}</span>
        <v-spacer></v-spacer>
        <v-btn icon @click="closeDialog">
          <v-icon>mdi-close</v-icon>
        </v-btn>
      </v-card-title>
      <v-card-text>
        <slot name="content"></slot>
      </v-card-text>
    </v-card>
  </v-dialog>
</template>

<script>
export default {
  name: "DialogComponent",
  props: {
    title: {
      type: String,
      required: true
    },
    open: {
      type: Boolean,
      required: true
    },
    maxWidth: {
      type: String,
      default: '800px'
    }
  },
  computed: {
    dialog() {
      return this.open;
    }
  },
  methods: {
    closeDialog() {
      this.$emit('close');
    }
  }
}
</script>

<style scoped>

</style>