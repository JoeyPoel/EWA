<template>
  <tr class="border border-light-subtle border-1" @mouseleave="mouseOver = false" @mouseover="mouseOver = true">
    <td>
      <div>
        {{ category.productCategory.name }}
      </div>
    </td>
    <td>
      <div v-if="!isEditing">
        {{ category.minimumStockLevel }}
      </div>
      <div v-else>
        <input id="quantity" v-model="categoryCopy.minimumStockLevel" class="form-control" type="number"/>
      </div>
    </td>
    <td>
      <div v-if="!isEditing">
        {{ category.capacity }}
      </div>
      <div v-else>
        <input id="capacity" v-model="categoryCopy.capacity" class="form-control" type="number"/>
      </div>
    </td>
    <td class="row action-row">
      <div v-if="mouseOver || hasChanged" class="row">
        <b-icon-pen @click="edit"/>
        <b-icon-save v-if="hasChanged" @click="save"/>
        <b-icon-arrow-counterclockwise v-if="hasChanged" @click="reset"/>
        <b-icon-trash v-if="!hasChanged" @click="remove"/>
      </div>
    </td>
  </tr>
</template>

<script>
import {BIconArrowCounterclockwise, BIconPen, BIconSave, BIconTrash} from "bootstrap-icons-vue";
import {WarehouseProductCategoryCapacity} from "@/models/WarehouseProductCategoryCapacity";

export default {
  name: "WarehouseCapacityRowComponent",
  components: {
    BIconPen,
    BIconSave,
    BIconArrowCounterclockwise,
    BIconTrash
  },
  props: {
    category: {
      type: WarehouseProductCategoryCapacity,
      required: true
    },
  },
  computed: {
    hasChanged() {
      return !WarehouseProductCategoryCapacity.equals(this.category, this.categoryCopy);
    }
  },
  data() {
    return {
      categoryCopy: Object.assign(new WarehouseProductCategoryCapacity(), this.category),
      isEditing: false,
      mouseOver: false
    }
  },
  methods: {
    edit() {
      this.isEditing = !this.isEditing;
    },
    save() {
      this.isEditing = false;
      this.$emit('save-category-capacity', this.category);
    },
    reset() {
      this.isEditing = false;
    },
    remove() {
      this.$emit('remove-category-capacity', this.category);
    }
  },
  watch: {
    mouseOver: function (val) {
      if (!val && this.hasChanged) {
        this.isEditing = true;
      }
    }
  }
}
</script>

<style scoped>
.action-row {
  border: none;
}
</style>