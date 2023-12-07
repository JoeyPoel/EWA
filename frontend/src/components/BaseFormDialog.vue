<template>
  <DialogComponent :title="dialogTitle" :open="dialogOpen" @close="closeDialog">
    <ItemForm :item="item" :itemFields="itemFields" @save="handleSave" :actions="formActions"/>
  </DialogComponent>
</template>

<script>
import DialogComponent from "@/components/DialogComponent";
import ItemForm from "@/components/ItemForm";

export default {
  name: 'BaseFormDialog',
  components: {
    DialogComponent,
    ItemForm,
  },
  props: {
    dialogOpen: Boolean,
    dialogTitle: String,
    item: Object,
    itemFields: Array,
    formActions: Array
  },
  methods: {
    closeDialog() {
      this.$emit('dialog-closed');
    },
    handleSave(item){
      this.$emit('save-item', item);
    }
  }
}
</script>

<style scoped>

</style>