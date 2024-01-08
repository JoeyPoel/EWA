<template>
  <v-container>
    <v-form>
      <v-row>
        <ItemField v-for="field in itemFields" :key="field.name" :disabled="allDisabled || field.disabled"
                   :fieldName="field.name" :item="itemCopy" :items="field.items" :label="field.label"
                   :rules="field.rules" :type="field.type" @input-change="handleInputChange($event)"/>
      </v-row>
      <v-row v-if="!allDisabled">
        <v-col v-if="!isNewItem" cols="12" sm="4">
          <v-btn :block="true" :disabled="!canDelete" color="secondary" rounded="sm" text="Delete"
                 @click="confirmDelete"/>
          <v-dialog v-model="dialog" max-width="500px">
            <v-card class="text-center" text="Are you sure you want to delete this item?" title="Delete">
              <v-container>
                <v-row>
                  <v-col>
                    <v-btn :block="true" color="secondary" rounded="sm" text="Delete" @click="this.delete"/>
                  </v-col>
                  <v-col>
                    <v-btn :block="true" color="primary" rounded="sm" text="Cancel" @click="dialog = false"/>
                  </v-col>
                </v-row>
              </v-container>
            </v-card>
          </v-dialog>
        </v-col>
        <v-col :sm="isNewItem ? 6 : 4" cols="12">
          <v-btn :block="true" :disabled="!changed" :color="isNewItem ? 'secondary' : 'primary'" rounded="sm"
                 text="Reset" @click="resetForm"/>
        </v-col>
        <v-col :sm="isNewItem ? 6 : 4" cols="12">
          <v-btn :block="true" color="primary" :disabled="!changed" rounded="sm"
                 text="Save" @click="save"/>
        </v-col>
      </v-row>
    </v-form>
  </v-container>
</template>

<script>
import ItemField from './ItemField.vue'

export default {
  name: "ItemForm",
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
    },
    isNewItem: {
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
    },
    canDelete() {
      //   TODO: implement. Check data integrity.
      return true;
    }
  },
  methods: {
    handleInputChange(event) {
      this.itemCopy[event.field] = event.value;
      this.$emit('input-change', event.field, event.value);
    },
    resetForm() {
      this.itemCopy = Object.assign({}, this.item);
    },
    confirmDelete() {
      this.dialog = true
    },
    save() {
      this.$emit('save', this.itemCopy);
    },
    delete() {
      this.dialog = false
      this.$emit('delete', this.itemCopy);
    }
  }
}
</script>

<style scoped>
</style>