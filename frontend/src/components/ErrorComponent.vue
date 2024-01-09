<template>
  <v-dialog v-model="isOpen" max-width="450px">
    <v-card>
      <v-card-text>
        <v-row class="text-center justify-space-between">
            <v-card-title class="ps-3 pb-5"><h3>Something went wrong!</h3></v-card-title>
            <v-btn @click="closeDialog" icon="$close" color="secondary"/>
        </v-row>
        <v-row :no-gutters="true">
          <v-col>
            <p v-html="formattedMessage"></p>
          </v-col>
        </v-row>
      </v-card-text>
    </v-card>
  </v-dialog>
</template>

<script>
export default {
  props: {
    open: {
      type: Boolean,
      required: true,
    },
    message:{
      type: String,
      required: false
    },
  },
  methods: {
    closeDialog() {
      this.$emit('close');
    },
  },
  computed: {
    formattedMessage() {
      return this.message.replace(/\n/g, '<br>');
    },
    isOpen: {
      get() {
        return this.open;
      },
      set() {
        this.closeDialog();
      }
    },
  },
}
</script>

<style scoped>

</style>