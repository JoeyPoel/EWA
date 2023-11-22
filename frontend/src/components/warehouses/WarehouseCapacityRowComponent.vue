<template>
  <tr class="border border-light-subtle border-1" @mouseleave="mouseOver = false" @mouseover="mouseOver = true">
    <td>
      <div>
        {{ category.categoryName }}
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
    <td class="col col-3">
      <div v-show="mouseOver || hasChanged" class="row row-cols-4 align-self-center justify-content-center">
        <b-icon-pen data-bs-placement="top" data-bs-toggle="tooltip" title="Edit capacity" @click="edit"/>
        <b-icon-save v-show="hasChanged" data-bs-placement="top" data-bs-toggle="tooltip" title="Save capacity"
                     @click="save"/>
        <b-icon-arrow-counterclockwise v-show="hasChanged" data-bs-placement="top" data-bs-toggle="tooltip"
                                       title="Reset capacity" @click="reset"/>
      </div>
    </td>
  </tr>
</template>

<script>
import {BIconArrowCounterclockwise, BIconPen, BIconSave} from "bootstrap-icons-vue";
import {WarehouseProductCategoryCapacity} from "@/models/WarehouseProductCategoryCapacity";
import {Tooltip} from "bootstrap";

export default {
  name: "WarehouseCapacityRowComponent",
  inject: ['warehousesService'],
  components: {
    BIconPen,
    BIconSave,
    BIconArrowCounterclockwise
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
  mounted() {
    const tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'))
    tooltipTriggerList.map(function (tooltipTriggerEl) {
      return new Tooltip(tooltipTriggerEl)
    })
  },
  methods: {
    edit() {
      this.isEditing = !this.isEditing;
    },
    async save() {
      this.isEditing = false;

      const savedCategory = this.category.id ?
          await this.warehousesService.asyncUpdateWarehouseCapacityById(
              this.category.id, this.categoryCopy) :
          await this.warehousesService.asyncAddWarehouseCapacityByWarehouseId(
              this.$route.params.id, this.categoryCopy);

      if (savedCategory) {
        this.categoryCopy = Object.assign(new WarehouseProductCategoryCapacity(), savedCategory);
      }

      this.$emit('save-category-capacity', this.category);
    },
    reset() {
      this.categoryCopy = Object.assign(new WarehouseProductCategoryCapacity(), this.category);
      this.isEditing = false;
    },
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
tr {
  min-height: 55px;
}

</style>