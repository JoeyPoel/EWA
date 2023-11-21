<template>
  <div class="m-0 border border-2 rounded border-light-subtle">
    <div class="row m-0 justify-content-center border border-2 rounded border-light-subtle ">
      <WarehouseNewProductCategoryStorageCapacityComponent @addMissingCategory="addCategoryCapacity"/>
    </div>
    <div class="row m-0 justify-content-center">
      <div class="col m-0 align-self-center">
        <div class="row m-0 justify-content-center">
          <div class="scrollPanel">
            <table class="table table-hover">
              <thead>
              <tr>
                <th class="fw-light">Category Name</th>
                <th class="fw-light">Minimum stock level</th>
                <th class="fw-light">Capacity</th>
                <th class="fw-light">Actions</th>
              </tr>
              </thead>
              <tbody>
<!--              <tr v-for="category in warehouseProductCategoryCapacities" :key="category.id"-->
<!--                  class="border border-light-subtle border-1">-->
<!--                <td>-->
<!--                  <div v-if="!isEditing">-->
<!--                    {{ category.productCategory.name }}-->
<!--                  </div>-->
<!--                  <div v-else>-->
<!--                    <input id="quantity" v-model="category.name" class="form-control"-->
<!--                           type="text"/>-->
<!--                  </div>-->
<!--                </td>-->
<!--                <td>-->
<!--                  <div v-if="!isEditing">-->
<!--                    {{ category.minimumStockLevel }}-->
<!--                  </div>-->
<!--                  <div v-else>-->
<!--                    <input id="quantity" v-model="category.minimumStockLevel" class="form-control"-->
<!--                           type="number"/>-->
<!--                  </div>-->
<!--                </td>-->
<!--                <td>{{ category.capacity }}</td>-->
<!--                <td>-->
<!--                  <div class="row">-->
<!--                    <b-icon-pen/>-->
<!--                    <b-icon-save/>-->
<!--                    <b-icon-arrow-counterclockwise/>-->
<!--                    <b-icon-trash/>-->
<!--                  </div>-->
<!--                </td>-->
<!--              </tr>-->
              <warehouse-capacity-row-component v-for="category in warehouseProductCategoryCapacities"
                                                :key="category.id"
                                                :category="category"
                                                @save-category-capacity="saveCategoryCapacity"
                                                @remove-category-capacity="removeCategoryCapacity"/>
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import WarehouseNewProductCategoryStorageCapacityComponent
  from "@/components/warehouses/WarehouseNewProductCategoryStorageCapacityComponent.vue";
import WarehouseCapacityRowComponent from "@/components/warehouses/WarehouseCapacityRowComponent.vue";

export default {
  name: "WarehouseDetailInventoryComponent",
  components: {
    WarehouseNewProductCategoryStorageCapacityComponent,
    WarehouseCapacityRowComponent
  },
  inject: ['productsService', 'warehousesService'],
  props: {},
  data() {
    return {
      lastId: 1000,
      isActive: false,
      warehouseProductCategoryCapacities: [],
      isEditing: false,
    }
  },
  async mounted() {
    await this.getCapacities()
  },
  computed: {},
  methods: {
    addCategoryCapacity(category) {
      this.$emit('add-category', category)
    },
    removeCategoryCapacity(category) {
      this.$emit('remove-category-capacity', category)
    },
    saveCategoryCapacity(category) {
      this.$emit('save-category-capacity', category)
    },
    async getCapacities() {
      this.warehouseProductCategoryCapacities = this.$route.params.id ?
          await this.warehousesService.asyncGetWarehouseCapacityCategories(this.$route.params.id) : null;
    },
    mouseOver() {
      this.isActivated = true;
    },
  },
}
</script>

<style scoped>
.scrollPanel {
  max-height: 40vh;
  overflow-y: auto;
}
</style>