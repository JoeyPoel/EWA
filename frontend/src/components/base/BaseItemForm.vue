<template>
  <v-container>
    <v-form>
      <v-row>
        <ItemField v-for="field in itemFields" :key="field.name" :type="field.type" :label="field.label"
                   :rules="field.rules" :disabled="allDisabled || field.disabled" :fieldName="field.name"
                   :item="itemCopy" :items="field.items"
                   @input-change="handleInputChange($event)" />
      </v-row>
      <v-row v-if="!allDisabled">
        <v-col cols="12" sm="6">
          <v-btn text="Clear" color="primary" @click="clearForm" :block="true" rounded="sm"/>
        </v-col>
        <v-col cols="12" sm="6">
          <v-btn text="Reset" :disabled="!changed" color="primary" @click="resetForm" :block="true" rounded="sm"/>
        </v-col>
        <v-col cols="12" sm="6">
          <v-btn text="Save" :disabled="!changed" color="primary" @click="saveForm" :block="true" rounded="sm"/>
        </v-col>
        <v-col cols="12" sm="6">
          <v-btn text="Delete" color="secondary" :disabled="!canDelete" @click="confirmDelete" :block="true"
                 rounded="sm"/>
          <v-dialog v-model="dialog" max-width="500px">
            <v-card title="Delete" text="Are you sure you want to delete this item?" class="text-center">
              <v-container>
                <v-row>
                  <v-col>
                    <v-btn text="Delete" color="secondary" @click="deleteForm" rounded="sm" :block="true"/>
                  </v-col>
                  <v-col>
                    <v-btn text="Cancel" color="primary" @click="dialog = false" rounded="sm" :block="true"/>
                  </v-col>
                </v-row>
              </v-container>
            </v-card>
          </v-dialog>
        </v-col>
      </v-row>
    </v-form>
  </v-container>
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
    },
    allDisabled: {
      type: Boolean,
      required: false,
      default: false
    }
  },
  data() {
    return {
      itemCopy: Object.assign({}, this.item),
      dialog: false
    }
  },
  computed: {
    changed() {
      return JSON.stringify(this.itemCopy) !== JSON.stringify(this.item);
    }
  },
  methods: {
    handleInputChange(event) {
      this.itemCopy[event.field] = event.value;
      console.log(`BaseItemForm: ${event.field} changed to ${event.value}`);
      this.$emit('input-change', event.field, event.value);
    },
    clearForm() {
      this.itemCopy = {};
    },
    resetForm() {
      this.itemCopy = Object.assign({}, this.item);
    },
    saveForm() {
      this.$emit('save', this.itemCopy);
    },
    confirmDelete() {
      this.dialog = true
    },
    deleteForm() {
      this.dialog = false
      this.$emit('delete', this.itemCopy);
    },
    canDelete() {
    //   TODO: implement. Check data integrity.
      return true;
    }
  }
}
</script>

<style scoped>
</style>